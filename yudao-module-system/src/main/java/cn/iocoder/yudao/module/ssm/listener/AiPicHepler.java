package cn.iocoder.yudao.module.ssm.listener;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PictureSaveReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.VideoSaveReqVO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.video.VideoDO;
import cn.iocoder.yudao.module.ssm.dal.mysql.picture.PictureMapper;
import cn.iocoder.yudao.module.ssm.dal.mysql.video.VideoMapper;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;
import cn.iocoder.yudao.module.ssm.service.picture.PictureService;
import cn.iocoder.yudao.module.ssm.service.video.VideoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

@Component
public class AiPicHepler {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MqttLogService mqttServerService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoMapper videoMapper;
    @Value("${aipic}")
    private String aiPicUrl;
    @Value("${aivideo}")
    private String aiVideoUrl;
    @Value("${aipath}")
    private String aiPath;
    @Value("${aidown}")
    private String aiDown;


    public MqPicDTO handPic(MqPicDTO mqPicDTO) throws Exception {
        long begintime = System.currentTimeMillis();
        HttpHeaders headers1 = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers1.setContentType(type);
        //FileSystemResource将文件变成流以发送
        MultiValueMap<String, Object> resultMap = new LinkedMultiValueMap<>();
        File f = new File(mqPicDTO.getPicUrl());
        resultMap.add("file", new FileSystemResource(f));
        resultMap.add("filename", f.getName());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(resultMap, headers1);
        //postForObject发送请求体
        JSONObject result = restTemplate.postForObject(aiPicUrl, httpEntity, JSONObject.class);
        String url = String.valueOf(result.get("processed_image_url"));
        //存储AI处理图片信息日志
        long endTime = System.currentTimeMillis();
        MqttLogSaveReqVO handPic = new MqttLogSaveReqVO();
        handPic.setType(Constant.AI_HAND_PIC);
        handPic.setContext(result.toString());
        handPic.setHandleCode(mqPicDTO.getHandCode());
        handPic.setHandleTime(String.valueOf(endTime-begintime));
        mqttServerService.createMqttLog(handPic);
        mqPicDTO.setAiPicUrl(url);
        return mqPicDTO;
    }

    public MqPicDTO handVideo(MqPicDTO mqPicDTO) throws Exception {
        long begintime = System.currentTimeMillis();
        HttpHeaders headers1 = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers1.setContentType(type);
        //FileSystemResource将文件变成流以发送
        MultiValueMap<String, Object> resultMap = new LinkedMultiValueMap<>();
        File f = new File(mqPicDTO.getVideoUrl());
        resultMap.add("file", new FileSystemResource(f));
        resultMap.add("filename", f.getName());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(resultMap, headers1);
        //postForObject发送请求体
        JSONObject result = restTemplate.postForObject(aiVideoUrl, httpEntity, JSONObject.class);
        String url = String.valueOf(result.get("processed_video_url"));
        //存储AI处理图片信息日志
        long endTime = System.currentTimeMillis();
        MqttLogSaveReqVO handPic = new MqttLogSaveReqVO();
        handPic.setType(Constant.AI_HAND_VIDEO);
        handPic.setContext(result.toString());
        handPic.setHandleCode(mqPicDTO.getHandCode());
        handPic.setHandleTime(String.valueOf(endTime-begintime));
        mqttServerService.createMqttLog(handPic);
        mqPicDTO.setAiVideoUrl(url);
        return mqPicDTO;
    }


    public AIDTO getPicAndVideo(MqPicDTO mqPicDTO) throws Exception {
        long begintime = System.currentTimeMillis();
        //远程接口调用获取AI处理过图片
        String imageUrl = aiDown+mqPicDTO.getAiPicUrl();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, entity, byte[].class);
        long pictime = System.currentTimeMillis();
        MqttLogSaveReqVO getPic = new MqttLogSaveReqVO();
        getPic.setType(Constant.AI_PIC);
        getPic.setContext(response.toString());
        getPic.setHandleTime(String.valueOf(pictime-begintime));
        getPic.setHandleCode(mqPicDTO.getHandCode());
        mqttServerService.createMqttLog(getPic);
        String[] img = mqPicDTO.getAiPicUrl().split("/");
        String imgPath = aiPath + img[img.length - 1];
        //保存处理过的图片，修改原来图片状态
        //保存图片信息
        PictureSaveReqVO picture = new PictureSaveReqVO();
        picture.setBaseContent(Base64.getEncoder().encodeToString(response.getBody()));
        picture.setName(img[img.length - 1]);
        picture.setParentId(mqPicDTO.getPicId());
        picture.setHandleCode(mqPicDTO.getHandCode());
        pictureService.createPicture(picture);
        PictureDO picture1 =pictureService.getPicture(mqPicDTO.getPicId());
        picture1.setStatus("已处理");
        pictureMapper.updateById(picture1);
        // 获取文件名,存储在当天的文件夹中
        FileOutputStream fos = new FileOutputStream(imgPath);
        fos.write(response.getBody());
        //获取视频
        String vidUrl = aiDown+mqPicDTO.getAiVideoUrl();
        ResponseEntity<byte[]> videoRespons = restTemplate.exchange(vidUrl, HttpMethod.GET, entity, byte[].class);
        long videotime = System.currentTimeMillis();
        MqttLogSaveReqVO getVideo = new MqttLogSaveReqVO();
        getVideo.setType(Constant.AI_VIDEO);
        getVideo.setContext(response.toString());
        getVideo.setHandleTime(String.valueOf(videotime-begintime));
        getVideo.setHandleCode(mqPicDTO.getHandCode());
        mqttServerService.createMqttLog(getVideo);
        String[] vdieName = mqPicDTO.getAiVideoUrl().split("/");
        String vdieoPath = aiPath + vdieName[vdieName.length - 1];
        FileOutputStream vop = new FileOutputStream(vdieoPath);
        vop.write(videoRespons.getBody());
        //保存视频信息
        VideoSaveReqVO video = new VideoSaveReqVO();
        video.setBaseContent(Base64.getEncoder().encodeToString(videoRespons.getBody()));
        video.setName(vdieName[vdieName.length - 1]);
        video.setParentId(mqPicDTO.getVideoId());
        video.setHandleCode(mqPicDTO.getHandCode());
        videoService.createVideo(video);
        //更新原来视频为已处理
        VideoDO v =videoService.getVideo(mqPicDTO.getVideoId());
        v.setStatus("已处理");
        videoMapper.updateById(v);
        AIDTO aidto = new AIDTO();
        aidto.setImageName(img[img.length - 1]);
        aidto.setImageBytes(response.getBody());
        aidto.setVideoName(vdieName[vdieName.length - 1]);
        aidto.setVideoBytes(videoRespons.getBody());
        aidto.setHandCode(mqPicDTO.getHandCode());
        return aidto;
    }


}
