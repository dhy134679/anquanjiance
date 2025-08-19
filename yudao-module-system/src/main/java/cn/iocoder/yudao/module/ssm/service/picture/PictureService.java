package cn.iocoder.yudao.module.ssm.service.picture;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 图片 Service 接口
 *
 * @author 管理员
 */
public interface PictureService {

    /**
     * 创建图片
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPicture(@Valid PictureSaveReqVO createReqVO);

    /**
     * 更新图片
     *
     * @param updateReqVO 更新信息
     */
    void updatePicture(@Valid PictureSaveReqVO updateReqVO);

    /**
     * 删除图片
     *
     * @param id 编号
     */
    void deletePicture(Long id);

    /**
    * 批量删除图片
    *
    * @param ids 编号
    */
    void deletePictureListByIds(List<Long> ids);

    /**
     * 获得图片
     *
     * @param id 编号
     * @return 图片
     */
    PictureDO getPicture(Long id);

    /**
     * 获得图片分页
     *
     * @param pageReqVO 分页查询
     * @return 图片分页
     */
    PageResult<PictureDO> getPicturePage(PicturePageReqVO pageReqVO);

}