package com.FireAlarm.Mapper;

import com.FireAlarm.pojo.Message;
import com.FireAlarm.pojo.MessageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Insert(("INSERT INTO alarm_ftom (fid, mid) VALUES (#{fid}, #{mid})"))
    void insertAlarmftom( @Param("fid")int fid,@Param("mid") int mid);
    @Insert(("INSERT INTO alarm_association (cid, mid) VALUES (#{cid}, #{mid})"))
    void insertAlarmAssociation( @Param("cid")int fid,@Param("mid") int mid);

    @Select("SELECT fc.fire_conditions FROM alarm_firecondition fc " +
            "JOIN alarm_ftom fmc ON fc.fid = fmc.fid " +
            "WHERE fmc.mid = #{mid}")
    List<String> selectFireConditions(int mid);

    @Select("SELECT ac.alarm_name FROM alarm_content ac " +
            "JOIN alarm_association mc ON ac.cid = mc.cid " +
            "WHERE mc.mid = #{mid}")
    List<String> selectAlarmContents(int mid);
}
