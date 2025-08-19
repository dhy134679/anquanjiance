package cn.iocoder.yudao.module.ssm.controller.admin.device.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备信息分页 Request VO")
@Data
public class DevicePageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "芋艿")
    private String name;

    @Schema(description = "设备code")
    private String code;

    @Schema(description = "设设备状态", example = "1")
    private String status;

    @Schema(description = "设备模型")
    private String model;

    @Schema(description = "设备类型", example = "1")
    private String type;

    @Schema(description = "mqttId", example = "10294")
    private String clientId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "对应发送邮箱")
    private String email;

}