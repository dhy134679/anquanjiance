package cn.iocoder.yudao.module.ssm.controller.admin.video.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 视频新增/修改 Request VO")
@Data
public class VideoSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26973")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "原始字节码")
    private String baseContent;

    @Schema(description = "解密字节码")
    private String content;

    private Long  parentId;

    private String handleCode;

}
