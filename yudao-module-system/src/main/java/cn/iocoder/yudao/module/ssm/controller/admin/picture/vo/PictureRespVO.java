package cn.iocoder.yudao.module.ssm.controller.admin.picture.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 图片 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PictureRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20935")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "张三")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "类型", example = "2")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "原始字节码")
    @ExcelProperty("原始字节码")
    private String baseContent;

    @Schema(description = "解密字节码")
    @ExcelProperty("解密字节码")
    private String content;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private LocalDateTime sendTime;

    private String handleCode;


    private List<PictureRespVO> children;


}
