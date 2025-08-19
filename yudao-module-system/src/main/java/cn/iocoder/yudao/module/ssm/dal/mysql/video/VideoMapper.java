package cn.iocoder.yudao.module.ssm.dal.mysql.video;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.ssm.dal.dataobject.video.VideoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.*;

/**
 * 视频 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface VideoMapper extends BaseMapperX<VideoDO> {

    default PageResult<VideoDO> selectPage(VideoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VideoDO>()
                .likeIfPresent(VideoDO::getName, reqVO.getName())
                .eqIfPresent(VideoDO::getType, reqVO.getType())
                .eqIfPresent(VideoDO::getAddress, reqVO.getAddress())
                .eqIfPresent(VideoDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VideoDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(VideoDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(VideoDO::getBaseContent, reqVO.getBaseContent())
                .eqIfPresent(VideoDO::getContent, reqVO.getContent())
                .orderByDesc(VideoDO::getId));
    }

}