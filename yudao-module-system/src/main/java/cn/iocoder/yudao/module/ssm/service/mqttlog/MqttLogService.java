package cn.iocoder.yudao.module.ssm.service.mqttlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.mqttlog.MqttLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * mqtt日志 Service 接口
 *
 * @author 芋道源码
 */
public interface MqttLogService {

    /**
     * 创建mqtt日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMqttLog(@Valid MqttLogSaveReqVO createReqVO);

    /**
     * 更新mqtt日志
     *
     * @param updateReqVO 更新信息
     */
    void updateMqttLog(@Valid MqttLogSaveReqVO updateReqVO);

    /**
     * 删除mqtt日志
     *
     * @param id 编号
     */
    void deleteMqttLog(Long id);

    /**
    * 批量删除mqtt日志
    *
    * @param ids 编号
    */
    void deleteMqttLogListByIds(List<Long> ids);

    /**
     * 获得mqtt日志
     *
     * @param id 编号
     * @return mqtt日志
     */
    MqttLogDO getMqttLog(Long id);

    /**
     * 获得mqtt日志分页
     *
     * @param pageReqVO 分页查询
     * @return mqtt日志分页
     */
    PageResult<MqttLogDO> getMqttLogPage(MqttLogPageReqVO pageReqVO);

}