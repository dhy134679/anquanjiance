package cn.iocoder.yudao.module.ssm.dal.dataobject.mqttlog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * mqtt日志 DO
 *
 * @author 芋道源码
 */
@TableName("ssm_mqtt_log")
@KeySequence("ssm_mqtt_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqttLogDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 连接者id
     */
    private String clientId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 报文
     */
    private String context;
    /**
     * 类型
     */
    private String type;
    /**
     * 消息记录
     */
    private String message;

    private String handleTime;

    private String handleCode;

}
