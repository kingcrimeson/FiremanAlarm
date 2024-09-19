package com.FireAlarm.Service.ServiceImpl;

import com.FireAlarm.Mapper.PictureMapper;
import com.FireAlarm.Mapper.VideoMapper;
import com.FireAlarm.Service.CachePreWarm;
import com.FireAlarm.pojo.Picture;
import com.FireAlarm.pojo.Video;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CachePreWarmImpl implements CachePreWarm {



    public static String PIC_KEY = "image";
    public static String VIDEO_KEY = "video";

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    public String getPicKey(){
        return PIC_KEY;
    }

    public String getVideoKey(){
        return VIDEO_KEY;
    }
    @PostConstruct
    public void preWarm() {
        List<Picture> pictures = pictureMapper.selectList(null);
        List<Video> videos = videoMapper.selectList(null);

        for (Picture picture : pictures) {
            redisTemplate.opsForValue().set(picture.getMD5(),1);
            addRedisBoolean(PIC_KEY, picture.getMD5());
        }

        for (Video video : videos) {
            redisTemplate.opsForValue().set(video.getMD5(),1);
            addRedisBoolean(VIDEO_KEY, video.getMD5());
        }
    }

    public void addRedisBoolean(String key, String value) {
        int[] hashValues = hash(value);
        for(int hashValue : hashValues) {
            redisTemplate.opsForValue().setBit(key,hashValue,true);
        }
    }
    public boolean existed(String key,String value) {
        int[] hashValues = hash(value);
        for (int hashvalue : hashValues) {
            if(!redisTemplate.opsForValue().getBit(key,hashvalue)){
                return false;
            }
        }
        return true;
    }
    public int[] hash(String value) {
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;
        return new int[]{Math.abs(hash1 % 1000), Math.abs(hash2 % 1000)};
    }



}
