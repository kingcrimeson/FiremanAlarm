package com.FireAlarm.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Picture {
    @TableId
    private Integer pid;
    private String imageUuid;
    @TableField("MD5")
    private String MD5;
    private String filePath;


    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

}
