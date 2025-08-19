package cn.iocoder.yudao.module.ssm.controller.admin.picture;

import cn.iocoder.yudao.module.ssm.dal.mysql.picture.PictureMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import cn.iocoder.yudao.module.ssm.service.picture.PictureService;

@Tag(name = "管理后台 - 图片")
@RestController
@RequestMapping("/ssm/picture")
@Validated
public class PictureController {

    @Resource
    private PictureService pictureService;
    @Resource
    private PictureMapper pictureMapper;

    @PostMapping("/create")
    @Operation(summary = "创建图片")
    @PreAuthorize("@ss.hasPermission('ssm:picture:create')")
    public CommonResult<Long> createPicture(@Valid @RequestBody PictureSaveReqVO createReqVO) {
        return success(pictureService.createPicture(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新图片")
    @PreAuthorize("@ss.hasPermission('ssm:picture:update')")
    public CommonResult<Boolean> updatePicture(@Valid @RequestBody PictureSaveReqVO updateReqVO) {
        pictureService.updatePicture(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除图片")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ssm:picture:delete')")
    public CommonResult<Boolean> deletePicture(@RequestParam("id") Long id) {
        pictureService.deletePicture(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除图片")
                @PreAuthorize("@ss.hasPermission('ssm:picture:delete')")
    public CommonResult<Boolean> deletePictureList(@RequestParam("ids") List<Long> ids) {
        pictureService.deletePictureListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得图片")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ssm:picture:query')")
    public CommonResult<PictureRespVO> getPicture(@RequestParam("id") Long id) {
        PictureDO picture = pictureService.getPicture(id);
        return success(BeanUtils.toBean(picture, PictureRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得图片分页")
    @PreAuthorize("@ss.hasPermission('ssm:picture:query')")
    public CommonResult<PageResult<PictureRespVO>> getPicturePage(@Valid PicturePageReqVO pageReqVO) {
        pageReqVO.setStatus("已处理");
        PageResult<PictureDO> pageResult = pictureService.getPicturePage(pageReqVO);
        List<PictureRespVO> ps = new ArrayList<>();
        for(PictureDO p:pageResult.getList()){
            QueryWrapper<PictureDO> q = new QueryWrapper();
            q.eq("parent_id",p.getId());
            List<PictureDO> pictureDOS = pictureMapper.selectList(q);
            PictureRespVO v = BeanUtils.toBean(p, PictureRespVO.class);
            v.setChildren(BeanUtils.toBean(pictureDOS, PictureRespVO.class));
            ps.add(v);
        }
        PageResult<PictureRespVO> page = new PageResult<PictureRespVO>();
        page.setList(ps);
        page.setTotal(pageResult.getTotal());
        return success(page);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出图片 Excel")
    @PreAuthorize("@ss.hasPermission('ssm:picture:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPictureExcel(@Valid PicturePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PictureDO> list = pictureService.getPicturePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "图片.xls", "数据", PictureRespVO.class,
                        BeanUtils.toBean(list, PictureRespVO.class));
    }

}
