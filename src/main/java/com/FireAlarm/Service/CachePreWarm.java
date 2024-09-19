package com.FireAlarm.Service;

import com.FireAlarm.pojo.Picture;
import com.FireAlarm.pojo.Video;

import java.util.List;

public interface CachePreWarm {
    public void preWarm();


    public void addRedisBoolean(String key, String value);
    public boolean existed(String key,String value);
    public int[] hash(String value);
    public String getPicKey();
    public String getVideoKey();

}
