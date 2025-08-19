package cn.iocoder.yudao.module.ssm.controller.admin.mqttlog;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PictureRespVO;
import cn.iocoder.yudao.module.ssm.dal.mysql.mqttlog.MqttLogMapper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
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

import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.mqttlog.MqttLogDO;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;

@Tag(name = "管理后台 - mqtt日志")
@RestController
@RequestMapping("/ssm/mqtt-log")
@Validated
public class MqttLogController {

    @Value("${spring.mqtt.key}")
    private String key;

    @Resource
    private MqttLogService mqttLogService;

    @Resource
    private MqttLogMapper mqttLogMapper;



    @PostMapping("/getPic")
    public Map getPic(@RequestBody JSONObject map) throws Exception {

        byte[] imageBytes = Files.readAllBytes(Paths.get("D:\\1.png"));
        String code = Base64.getEncoder().encodeToString(imageBytes);
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        //对图片base64转字符串后AES加密
        String encrypt = aes.encryptBase64(code);
        Map result = new HashMap();
        result.put("value",encrypt);
        result.put("name","ces.png");
        return result;
    }

    @PostMapping("/create")
    @Operation(summary = "创建mqtt日志")
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:create')")
    public CommonResult<Long> createMqttLog(@Valid @RequestBody MqttLogSaveReqVO createReqVO) {
        return success(mqttLogService.createMqttLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新mqtt日志")
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:update')")
    public CommonResult<Boolean> updateMqttLog(@Valid @RequestBody MqttLogSaveReqVO updateReqVO) {
        mqttLogService.updateMqttLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除mqtt日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:delete')")
    public CommonResult<Boolean> deleteMqttLog(@RequestParam("id") Long id) {
        mqttLogService.deleteMqttLog(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除mqtt日志")
                @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:delete')")
    public CommonResult<Boolean> deleteMqttLogList(@RequestParam("ids") List<Long> ids) {
        mqttLogService.deleteMqttLogListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得mqtt日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:query')")
    public CommonResult<MqttLogRespVO> getMqttLog(@RequestParam("id") Long id) {
        MqttLogDO mqttLog = mqttLogService.getMqttLog(id);
        return success(BeanUtils.toBean(mqttLog, MqttLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得mqtt日志分页")
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:query')")
    public CommonResult<PageResult<MqttLogRespVO>> getMqttLogPage(@Valid MqttLogPageReqVO pageReqVO) {
        QueryWrapper<MqttLogDO> queryWrapper = new QueryWrapper<>();
        Page page = new Page();
        page.setSize(pageReqVO.getPageSize());
        page.setPages(page.getPages());
        if(pageReqVO.getClientId()!=null){
            queryWrapper.eq("client_id",pageReqVO.getClientId());
        }
        IPage<MqttLogDO> ps = mqttLogMapper.pageGroup(new Page(pageReqVO.getPageNo(), pageReqVO.getPageSize()),queryWrapper);
        List<MqttLogRespVO> lat = new ArrayList<>();
        PageResult<MqttLogRespVO> pst = new PageResult();
        Random rand = new Random();
        for(int i=0;i<ps.getRecords().size();i++){
            MqttLogDO d = ps.getRecords().get(i);
            QueryWrapper<MqttLogDO> q = new QueryWrapper<>();
            q.eq("handle_code",d.getHandleCode());
            List<MqttLogDO> list = mqttLogMapper.selectList(q);
            MqttLogRespVO mqttLogRespVO = BeanUtils.toBean(d, MqttLogRespVO.class);
            mqttLogRespVO.setId(Long.valueOf(10000000+i));
            mqttLogRespVO.setChildren(BeanUtils.toBean(list, MqttLogRespVO.class));
            lat.add(mqttLogRespVO);
        }
        pst.setTotal(ps.getTotal());
        pst.setList(lat);
        return success(pst);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出mqtt日志 Excel")
    @PreAuthorize("@ss.hasPermission('ssm:mqtt-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMqttLogExcel(@Valid MqttLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MqttLogDO> list = mqttLogService.getMqttLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "mqtt日志.xls", "数据", MqttLogRespVO.class,
                        BeanUtils.toBean(list, MqttLogRespVO.class));
    }

}
