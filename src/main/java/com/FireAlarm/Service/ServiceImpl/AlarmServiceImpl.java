package com.FireAlarm.Service.ServiceImpl;


import com.FireAlarm.Config.WebSocketConfig.MyWebSocketHandler;
import com.FireAlarm.Mapper.ContentMapper;
import com.FireAlarm.Mapper.FireconditionMapper;
import com.FireAlarm.Mapper.MessageMapper;
import com.FireAlarm.pojo.Content;
import com.FireAlarm.pojo.Firecondition;
import com.FireAlarm.pojo.Message;
import com.FireAlarm.pojo.MessageDTO;
import com.FireAlarm.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmServiceImpl {

    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private FireconditionMapper fireconditionMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Resource
    private MyWebSocketHandler myWebSocketHandler;


    public Result RingOut(MessageDTO alarmMessage){
        Message message = new Message();

        try{
            BeanUtils.copyProperties(alarmMessage,message);
            System.out.println("message = " + message);
            LocalDateTime createTime = LocalDateTime.now();
            message.setCreateTime(createTime);
            messageMapper.insert(message);
            if(alarmMessage.getFireConditions().size()!=0){
                LambdaQueryWrapper<Firecondition> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(Firecondition::getFireConditions,alarmMessage.getFireConditions());
                List<Firecondition> fireconditionList = fireconditionMapper.selectList(queryWrapper);
                for (int i = 0 ;i<fireconditionList.size();i++){
                    messageMapper.insertAlarmftom(fireconditionList.get(i).getFid(),message.getMid());
                }
            }
            else if(alarmMessage.getAlarmContent().size()!=0){
                LambdaQueryWrapper<Content> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(Content::getAlarmName,alarmMessage.getAlarmContent());
                List<Content> contentList = contentMapper.selectList(queryWrapper);
                for (int i = 0 ;i<contentList.size();i++){
                    messageMapper.insertAlarmAssociation(contentList.get(i).getCid(),message.getMid());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(alarmMessage);
            myWebSocketHandler.broadcastMessage(jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //downloadImage(alarmMessage.getImage());

        return Result.ok("Get it");
    }
    public Result getMessageDTO(){
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<Message>().orderByDesc(Message::getCreateTime);
        List<Message>messages = messageMapper.selectList(queryWrapper);
        List<MessageDTO>messageDTOS = messages.stream().map(message -> {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(message,messageDTO);
            return  messageDTO;
        }).collect(Collectors.toList());
        for (int i=0;i<messageDTOS.size();i++){
            messageDTOS.get(i).setAlarmContent(messageMapper.selectAlarmContents(messageDTOS.get(i).getMid()));
            messageDTOS.get(i).setFireConditions(messageMapper.selectFireConditions(messageDTOS.get(i).getMid()));
        }

        return Result.ok(messageDTOS);
    }
    public Result upLoadVideo(MultipartFile video, String UUID){
        return Result.ok(null);
    }
}
