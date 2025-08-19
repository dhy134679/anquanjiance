package cn.iocoder.yudao.module.ssm.controller.admin.device;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.ssm.dal.mysql.mqttlog.MqttLogMapper;
import cn.iocoder.yudao.module.ssm.dal.mysql.picture.PictureMapper;
import cn.iocoder.yudao.module.ssm.dal.mysql.video.VideoMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.ssm.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.device.DeviceDO;
import cn.iocoder.yudao.module.ssm.service.device.DeviceService;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/ssm/device")
@Validated
public class DeviceController {

    @Resource
    private DeviceService deviceService;
    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private MqttLogMapper mqttLogMapper;

    @GetMapping("/bi")
    public CommonResult<Map> getBi(String day) {
        if(day==null||"undefined".equals(day)){
            day = DateUtil.today();
        }
        Map map = new HashMap();
        map.put("all",mqttLogMapper.getAllMessage(day));
        map.put("send",mqttLogMapper.getSendMessage(day));
        map.put("picture",mqttLogMapper.getPic(day));
        map.put("video",mqttLogMapper.getVideo(day));
        map.put("email",mqttLogMapper.getEmail(day));
        map.put("error",mqttLogMapper.getError(day));
        return success(map);
    }

    @GetMapping("/getLine")
    public CommonResult<Map> getLine() {
        Map map = new HashMap();
        List<String> line = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for(Map map1:mqttLogMapper.getTime()){
            line.add(String.valueOf(map1.get("num")));
            time.add(String.valueOf(map1.get("time")));
        }
        map.put("num",line);
        map.put("time",time);
        return success(map);
    }


    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    @PreAuthorize("@ss.hasPermission('ssm:device:create')")
    public CommonResult<Long> createDevice(@Valid @RequestBody DeviceSaveReqVO createReqVO) {
        return success(deviceService.createDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    @PreAuthorize("@ss.hasPermission('ssm:device:update')")
    public CommonResult<Boolean> updateDevice(@Valid @RequestBody DeviceSaveReqVO updateReqVO) {
        deviceService.updateDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ssm:device:delete')")
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        deviceService.deleteDevice(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除设备信息")
                @PreAuthorize("@ss.hasPermission('ssm:device:delete')")
    public CommonResult<Boolean> deleteDeviceList(@RequestParam("ids") List<Long> ids) {
        deviceService.deleteDeviceListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ssm:device:query')")
    public CommonResult<DeviceRespVO> getDevice(@RequestParam("id") Long id) {
        DeviceDO device = deviceService.getDevice(id);
        return success(BeanUtils.toBean(device, DeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    @PreAuthorize("@ss.hasPermission('ssm:device:query')")
    public CommonResult<PageResult<DeviceRespVO>> getDevicePage(@Valid DevicePageReqVO pageReqVO) {
        PageResult<DeviceDO> pageResult = deviceService.getDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('ssm:device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeviceExcel(@Valid DevicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeviceDO> list = deviceService.getDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备信息.xls", "数据", DeviceRespVO.class,
                        BeanUtils.toBean(list, DeviceRespVO.class));
    }

}
