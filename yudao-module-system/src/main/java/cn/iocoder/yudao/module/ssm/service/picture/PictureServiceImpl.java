package cn.iocoder.yudao.module.ssm.service.picture;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PicturePageReqVO;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PictureSaveReqVO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import cn.iocoder.yudao.module.ssm.dal.mysql.picture.PictureMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 图片 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class PictureServiceImpl implements PictureService {

    @Resource
    private PictureMapper pictureMapper;

    @Override
    public Long createPicture(PictureSaveReqVO createReqVO) {
        // 插入
        PictureDO picture = BeanUtils.toBean(createReqVO, PictureDO.class);
        pictureMapper.insert(picture);

        // 返回
        return picture.getId();
    }

    @Override
    public void updatePicture(PictureSaveReqVO updateReqVO) {
        // 校验存在
        validatePictureExists(updateReqVO.getId());
        // 更新
        PictureDO updateObj = BeanUtils.toBean(updateReqVO, PictureDO.class);
        pictureMapper.updateById(updateObj);
    }

    @Override
    public void deletePicture(Long id) {
        // 校验存在
        validatePictureExists(id);
        // 删除
        pictureMapper.deleteById(id);
    }

    @Override
        public void deletePictureListByIds(List<Long> ids) {
        // 删除
        pictureMapper.deleteByIds(ids);
        }


    private void validatePictureExists(Long id) {
        if (pictureMapper.selectById(id) == null) {
            throw exception(new ErrorCode(6000,""));
        }
    }

    @Override
    public PictureDO getPicture(Long id) {
        return pictureMapper.selectById(id);
    }

    @Override
    public PageResult<PictureDO> getPicturePage(PicturePageReqVO pageReqVO) {
        return pictureMapper.selectPage(pageReqVO);
    }

}
