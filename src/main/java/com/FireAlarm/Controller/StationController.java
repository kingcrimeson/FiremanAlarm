package com.FireAlarm.Controller;

import com.FireAlarm.Service.ServiceImpl.AlarmServiceImpl;
import com.FireAlarm.Service.ServiceImpl.StationServiceImpl;
import com.FireAlarm.Service.StationService;
import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Station")

public class StationController {

    @Autowired
    private AlarmServiceImpl alarmService;

    @Autowired
    private StationService stationService;


    @GetMapping("getAllAlarm")
    public Result GetAllAlarm(){
        return alarmService.getMessageDTO();
    }

    @PostMapping("acceptAlarm")
    public Result acceptAlarm(@RequestBody MessageDTO messageDTO){

        return stationService.acceptAlarm(messageDTO);
    }
}
