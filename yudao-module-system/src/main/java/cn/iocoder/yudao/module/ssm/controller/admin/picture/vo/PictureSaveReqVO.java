package cn.iocoder.yudao.module.ssm.controller.admin.picture.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 图片新增/修改 Request VO")
@Data
public class PictureSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20935")
    private Long id;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "原始字节码")
    private String baseContent;

    @Schema(description = "解密字节码")
    private String content;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    private Long  parentId;

    private String handleCode;


}
