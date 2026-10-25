package cn.iocoder.yudao.module.system.service.student3;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.student3.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.Student3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentCourse3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentGrade3DO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 学生 Service 接口
 *
 * @author 芋道源码
 */
public interface Student3Service {

    /**
     * 创建学生
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStudent3(@Valid Student3SaveReqVO createReqVO);

    /**
     * 更新学生
     *
     * @param updateReqVO 更新信息
     */
    void updateStudent3(@Valid Student3SaveReqVO updateReqVO);

    /**
     * 删除学生
     *
     * @param id 编号
     */
    void deleteStudent3(Long id);

    /**
    * 批量删除学生
    *
    * @param ids 编号
    */
    void deleteStudent3ListByIds(List<Long> ids);

    /**
     * 获得学生
     *
     * @param id 编号
     * @return 学生
     */
    Student3DO getStudent3(Long id);

    /**
     * 获得学生分页
     *
     * @param pageReqVO 分页查询
     * @return 学生分页
     */
    PageResult<Student3DO> getStudent3Page(Student3PageReqVO pageReqVO);

    // ==================== 子表（学生课程） ====================

    /**
     * 获得学生课程分页
     *
     * @param pageReqVO 分页查询
     * @param studentId 学生编号
     * @return 学生课程分页
     */
    PageResult<StudentCourse3DO> getStudentCourse3Page(PageParam pageReqVO, Long studentId);

    /**
     * 创建学生课程
     *
     * @param studentCourse3 创建信息
     * @return 编号
     */
    Long createStudentCourse3(@Valid StudentCourse3DO studentCourse3);

    /**
     * 更新学生课程
     *
     * @param studentCourse3 更新信息
     */
    void updateStudentCourse3(@Valid StudentCourse3DO studentCourse3);

    /**
     * 删除学生课程
     *
     * @param id 编号
     */
    void deleteStudentCourse3(Long id);

    /**
    * 批量删除学生课程
    *
    * @param ids 编号
    */
    void deleteStudentCourse3ListByIds(List<Long> ids);

	/**
	 * 获得学生课程
	 *
	 * @param id 编号
     * @return 学生课程
	 */
    StudentCourse3DO getStudentCourse3(Long id);

    // ==================== 子表（学生班级） ====================

    /**
     * 获得学生班级分页
     *
     * @param pageReqVO 分页查询
     * @param studentId 学生编号
     * @return 学生班级分页
     */
    PageResult<StudentGrade3DO> getStudentGrade3Page(PageParam pageReqVO, Long studentId);

    /**
     * 创建学生班级
     *
     * @param studentGrade3 创建信息
     * @return 编号
     */
    Long createStudentGrade3(@Valid StudentGrade3DO studentGrade3);

    /**
     * 更新学生班级
     *
     * @param studentGrade3 更新信息
     */
    void updateStudentGrade3(@Valid StudentGrade3DO studentGrade3);

    /**
     * 删除学生班级
     *
     * @param id 编号
     */
    void deleteStudentGrade3(Long id);

    /**
    * 批量删除学生班级
    *
    * @param ids 编号
    */
    void deleteStudentGrade3ListByIds(List<Long> ids);

	/**
	 * 获得学生班级
	 *
	 * @param id 编号
     * @return 学生班级
	 */
    StudentGrade3DO getStudentGrade3(Long id);

}