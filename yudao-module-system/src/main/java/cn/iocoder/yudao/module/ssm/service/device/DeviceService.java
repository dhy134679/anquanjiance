package cn.iocoder.yudao.module.ssm.service.device;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.ssm.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.device.DeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 设备信息 Service 接口
 *
 * @author 管理员
 */
public interface DeviceService {

    /**
     * 创建设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDevice(@Valid DeviceSaveReqVO createReqVO);

    /**
     * 更新设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDevice(@Valid DeviceSaveReqVO updateReqVO);

    /**
     * 删除设备信息
     *
     * @param id 编号
     */
    void deleteDevice(Long id);

    /**
    * 批量删除设备信息
    *
    * @param ids 编号
    */
    void deleteDeviceListByIds(List<Long> ids);

    /**
     * 获得设备信息
     *
     * @param id 编号
     * @return 设备信息
     */
    DeviceDO getDevice(Long id);

    /**
     * 获得设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备信息分页
     */
    PageResult<DeviceDO> getDevicePage(DevicePageReqVO pageReqVO);

}