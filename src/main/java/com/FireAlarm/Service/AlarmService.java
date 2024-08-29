package com.FireAlarm.Service;

import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;

public interface AlarmService {
    public Result ringout(MessageDTO alarmessage);
}
