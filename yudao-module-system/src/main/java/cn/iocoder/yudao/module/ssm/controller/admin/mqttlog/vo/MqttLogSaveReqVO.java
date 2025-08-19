package cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - mqtt日志新增/修改 Request VO")
@Data
public class MqttLogSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17444")
    private Long id;

    @Schema(description = "连接者id", example = "6252")
    private String clientId;

    @Schema(description = "用户名", example = "李四")
    private String userName;

    @Schema(description = "报文")
    private String context;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "消息记录")
    private String message;

    @Schema(description = "处理时间")
    private String handleTime;

    @Schema(description = "处理唯一编码")
    private String handleCode;

}
