package com.FireAlarm.Controller;


import com.FireAlarm.Service.UserService;
import com.FireAlarm.pojo.User;
import com.FireAlarm.utils.JwtHelper;
import com.FireAlarm.utils.Result;
import com.FireAlarm.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("User")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;
    @PostMapping("login")
    public Result login(@RequestBody User user){

        Result result = userService.login(user);
        System.out.println("Result = " + result);
        return result;
    }
    @GetMapping("getUserInfo")
    public Result getMessage(@RequestHeader("token") String token){
        Result result = userService.getMessage(token);
        System.out.println("token = " + token);
        return result;
    }
    @PostMapping("checkUserName")
    public Result checkUserName(@Param("username") String username){
        Result result = userService.checkUserName(username);
        System.out.println("Result = " +result);
        return result;
    }
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        System.out.println("Result = " + result);
        return result;
    }
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader("token") String token){
        if(StringUtils.isEmpty(token)|| jwtHelper.isExpiration(token)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}