package com.FireAlarm.Service.ServiceImpl;

import com.FireAlarm.Mapper.MessageMapper;
import com.FireAlarm.Service.StationService;
import com.FireAlarm.pojo.Message;
import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private MessageMapper messageMapper;



    @Override
    public Result acceptAlarm(MessageDTO messageDTO) {
        Message message = new Message();
        BeanUtils.copyProperties(messageDTO,message);
        Integer version = messageMapper.selectById(message.getMid()).getVersion();
        message.setIsAccepted(1);
        message.setVersion(version);

        messageMapper.updateById(message);
        return Result.ok(null);
    }
}
