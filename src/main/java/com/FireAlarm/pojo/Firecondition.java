package com.FireAlarm.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Firecondition {
    @TableId
    private Integer fid;
    private String fireConditions;
    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


}
