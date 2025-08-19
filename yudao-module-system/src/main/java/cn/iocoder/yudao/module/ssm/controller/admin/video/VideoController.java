package cn.iocoder.yudao.module.ssm.controller.admin.video;

import cn.iocoder.yudao.module.ssm.controller.admin.picture.vo.PictureRespVO;
import cn.iocoder.yudao.module.ssm.dal.dataobject.picture.PictureDO;
import cn.iocoder.yudao.module.ssm.dal.mysql.video.VideoMapper;
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

import cn.iocoder.yudao.module.ssm.controller.admin.video.vo.*;
import cn.iocoder.yudao.module.ssm.dal.dataobject.video.VideoDO;
import cn.iocoder.yudao.module.ssm.service.video.VideoService;

@Tag(name = "管理后台 - 视频")
@RestController
@RequestMapping("/ssm/video")
@Validated
public class VideoController {

    @Resource
    private VideoService videoService;
    @Resource
    private VideoMapper videoMapper;

    @PostMapping("/create")
    @Operation(summary = "创建视频")
    @PreAuthorize("@ss.hasPermission('ssm:video:create')")
    public CommonResult<Long> createVideo(@Valid @RequestBody VideoSaveReqVO createReqVO) {
        return success(videoService.createVideo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新视频")
    @PreAuthorize("@ss.hasPermission('ssm:video:update')")
    public CommonResult<Boolean> updateVideo(@Valid @RequestBody VideoSaveReqVO updateReqVO) {
        videoService.updateVideo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除视频")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ssm:video:delete')")
    public CommonResult<Boolean> deleteVideo(@RequestParam("id") Long id) {
        videoService.deleteVideo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除视频")
                @PreAuthorize("@ss.hasPermission('ssm:video:delete')")
    public CommonResult<Boolean> deleteVideoList(@RequestParam("ids") List<Long> ids) {
        videoService.deleteVideoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得视频")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ssm:video:query')")
    public CommonResult<VideoRespVO> getVideo(@RequestParam("id") Long id) {
        VideoDO video = videoService.getVideo(id);
        return success(BeanUtils.toBean(video, VideoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得视频分页")
    @PreAuthorize("@ss.hasPermission('ssm:video:query')")
    public CommonResult<PageResult<VideoRespVO>> getVideoPage(@Valid VideoPageReqVO pageReqVO) {
        pageReqVO.setStatus("已处理");
        PageResult<VideoDO> pageResult = videoService.getVideoPage(pageReqVO);
        List<VideoRespVO> ps = new ArrayList<>();
        for(VideoDO p:pageResult.getList()){
            QueryWrapper<VideoDO> q = new QueryWrapper();
            q.eq("parent_id",p.getId());
            List<VideoDO> pictureDOS = videoMapper.selectList(q);
            VideoRespVO v = BeanUtils.toBean(p, VideoRespVO.class);
            v.setChildren(BeanUtils.toBean(pictureDOS, VideoRespVO.class));
            ps.add(v);
        }
        PageResult<VideoRespVO> page = new PageResult<VideoRespVO>();
        page.setList(ps);
        page.setTotal(pageResult.getTotal());
        return success(page);

    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出视频 Excel")
    @PreAuthorize("@ss.hasPermission('ssm:video:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVideoExcel(@Valid VideoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VideoDO> list = videoService.getVideoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "视频.xls", "数据", VideoRespVO.class,
                        BeanUtils.toBean(list, VideoRespVO.class));
    }

}
