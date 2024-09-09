package com.FireAlarm.Controller;

import com.FireAlarm.Service.ServiceImpl.AlarmServiceImpl;
import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("alarm")
@CrossOrigin
public class AlarmController {
    @Autowired
    private AlarmServiceImpl alarmService;

    @PostMapping("postalarm")
    public Result postalarm(@RequestBody MessageDTO alarmMessage){

        return alarmService.RingOut(alarmMessage);
    }


    @GetMapping("getAllAlarm")
    public Result GetAllAlarm(){
        return alarmService.getMessageDTO();
    }

    @PostMapping("uploadVideo")
    public Result uploadVideo(@RequestParam("video") MultipartFile video,@RequestParam("UUID") String UUID){
        return alarmService.upLoadVideo(video,UUID);
    }
}
