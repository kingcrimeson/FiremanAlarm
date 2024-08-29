package com.FireAlarm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Content {
    @TableId
    private Integer cid;
    private String alarmName;


    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;
}
