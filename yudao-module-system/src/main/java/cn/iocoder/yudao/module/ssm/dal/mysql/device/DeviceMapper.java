package cn.iocoder.yudao.module.ssm.dal.mysql.device;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.ssm.dal.dataobject.device.DeviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.ssm.controller.admin.device.vo.*;

/**
 * 设备信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface DeviceMapper extends BaseMapperX<DeviceDO> {

    default PageResult<DeviceDO> selectPage(DevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceDO>()
                .likeIfPresent(DeviceDO::getName, reqVO.getName())
                .eqIfPresent(DeviceDO::getCode, reqVO.getCode())
                .eqIfPresent(DeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DeviceDO::getModel, reqVO.getModel())
                .eqIfPresent(DeviceDO::getType, reqVO.getType())
                .eqIfPresent(DeviceDO::getClientId, reqVO.getClientId())
                .betweenIfPresent(DeviceDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DeviceDO::getEmail, reqVO.getEmail())
                .orderByDesc(DeviceDO::getId));
    }

}