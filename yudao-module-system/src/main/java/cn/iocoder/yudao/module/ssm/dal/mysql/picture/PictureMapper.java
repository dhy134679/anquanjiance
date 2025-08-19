package cn.iocoder.yudao.module.ssm.dal.mysql.picture;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.*;

/**
 * 图片 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PictureMapper extends BaseMapperX<PictureDO> {

    default PageResult<PictureDO> selectPage(PicturePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PictureDO>()
                .likeIfPresent(PictureDO::getName, reqVO.getName())
                .eqIfPresent(PictureDO::getType, reqVO.getType())
                .eqIfPresent(PictureDO::getAddress, reqVO.getAddress())
                .eqIfPresent(PictureDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PictureDO::getBaseContent, reqVO.getBaseContent())
                .eqIfPresent(PictureDO::getContent, reqVO.getContent())
                .betweenIfPresent(PictureDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(PictureDO::getSendTime, reqVO.getSendTime())
                .orderByDesc(PictureDO::getId));
    }

}