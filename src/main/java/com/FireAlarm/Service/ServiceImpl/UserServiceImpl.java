package com.FireAlarm.Service.ServiceImpl;

import com.FireAlarm.Mapper.UserMapper;
import com.FireAlarm.Service.UserService;
import com.FireAlarm.pojo.User;
import com.FireAlarm.utils.JwtHelper;
import com.FireAlarm.utils.MD5Util;
import com.FireAlarm.utils.Result;
import com.FireAlarm.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);
        if(loginUser==null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        else if(!StringUtils.isEmptyOrWhitespaceOnly(user.getUserPwd())&&loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        //return Result.ok(loginUser);
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }
    public Result getMessage(String token){
        Long userId = jwtHelper.getUserId(token);
        if(jwtHelper.isExpiration(token))
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        if(userId!=null){
            User loginUser = userMapper.selectById(userId);
            if(loginUser!=null){
                Map data = new HashMap();
                data.put("loginUser",loginUser);
                return Result.ok(data);
            }
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User loginUser = userMapper.selectOne(queryWrapper);
        if(loginUser==null){
            return  Result.ok(null);

        }
        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User hasUser = userMapper.selectOne(queryWrapper);
        if(hasUser!=null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int uid = userMapper.insert(user);
        return Result.ok(null);
    }
}

