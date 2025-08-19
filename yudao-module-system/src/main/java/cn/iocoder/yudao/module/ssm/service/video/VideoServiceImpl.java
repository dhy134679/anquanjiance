package cn.iocoder.yudao.module.ssm.service.video;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.VideoPageReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.VideoSaveReqVO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.video.VideoDO;
import cn.iocoder.yudao.module.ssm.dal.mysql.video.VideoMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 视频 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public Long createVideo(VideoSaveReqVO createReqVO) {
        // 插入
        VideoDO video = BeanUtils.toBean(createReqVO, VideoDO.class);
        videoMapper.insert(video);

        // 返回
        return video.getId();
    }

    @Override
    public void updateVideo(VideoSaveReqVO updateReqVO) {
        // 校验存在
        validateVideoExists(updateReqVO.getId());
        // 更新
        VideoDO updateObj = BeanUtils.toBean(updateReqVO, VideoDO.class);
        videoMapper.updateById(updateObj);
    }

    @Override
    public void deleteVideo(Long id) {
        // 校验存在
        validateVideoExists(id);
        // 删除
        videoMapper.deleteById(id);
    }

    @Override
        public void deleteVideoListByIds(List<Long> ids) {
        // 删除
        videoMapper.deleteByIds(ids);
        }


    private void validateVideoExists(Long id) {
        if (videoMapper.selectById(id) == null) {
            throw exception(new ErrorCode(6000,""));
        }
    }

    @Override
    public VideoDO getVideo(Long id) {
        return videoMapper.selectById(id);
    }

    @Override
    public PageResult<VideoDO> getVideoPage(VideoPageReqVO pageReqVO) {
        return videoMapper.selectPage(pageReqVO);
    }

}
