package org.example.bean;

public class Bean {
    public String songName;
    public String songSinger;
    public String songURL;
    public String songTime;
    public String fileSize;

    public Bean() {
    }

    public Bean(String songName, String songSinger, String songURL, String songTime, String fileSize) {
        this.songName = songName;
        this.songSinger = songSinger;
        this.songURL = songURL;
        this.songTime = songTime;
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "歌名='" + songName + '\'' +
                ", 演唱='" + songSinger + '\'' +
                ", URL='" + songURL + '\'' +
                ", 时长='" + songTime + '\'' +
                ", 大小='" + fileSize + '\'' +
                '}';
    }
}
