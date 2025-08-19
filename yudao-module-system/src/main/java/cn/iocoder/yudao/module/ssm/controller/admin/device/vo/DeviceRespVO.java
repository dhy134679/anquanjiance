package cn.iocoder.yudao.module.ssm.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeviceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12438")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "设备名称", example = "芋艿")
    @ExcelProperty("设备名称")
    private String name;

    @Schema(description = "设备code")
    @ExcelProperty("设备code")
    private String code;

    @Schema(description = "设设备状态", example = "1")
    @ExcelProperty("设设备状态")
    private String status;

    @Schema(description = "设备模型")
    @ExcelProperty("设备模型")
    private String model;

    @Schema(description = "设备类型", example = "1")
    @ExcelProperty("设备类型")
    private String type;

    @Schema(description = "mqttId", example = "10294")
    @ExcelProperty("mqttId")
    private String clientId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "对应发送邮箱")
    @ExcelProperty("对应发送邮箱")
    private String email;

}