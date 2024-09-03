package com.FireAlarm.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;


@Data
public class User {

    @TableId
    private Integer uid;
    private String username;
    private String userPwd;
    private String idCard;
    private Integer role;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

}
