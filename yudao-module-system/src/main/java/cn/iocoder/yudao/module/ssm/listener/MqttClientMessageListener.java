package cn.iocoder.yudao.module.ssm.listener;

import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;
import com.alibaba.fastjson.JSONObject;
import org.dromara.mica.mqtt.codec.MqttPublishMessage;
import org.dromara.mica.mqtt.core.client.IMqttClientMessageListener;
import org.dromara.mica.mqtt.spring.client.MqttClientSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tio.core.ChannelContext;

import java.util.UUID;

/**
 * 客户端消息监听的另一种方式
 *
 * @author L.cm
 */
@Service
@MqttClientSubscribe("${topic1}")
public class MqttClientMessageListener implements IMqttClientMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MqttClientMessageListener.class);
    @Value("${spring.mqtt.key}")
    private String key;
    @Value("${spring.mqtt.client.id}")
    private String clientId;
    @Value("${spring.mqtt.username}")
    private String username;
    @Autowired
    private MqttLogService mqttServerService;
    @Autowired
    private RestTemplate restTemplate;
    //    @Autowired
//    private SsmPictureService pictureService;

    @Autowired
    private MqttPicHepler picHepler;
    @Autowired
    private AiPicHepler aiPicHepler;
    @Autowired
    private EmailHepler emailHepler;


    @Override
    public void onMessage(ChannelContext context, String topic, MqttPublishMessage message, byte[] payload) {
        String handCode = UUID.randomUUID().toString();
        try {
            String code = topic.split("/")[topic.split("/").length - 2];
            String messages = AesDecryptUtil.decrypt(new String(message.getPayload()));
            MqDevice device = JSONObject.parseObject(messages, MqDevice.class);
            MqttLogSaveReqVO server = new MqttLogSaveReqVO();
            server.setClientId(clientId);
            server.setUserName(username);
            server.setMessage(messages);
            server.setType(Constant.MQTT_RELEVANT);
            server.setContext(context.toString());
            server.setHandleCode(handCode);
            mqttServerService.createMqttLog(server);
            MqPicDTO mqPicDTO = picHepler.getPicAndVideo(code,device,handCode);
            //AI处理图片
            mqPicDTO = aiPicHepler.handPic(mqPicDTO);
            mqPicDTO = aiPicHepler.handVideo(mqPicDTO);
            AIDTO aidto =  aiPicHepler.getPicAndVideo(mqPicDTO);
            emailHepler.sendEmail(aidto);
        } catch (Exception e) {
            MqttLogSaveReqVO exception = new MqttLogSaveReqVO();
            exception.setType(Constant.HAND_ERROR);
            exception.setContext(e.toString());
            exception.setHandleCode(handCode);
            mqttServerService.createMqttLog(exception);
        }
    }


}

