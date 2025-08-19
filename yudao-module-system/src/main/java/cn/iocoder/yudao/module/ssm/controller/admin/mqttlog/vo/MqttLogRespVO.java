package cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - mqtt日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MqttLogRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17444")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "连接者id", example = "6252")
    @ExcelProperty("连接者id")
    private String clientId;

    @Schema(description = "用户名", example = "李四")
    @ExcelProperty("用户名")
    private String userName;

    @Schema(description = "报文")
    @ExcelProperty("报文")
    private String context;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "类型", example = "1")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "消息记录")
    @ExcelProperty("消息记录")
    private String message;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private String handleTime;

    private String handleCode;

    private List<MqttLogRespVO> children;

}
