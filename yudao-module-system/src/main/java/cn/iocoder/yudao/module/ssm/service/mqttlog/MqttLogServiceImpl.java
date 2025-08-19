package cn.iocoder.yudao.module.ssm.service.mqttlog;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogPageReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.mqttlog.MqttLogDO;
import cn.iocoder.yudao.module.ssm.dal.mysql.mqttlog.MqttLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * mqtt日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MqttLogServiceImpl implements MqttLogService {

    @Resource
    private MqttLogMapper mqttLogMapper;

    @Override
    public Long createMqttLog(MqttLogSaveReqVO createReqVO) {
        // 插入
        MqttLogDO mqttLog = BeanUtils.toBean(createReqVO, MqttLogDO.class);
        mqttLogMapper.insert(mqttLog);

        // 返回
        return mqttLog.getId();
    }

    @Override
    public void updateMqttLog(MqttLogSaveReqVO updateReqVO) {
        // 校验存在
        validateMqttLogExists(updateReqVO.getId());
        // 更新
        MqttLogDO updateObj = BeanUtils.toBean(updateReqVO, MqttLogDO.class);
        mqttLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteMqttLog(Long id) {
        // 校验存在
        validateMqttLogExists(id);
        // 删除
        mqttLogMapper.deleteById(id);
    }

    @Override
        public void deleteMqttLogListByIds(List<Long> ids) {
        // 删除
        mqttLogMapper.deleteByIds(ids);
        }


    private void validateMqttLogExists(Long id) {
        if (mqttLogMapper.selectById(id) == null) {
            throw exception(new ErrorCode(6000,""));
        }
    }

    @Override
    public MqttLogDO getMqttLog(Long id) {
        return mqttLogMapper.selectById(id);
    }

    @Override
    public PageResult<MqttLogDO> getMqttLogPage(MqttLogPageReqVO pageReqVO) {
        return mqttLogMapper.selectPage(pageReqVO);
    }

}
