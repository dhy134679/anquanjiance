package cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - mqtt日志分页 Request VO")
@Data
public class MqttLogPageReqVO extends PageParam {

    @Schema(description = "连接者id", example = "6252")
    private String clientId;

    @Schema(description = "用户名", example = "李四")
    private String userName;

    @Schema(description = "报文")
    private String context;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "消息记录")
    private String message;

}