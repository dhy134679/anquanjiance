package cn.iocoder.yudao.module.ssm.dal.mysql.mqttlog;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.ssm.dal.dataobject.mqttlog.MqttLogDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * mqtt日志 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MqttLogMapper extends BaseMapperX<MqttLogDO> {

    default PageResult<MqttLogDO> selectPage(MqttLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MqttLogDO>()
                .eqIfPresent(MqttLogDO::getClientId, reqVO.getClientId())
                .likeIfPresent(MqttLogDO::getUserName, reqVO.getUserName())
                .eqIfPresent(MqttLogDO::getContext, reqVO.getContext())
                .betweenIfPresent(MqttLogDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MqttLogDO::getType, reqVO.getType())
                .eqIfPresent(MqttLogDO::getMessage, reqVO.getMessage())
                .orderByDesc(MqttLogDO::getId));
    }

    @Select("select count(id) from ssm_mqtt_log where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day}")
    int getAllMessage(@Param("day") String day);

    @Select("select count(DISTINCT handle_code) from ssm_mqtt_log where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day} and  handle_code is not null ")
    int getSendMessage(@Param("day") String day);

    @Select("select count(1) from ssm_picture where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day} and  `status` = '已处理'")
    int getPic(@Param("day") String day);

    @Select("select count(1) from ssm_video where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day} and `status` = '已处理'")
    int getVideo(@Param("day") String day);

    @Select("select count(id) from ssm_mqtt_log where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day} and  type='发送邮箱'")
    int getEmail(@Param("day") String day);

    @Select("select count(id) from ssm_mqtt_log where DATE_FORMAT(create_time,'%Y-%m-%d') = #{day} and  type='处理异常'")
    int getError(@Param("day") String day);

    @Select("select COUNT(id) num,DATE_FORMAT(create_time,'%Y-%m-%d') time from ssm_mqtt_log GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')")
    List<Map<String,String>> getTime();

    @Select("select handle_code from ssm_mqtt_log  where handle_code is not null GROUP BY handle_code")
    IPage<MqttLogDO> pageGroup(Page page, QueryWrapper<MqttLogDO> queryWrapper);

}
