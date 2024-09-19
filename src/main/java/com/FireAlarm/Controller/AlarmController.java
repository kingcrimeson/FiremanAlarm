package com.FireAlarm.Controller;

import com.FireAlarm.Service.ServiceImpl.AlarmServiceImpl;
import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("alarm")
@CrossOrigin
public class AlarmController {
    @Autowired
    private AlarmServiceImpl alarmService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @PostMapping("postalarm")
    public Result postalarm(@RequestBody MessageDTO alarmMessage) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(alarmMessage);
        rabbitTemplate.convertAndSend("alarm.exchange","alarm.routing.key",jsonMessage);
        return Result.ok("Alarm message sent to queue");
    }


    @GetMapping("getAllAlarm")
    public Result GetAllAlarm(){
        return alarmService.getMessageDTO();
    }

    @PostMapping("uploadVideo")
    public Result uploadVideo(@RequestParam("video") MultipartFile video,@RequestParam("UUID") String UUID){
        return alarmService.upLoadVideo(video,UUID);
    }
    @PostMapping("uploadPic")
    public Result uploadPic(@RequestParam("picture") MultipartFile pic,@RequestParam("UUID") String UUID){
        return alarmService.upLoadPic(pic,UUID);
    }
}
