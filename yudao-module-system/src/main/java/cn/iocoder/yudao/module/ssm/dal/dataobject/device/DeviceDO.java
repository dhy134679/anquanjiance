package cn.iocoder.yudao.module.ssm.dal.dataobject.device;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备信息 DO
 *
 * @author 管理员
 */
@TableName("ssm_device")
@KeySequence("ssm_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备code
     */
    private String code;
    /**
     * 设设备状态
     */
    private String status;
    /**
     * 设备模型
     */
    private String model;
    /**
     * 设备类型
     */
    private String type;
    /**
     * mqttId
     */
    private String clientId;
    /**
     * 对应发送邮箱
     */
    private String email;


}