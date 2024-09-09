package com.FireAlarm.Service;

import com.FireAlarm.pojo.User;
import com.FireAlarm.utils.Result;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Result login(User user);
    public Result getMessage(String token);
    Result checkUserName(String username);
    Result regist(User user);

}
