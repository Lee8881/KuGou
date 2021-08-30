package org.example.main;

import com.alibaba.fastjson.JSONArray;
import org.example.bean.Bean;
import org.example.spider.Spider;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class App 
{
    public static void main( String[] args )
    {
        String json = "" ;  //存储json数据
        List<Bean> beans = new ArrayList<Bean>();
        String url = "https://www.kugou.com/yy/html/rank.html";//top100界面
        String codeSource = Spider.sendGetRequest(url);//请求
        //匹配正则 , 获取网页json数组
        Pattern pattern=Pattern.compile("global.features = (.+?);");
        Matcher matcher=pattern.matcher(codeSource);
        if(matcher.find()){
            json = matcher.group(1);
        }
        //解析json数组
        JSONArray jsonArray = JSONArray.parseArray(json);
        for(int i=0; i<jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String songName = object.getString("FileName");
            songName = songName.substring(0, songName.indexOf(" - "));
            String songSinger = object.getString("FileName");
            songSinger = songSinger.substring(songSinger.indexOf(" - ")+3);
            String songTime = String.valueOf(Integer.parseInt(object.getString("timeLen")));
            songTime = Integer.parseInt(songTime)/60+"分"+Integer.parseInt(songTime)%60+"秒";
            String fileSize = object.getString("size");
            fileSize = (Integer.parseInt(fileSize)/1024/1024+"."+Integer.parseInt(fileSize)%1024%1024).substring(0,3)+"MB";
            String songURL = object.getString("Hash");
            songURL = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery191028290938214967376_1630337229430&hash="+songURL+"&dfid=1tyAI81zU0R83Nf97P2xaiGP&appid=1014&mid=a30207987911407224a5f3e21ef79205&platid=4&album_id="+object.getString("album_id")+"&_=1630337229431";
            //继续发送请求 , 获取下载地址等信息
            songURL = Spider.sendGetRequest(songURL);
            //匹配正则 , 找到下载地址
            Pattern pattern1=Pattern.compile("play_backup_url\":\"(.+?)\"}");
            Matcher matcher1=pattern1.matcher(songURL);
            if(matcher1.find()){
                songURL = matcher1.group(1).replace("\\","");
            }
            //保存并打印
            Bean bean = new Bean(songName,songSinger,songURL,songTime,fileSize);
            beans.add(bean);
            System.out.println(songName+"\t"+songSinger+"\t"+songTime+"\t"+fileSize+"\t"+songURL);

        }

    }
}
