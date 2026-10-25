package cn.iocoder.yudao.module.system.controller.admin.groupa;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
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

import cn.iocoder.yudao.module.system.controller.admin.groupa.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.groupa.GroupADO;
import cn.iocoder.yudao.module.system.service.groupa.GroupAService;

@Tag(name = "管理后台 - 用户组")
@RestController
@RequestMapping("/system/group-a")
@Validated
public class GroupAController {

    @Resource
    private GroupAService groupAService;

    @PostMapping("/create")
    @Operation(summary = "创建用户组")
    @PreAuthorize("@ss.hasPermission('system:group-a:create')")
    public CommonResult<Long> createGroupA(@Valid @RequestBody GroupASaveReqVO createReqVO) {
        return success(groupAService.createGroupA(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户组")
    @PreAuthorize("@ss.hasPermission('system:group-a:update')")
    public CommonResult<Boolean> updateGroupA(@Valid @RequestBody GroupASaveReqVO updateReqVO) {
        groupAService.updateGroupA(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:group-a:delete')")
    public CommonResult<Boolean> deleteGroupA(@RequestParam("id") Long id) {
        groupAService.deleteGroupA(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除用户组")
                @PreAuthorize("@ss.hasPermission('system:group-a:delete')")
    public CommonResult<Boolean> deleteGroupAList(@RequestParam("ids") List<Long> ids) {
        groupAService.deleteGroupAListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:group-a:query')")
    public CommonResult<GroupARespVO> getGroupA(@RequestParam("id") Long id) {
        GroupADO groupA = groupAService.getGroupA(id);
        return success(BeanUtils.toBean(groupA, GroupARespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户组分页")
    @PreAuthorize("@ss.hasPermission('system:group-a:query')")
    public CommonResult<PageResult<GroupARespVO>> getGroupAPage(@Valid GroupAPageReqVO pageReqVO) {
        PageResult<GroupADO> pageResult = groupAService.getGroupAPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, GroupARespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户组 Excel")
    @PreAuthorize("@ss.hasPermission('system:group-a:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportGroupAExcel(@Valid GroupAPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<GroupADO> list = groupAService.getGroupAPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户组.xls", "数据", GroupARespVO.class,
                        BeanUtils.toBean(list, GroupARespVO.class));
    }

}