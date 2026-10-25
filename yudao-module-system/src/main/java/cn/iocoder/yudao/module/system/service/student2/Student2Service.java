package cn.iocoder.yudao.module.system.service.student2;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.student2.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.Student2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentCourse2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentGrade2DO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 学生 Service 接口
 *
 * @author 芋道源码
 */
public interface Student2Service {

    /**
     * 创建学生
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStudent2(@Valid Student2SaveReqVO createReqVO);

    /**
     * 更新学生
     *
     * @param updateReqVO 更新信息
     */
    void updateStudent2(@Valid Student2SaveReqVO updateReqVO);

    /**
     * 删除学生
     *
     * @param id 编号
     */
    void deleteStudent2(Long id);

    /**
    * 批量删除学生
    *
    * @param ids 编号
    */
    void deleteStudent2ListByIds(List<Long> ids);

    /**
     * 获得学生
     *
     * @param id 编号
     * @return 学生
     */
    Student2DO getStudent2(Long id);

    /**
     * 获得学生分页
     *
     * @param pageReqVO 分页查询
     * @return 学生分页
     */
    PageResult<Student2DO> getStudent2Page(Student2PageReqVO pageReqVO);

    // ==================== 子表（学生课程） ====================

    /**
     * 获得学生课程列表
     *
     * @param studentId 学生编号
     * @return 学生课程列表
     */
    List<StudentCourse2DO> getStudentCourse2ListByStudentId(Long studentId);

    // ==================== 子表（学生班级） ====================

    /**
     * 获得学生班级列表
     *
     * @param studentId 学生编号
     * @return 学生班级列表
     */
    List<StudentGrade2DO> getStudentGrade2ListByStudentId(Long studentId);

}