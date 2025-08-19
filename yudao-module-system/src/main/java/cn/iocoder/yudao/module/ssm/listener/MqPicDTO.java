package cn.iocoder.yudao.module.ssm.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MqPicDTO {
    String picUrl;
    String videoUrl;
    Long picId;
    Long videoId;
    String aiPicUrl;
    String aiVideoUrl;
    String handCode;
}
