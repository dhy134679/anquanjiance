package cn.iocoder.yudao.module.ssm.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 设备信息新增/修改 Request VO")
@Data
public class DeviceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12438")
    private Long id;

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

    @Schema(description = "对应发送邮箱")
    private String email;

}