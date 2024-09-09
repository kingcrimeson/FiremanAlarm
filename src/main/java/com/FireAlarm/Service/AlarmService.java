package com.FireAlarm.Service;

import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface AlarmService {
    public Result ringout(MessageDTO alarmessage);
    Result upLoadVideo(MultipartFile video, String UUID);
}
