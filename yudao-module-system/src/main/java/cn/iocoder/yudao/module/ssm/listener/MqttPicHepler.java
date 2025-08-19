package cn.iocoder.yudao.module.ssm.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PictureSaveReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.VideoSaveReqVO;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;
import cn.iocoder.yudao.module.ssm.service.picture.PictureService;
import cn.iocoder.yudao.module.ssm.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

@Component
public class MqttPicHepler {

    @Autowired
    private MqttLogService mqttServerService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private VideoService videoService;
    @Value("${mqttpic}")
    private String mqttpic;
    @Value("${downpath}")
    private String downpath;

    public MqPicDTO getPicAndVideo(String code, MqDevice device,String handCode) throws Exception {
        long begintime = System.currentTimeMillis();
        //远程接口调用获取图片
        String path="/usr/local/nvr-manager/data/alert_images/person/2025/7/23/363fc002-6764-11f0-89f8-d4f2bcf3c8e5.jpg";
//        String path=device.getImage_path();
        String imageUrl = "http://" + code + mqttpic + path + "&auth=FDcGVhfljwAM04cZUx8Ltg==";
//        ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, null, byte[].class);
        byte[]  decoded = HttpUtil
                    .createGet(imageUrl)
                    .execute().bodyBytes();
        long pictime = System.currentTimeMillis();
        //解析图片报文
//
        //解密后的二进制
        byte[] plain   = AesDecryptUtil.decryptByte(decoded);
        MqttLogSaveReqVO getPic = new MqttLogSaveReqVO();
        getPic.setType(Constant.MQTT_PIC);
//        getPic.setContext(new String(decoded));
        getPic.setHandleTime(String.valueOf(pictime-begintime));
        getPic.setHandleCode(handCode);
        mqttServerService.createMqttLog(getPic);
        String[] img = device.getImage_path().split("/");
        String fileName = img[img.length-1].replace(".enc","");
        //保存图片信息
        PictureSaveReqVO picture = new PictureSaveReqVO();
        //图片base64存储
        //未处理
        picture.setStatus("未处理");
        picture.setName(img[img.length - 1]);
        picture.setHandleCode(handCode);
        Long id = pictureService.createPicture(picture);
        // 获取文件名,存储在当天的文件夹中
        String imgPath = downpath + DateUtil.today()+"-"+System.currentTimeMillis()+"-"+ fileName;
        FileOutputStream fos = new FileOutputStream(imgPath);
        fos.write(plain);
        //获取视频
        String pathv="/usr/local/nvr-manager/data/dvr_playbacks/live/1752718073478052173/2025/07/23/1753234052604.flv";
//        String pathv=device.getRecord_path();
        String videoUrl = "http://" + code + mqttpic + pathv + "&auth=FDcGVhfljwAM04cZUx8Ltg==";
        byte[]  decode = HttpUtil
                .createGet(videoUrl)
                .execute().bodyBytes();
        //解密后的二进制
        byte[] plainv   = AesDecryptUtil.decryptByte(decode);
        long videotime = System.currentTimeMillis();
        MqttLogSaveReqVO getVideo = new MqttLogSaveReqVO();
        getVideo.setType(Constant.MQTT_VIDEO);
        getVideo.setHandleTime(String.valueOf(videotime-begintime));
        getVideo.setHandleCode(handCode);
        mqttServerService.createMqttLog(getVideo);
        String[] vdieName = device.getRecord_path().split("/");
        String vdieNameEnd = vdieName[vdieName.length-1].replace(".enc","");
        String vdieoPath = downpath + DateUtil.today()+"-"+System.currentTimeMillis()+"-"+vdieNameEnd;
        //保存视频信息
        VideoSaveReqVO video = new VideoSaveReqVO();
        //视频base64存储
        //未处理
        video.setStatus("未处理");
        video.setName(vdieName[vdieName.length - 1]);
        video.setHandleCode(handCode);
        Long pid = videoService.createVideo(video);
        FileOutputStream vop = new FileOutputStream(vdieoPath);
        vop.write(plainv);
        MqPicDTO mqPicDTO = new MqPicDTO();
        mqPicDTO.setPicUrl(imgPath);
        mqPicDTO.setVideoUrl(vdieoPath);
        mqPicDTO.setPicId(id);
        mqPicDTO.setVideoId(pid);
        mqPicDTO.setHandCode(handCode);
        return mqPicDTO;
    }


}
