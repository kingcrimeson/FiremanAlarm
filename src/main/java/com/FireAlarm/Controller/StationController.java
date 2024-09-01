package com.FireAlarm.Controller;

import com.FireAlarm.Service.ServiceImpl.AlarmServiceImpl;
import com.FireAlarm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("Station")
@CrossOrigin
public class StationController {

    @Autowired
    private AlarmServiceImpl alarmService;

    @GetMapping("getAllAlarm")
    public Result GetAllAlarm(){
        return alarmService.getMessageDTO();
    }
}
