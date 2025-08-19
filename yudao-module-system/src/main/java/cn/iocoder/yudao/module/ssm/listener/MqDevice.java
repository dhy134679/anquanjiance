package cn.iocoder.yudao.module.ssm.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MqDevice {
    Integer id;
    String device_name;
    String device_id;
    String image_path;
    String record_path;
    JSONObject information;
    String time;
    String event;
    String region;
    Object object;
}
