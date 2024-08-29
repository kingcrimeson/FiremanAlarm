package com.FireAlarm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.List;


@Data
public class MessageDTO {
    @TableId
    private Integer mid;


    private String address;
    private double latitude;
    private double longitude;
    private String alarmType;
    private String fireType;
    private List<String> fireConditions;
    private String flammableItem;
    private String flammableContent;
    private List<String> alarmContent;
    private String rescueSituation;
    private String trappedPeoplecount;
    private String phoneNumber;
    private String createTime;
    private String image;
    private String video;
    private String otherContent;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;
}
