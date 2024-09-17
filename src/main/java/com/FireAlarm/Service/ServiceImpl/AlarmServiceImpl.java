package com.FireAlarm.Service.ServiceImpl;


import com.FireAlarm.Config.WebSocketConfig.MyWebSocketHandler;
import com.FireAlarm.Constant.FilePath;
import com.FireAlarm.Mapper.*;
import com.FireAlarm.pojo.*;
import com.FireAlarm.utils.MD5Util;
import com.FireAlarm.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PictureMapper pictureMapper;


    @Autowired
    private MD5Util md5Util;

    @Autowired
    private FilePath filePath;



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
        if(video.isEmpty()){
            return Result.ok("未选择视频");
        }
        try{
            String md5Checksum = md5Util.calculateMD5(video);
            LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Video::getMD5,md5Checksum);
            queryWrapper.last("LIMIT 1");
            Video t_v = videoMapper.selectOne(queryWrapper);
            Video n_v = new Video();
            String des = filePath.getVideoFilePath()+ video.getOriginalFilename();
            if(t_v==null){
                video.transferTo(new File(des));
            }
            else{
               des=t_v.getFilePath();
            }
            n_v.setFilePath(des);
            n_v.setMD5(md5Checksum);
            n_v.setVideoUuid(UUID);
            videoMapper.insert(n_v);
            return Result.ok("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok("上传失败");
    }
    public Result upLoadPic(MultipartFile pic, String UUID){
        if(pic.isEmpty()){
            return Result.ok("未选择图片");
        }
        try{
            String md5Checksum = md5Util.calculateMD5(pic);
            LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Picture::getMD5,md5Checksum);
            queryWrapper.last("LIMIT 1");
            Picture t_p = pictureMapper.selectOne(queryWrapper);
            Picture n_p = new Picture();
            String des = filePath.getPicFilePath()+ pic.getOriginalFilename();
            if(t_p==null){
                pic.transferTo(new File(des));
            }
            else{
                des=t_p.getFilePath();
            }
            n_p.setFilePath(des);
            n_p.setMD5(md5Checksum);
            n_p.setImageUuid(UUID);
            pictureMapper.insert(n_p);
            return Result.ok("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok("上传失败");
    }
}
