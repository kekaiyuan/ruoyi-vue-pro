package cn.iocoder.yudao.module.system.controller.admin.student2;

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

import cn.iocoder.yudao.module.system.controller.admin.student2.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.Student2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentCourse2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentGrade2DO;
import cn.iocoder.yudao.module.system.service.student2.Student2Service;

@Tag(name = "管理后台 - 学生")
@RestController
@RequestMapping("/system/student2")
@Validated
public class Student2Controller {

    @Resource
    private Student2Service student2Service;

    @PostMapping("/create")
    @Operation(summary = "创建学生")
    @PreAuthorize("@ss.hasPermission('system:student2:create')")
    public CommonResult<Long> createStudent2(@Valid @RequestBody Student2SaveReqVO createReqVO) {
        return success(student2Service.createStudent2(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学生")
    @PreAuthorize("@ss.hasPermission('system:student2:update')")
    public CommonResult<Boolean> updateStudent2(@Valid @RequestBody Student2SaveReqVO updateReqVO) {
        student2Service.updateStudent2(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学生")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:student2:delete')")
    public CommonResult<Boolean> deleteStudent2(@RequestParam("id") Long id) {
        student2Service.deleteStudent2(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除学生")
                @PreAuthorize("@ss.hasPermission('system:student2:delete')")
    public CommonResult<Boolean> deleteStudent2List(@RequestParam("ids") List<Long> ids) {
        student2Service.deleteStudent2ListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得学生")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:student2:query')")
    public CommonResult<Student2RespVO> getStudent2(@RequestParam("id") Long id) {
        Student2DO student2 = student2Service.getStudent2(id);
        return success(BeanUtils.toBean(student2, Student2RespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学生分页")
    @PreAuthorize("@ss.hasPermission('system:student2:query')")
    public CommonResult<PageResult<Student2RespVO>> getStudent2Page(@Valid Student2PageReqVO pageReqVO) {
        PageResult<Student2DO> pageResult = student2Service.getStudent2Page(pageReqVO);
        return success(BeanUtils.toBean(pageResult, Student2RespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学生 Excel")
    @PreAuthorize("@ss.hasPermission('system:student2:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStudent2Excel(@Valid Student2PageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Student2DO> list = student2Service.getStudent2Page(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "学生.xls", "数据", Student2RespVO.class,
                        BeanUtils.toBean(list, Student2RespVO.class));
    }

    // ==================== 子表（学生课程） ====================

    @GetMapping("/student-course2/list-by-student-id")
    @Operation(summary = "获得学生课程列表")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('system:student2:query')")
    public CommonResult<List<StudentCourse2DO>> getStudentCourse2ListByStudentId(@RequestParam("studentId") Long studentId) {
        return success(student2Service.getStudentCourse2ListByStudentId(studentId));
    }

    // ==================== 子表（学生班级） ====================

    @GetMapping("/student-grade2/list-by-student-id")
    @Operation(summary = "获得学生班级列表")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('system:student2:query')")
    public CommonResult<List<StudentGrade2DO>> getStudentGrade2ListByStudentId(@RequestParam("studentId") Long studentId) {
        return success(student2Service.getStudentGrade2ListByStudentId(studentId));
    }

}