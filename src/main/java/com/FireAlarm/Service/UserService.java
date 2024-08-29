package com.FireAlarm.Service;

import com.FireAlarm.pojo.User;
import com.FireAlarm.utils.Result;

public interface UserService {
    Result login(User user);
    public Result getMessage(String token);
    Result checkUserName(String username);
    Result regist(User user);
}
