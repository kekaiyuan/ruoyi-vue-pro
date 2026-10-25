package cn.iocoder.yudao.module.system.controller.admin.student3;

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

import cn.iocoder.yudao.module.system.controller.admin.student3.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.Student3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentCourse3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentGrade3DO;
import cn.iocoder.yudao.module.system.service.student3.Student3Service;

@Tag(name = "管理后台 - 学生")
@RestController
@RequestMapping("/system/student3")
@Validated
public class Student3Controller {

    @Resource
    private Student3Service student3Service;

    @PostMapping("/create")
    @Operation(summary = "创建学生")
    @PreAuthorize("@ss.hasPermission('system:student3:create')")
    public CommonResult<Long> createStudent3(@Valid @RequestBody Student3SaveReqVO createReqVO) {
        return success(student3Service.createStudent3(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学生")
    @PreAuthorize("@ss.hasPermission('system:student3:update')")
    public CommonResult<Boolean> updateStudent3(@Valid @RequestBody Student3SaveReqVO updateReqVO) {
        student3Service.updateStudent3(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学生")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudent3(@RequestParam("id") Long id) {
        student3Service.deleteStudent3(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除学生")
                @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudent3List(@RequestParam("ids") List<Long> ids) {
        student3Service.deleteStudent3ListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得学生")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
    public CommonResult<Student3RespVO> getStudent3(@RequestParam("id") Long id) {
        Student3DO student3 = student3Service.getStudent3(id);
        return success(BeanUtils.toBean(student3, Student3RespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学生分页")
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
    public CommonResult<PageResult<Student3RespVO>> getStudent3Page(@Valid Student3PageReqVO pageReqVO) {
        PageResult<Student3DO> pageResult = student3Service.getStudent3Page(pageReqVO);
        return success(BeanUtils.toBean(pageResult, Student3RespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学生 Excel")
    @PreAuthorize("@ss.hasPermission('system:student3:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStudent3Excel(@Valid Student3PageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Student3DO> list = student3Service.getStudent3Page(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "学生.xls", "数据", Student3RespVO.class,
                        BeanUtils.toBean(list, Student3RespVO.class));
    }

    // ==================== 子表（学生课程） ====================

    @GetMapping("/student-course3/page")
    @Operation(summary = "获得学生课程分页")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
    public CommonResult<PageResult<StudentCourse3DO>> getStudentCourse3Page(PageParam pageReqVO,
                                                                                        @RequestParam("studentId") Long studentId) {
        return success(student3Service.getStudentCourse3Page(pageReqVO, studentId));
    }

    @PostMapping("/student-course3/create")
    @Operation(summary = "创建学生课程")
    @PreAuthorize("@ss.hasPermission('system:student3:create')")
    public CommonResult<Long> createStudentCourse3(@Valid @RequestBody StudentCourse3DO studentCourse3) {
        return success(student3Service.createStudentCourse3(studentCourse3));
    }

    @PutMapping("/student-course3/update")
    @Operation(summary = "更新学生课程")
    @PreAuthorize("@ss.hasPermission('system:student3:update')")
    public CommonResult<Boolean> updateStudentCourse3(@Valid @RequestBody StudentCourse3DO studentCourse3) {
        student3Service.updateStudentCourse3(studentCourse3);
        return success(true);
    }

    @DeleteMapping("/student-course3/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除学生课程")
    @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudentCourse3(@RequestParam("id") Long id) {
        student3Service.deleteStudentCourse3(id);
        return success(true);
    }

    @DeleteMapping("/student-course3/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除学生课程")
    @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudentCourse3List(@RequestParam("ids") List<Long> ids) {
        student3Service.deleteStudentCourse3ListByIds(ids);
        return success(true);
    }

	@GetMapping("/student-course3/get")
	@Operation(summary = "获得学生课程")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
	public CommonResult<StudentCourse3DO> getStudentCourse3(@RequestParam("id") Long id) {
	    return success(student3Service.getStudentCourse3(id));
	}

    // ==================== 子表（学生班级） ====================

    @GetMapping("/student-grade3/page")
    @Operation(summary = "获得学生班级分页")
    @Parameter(name = "studentId", description = "学生编号")
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
    public CommonResult<PageResult<StudentGrade3DO>> getStudentGrade3Page(PageParam pageReqVO,
                                                                                        @RequestParam("studentId") Long studentId) {
        return success(student3Service.getStudentGrade3Page(pageReqVO, studentId));
    }

    @PostMapping("/student-grade3/create")
    @Operation(summary = "创建学生班级")
    @PreAuthorize("@ss.hasPermission('system:student3:create')")
    public CommonResult<Long> createStudentGrade3(@Valid @RequestBody StudentGrade3DO studentGrade3) {
        return success(student3Service.createStudentGrade3(studentGrade3));
    }

    @PutMapping("/student-grade3/update")
    @Operation(summary = "更新学生班级")
    @PreAuthorize("@ss.hasPermission('system:student3:update')")
    public CommonResult<Boolean> updateStudentGrade3(@Valid @RequestBody StudentGrade3DO studentGrade3) {
        student3Service.updateStudentGrade3(studentGrade3);
        return success(true);
    }

    @DeleteMapping("/student-grade3/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除学生班级")
    @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudentGrade3(@RequestParam("id") Long id) {
        student3Service.deleteStudentGrade3(id);
        return success(true);
    }

    @DeleteMapping("/student-grade3/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除学生班级")
    @PreAuthorize("@ss.hasPermission('system:student3:delete')")
    public CommonResult<Boolean> deleteStudentGrade3List(@RequestParam("ids") List<Long> ids) {
        student3Service.deleteStudentGrade3ListByIds(ids);
        return success(true);
    }

	@GetMapping("/student-grade3/get")
	@Operation(summary = "获得学生班级")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:student3:query')")
	public CommonResult<StudentGrade3DO> getStudentGrade3(@RequestParam("id") Long id) {
	    return success(student3Service.getStudentGrade3(id));
	}

}