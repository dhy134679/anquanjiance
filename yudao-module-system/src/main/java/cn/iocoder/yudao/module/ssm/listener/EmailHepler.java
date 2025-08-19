package cn.iocoder.yudao.module.ssm.listener;

import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;

@Component
public class EmailHepler {
    @Value("${email}")
    private String email;
    @Autowired
    private MqttLogService mqttServerService;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(AIDTO aidto) throws Exception {
        //发送邮箱
        MimeMessage m = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(m, true);
        helper.setFrom(email);
        helper.setTo("yinxiong_java@163.com");
        helper.setSubject("现场报警信息请查看！");
        helper.setText("现场报警信息请查看！", true);
        helper.addAttachment(aidto.getImageName(),new ByteArrayResource(aidto.getImageBytes()));
        helper.addAttachment(aidto.getVideoName(), new ByteArrayResource(aidto.getVideoBytes()));
        javaMailSender.send(m);
        MqttLogSaveReqVO emailLog = new MqttLogSaveReqVO();
        emailLog.setUserName("yinxiong_java@163.com");
        emailLog.setType(Constant.EMAIL_SEND);
        emailLog.setHandleCode(aidto.getHandCode());
        mqttServerService.createMqttLog(emailLog);
    }


}
