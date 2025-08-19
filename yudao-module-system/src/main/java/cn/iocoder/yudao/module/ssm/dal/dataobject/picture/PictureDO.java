package cn.iocoder.yudao.module.ssm.dal.dataobject.picture;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 图片 DO
 *
 * @author 管理员
 */
@TableName("ssm_picture")
@KeySequence("ssm_picture_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态
     */
    private String status;
    /**
     * 原始字节码
     */
    private String baseContent;
    /**
     * 解密字节码
     */
    private String content;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    private Long  parentId;
    private String handleCode;


}
