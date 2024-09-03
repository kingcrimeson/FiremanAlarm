package com.FireAlarm.utils;


import com.FireAlarm.pojo.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@ConfigurationProperties(prefix = "formverification")
@Component
public class FormVerification {

    private int USER_IDCARD_LENGTH;
    private int USER_PASSWORD_LENGTH;
    public boolean UserVerification(User user) {
        if(user.getIdCard().length()!=USER_IDCARD_LENGTH){
            return false;
        }
        else if (user.getUserPwd().length()<=USER_PASSWORD_LENGTH){
            return false;
        }
        return true;
    }




}
