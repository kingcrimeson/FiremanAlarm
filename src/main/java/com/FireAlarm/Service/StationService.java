package com.FireAlarm.Service;

import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import org.springframework.stereotype.Service;

@Service
public interface StationService {

    public Result acceptAlarm(MessageDTO messageDTO);
}
