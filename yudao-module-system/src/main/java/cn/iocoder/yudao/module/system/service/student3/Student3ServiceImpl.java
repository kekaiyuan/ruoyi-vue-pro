package cn.iocoder.yudao.module.system.service.student3;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.student3.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.Student3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentCourse3DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentGrade3DO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.student3.Student3Mapper;
import cn.iocoder.yudao.module.system.dal.mysql.student3.StudentCourse3Mapper;
import cn.iocoder.yudao.module.system.dal.mysql.student3.StudentGrade3Mapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 学生 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class Student3ServiceImpl implements Student3Service {

    @Resource
    private Student3Mapper student3Mapper;
    @Resource
    private StudentCourse3Mapper studentCourse3Mapper;
    @Resource
    private StudentGrade3Mapper studentGrade3Mapper;

    @Override
    public Long createStudent3(Student3SaveReqVO createReqVO) {
        // 插入
        Student3DO student3 = BeanUtils.toBean(createReqVO, Student3DO.class);
        student3Mapper.insert(student3);

        // 返回
        return student3.getId();
    }

    @Override
    public void updateStudent3(Student3SaveReqVO updateReqVO) {
        // 校验存在
        validateStudent3Exists(updateReqVO.getId());
        // 更新
        Student3DO updateObj = BeanUtils.toBean(updateReqVO, Student3DO.class);
        student3Mapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudent3(Long id) {
        // 校验存在
        validateStudent3Exists(id);
        // 删除
        student3Mapper.deleteById(id);

        // 删除子表
        deleteStudentCourse3ByStudentId(id);
        deleteStudentGrade3ByStudentId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteStudent3ListByIds(List<Long> ids) {
        // 删除
        student3Mapper.deleteByIds(ids);
    
    // 删除子表
            deleteStudentCourse3ByStudentIds(ids);
            deleteStudentGrade3ByStudentIds(ids);
    }


    private void validateStudent3Exists(Long id) {
        if (student3Mapper.selectById(id) == null) {
            throw exception(STUDENT3_NOT_EXISTS);
        }
    }

    @Override
    public Student3DO getStudent3(Long id) {
        return student3Mapper.selectById(id);
    }

    @Override
    public PageResult<Student3DO> getStudent3Page(Student3PageReqVO pageReqVO) {
        return student3Mapper.selectPage(pageReqVO);
    }

    // ==================== 子表（学生课程） ====================

    @Override
    public PageResult<StudentCourse3DO> getStudentCourse3Page(PageParam pageReqVO, Long studentId) {
        return studentCourse3Mapper.selectPage(pageReqVO, studentId);
    }

    @Override
    public Long createStudentCourse3(StudentCourse3DO studentCourse3) {
        studentCourse3.clean(); // 清理掉创建、更新时间等相关属性值
        studentCourse3Mapper.insert(studentCourse3);
        return studentCourse3.getId();
    }

    @Override
    public void updateStudentCourse3(StudentCourse3DO studentCourse3) {
        // 校验存在
        validateStudentCourse3Exists(studentCourse3.getId());
        // 更新
        studentCourse3.clean(); // 解决更新情况下：updateTime 不更新
        studentCourse3Mapper.updateById(studentCourse3);
    }

    @Override
    public void deleteStudentCourse3(Long id) {
        // 删除
        studentCourse3Mapper.deleteById(id);
    }

	@Override
	public void deleteStudentCourse3ListByIds(List<Long> ids) {
        // 删除
        studentCourse3Mapper.deleteByIds(ids);
	}

    @Override
    public StudentCourse3DO getStudentCourse3(Long id) {
        return studentCourse3Mapper.selectById(id);
    }

    private void validateStudentCourse3Exists(Long id) {
        if (studentCourse3Mapper.selectById(id) == null) {
            throw exception(STUDENT_COURSE3_NOT_EXISTS);
        }
    }

    private void deleteStudentCourse3ByStudentId(Long studentId) {
        studentCourse3Mapper.deleteByStudentId(studentId);
    }

	private void deleteStudentCourse3ByStudentIds(List<Long> studentIds) {
        studentCourse3Mapper.deleteByStudentIds(studentIds);
	}

    // ==================== 子表（学生班级） ====================

    @Override
    public PageResult<StudentGrade3DO> getStudentGrade3Page(PageParam pageReqVO, Long studentId) {
        return studentGrade3Mapper.selectPage(pageReqVO, studentId);
    }

    @Override
    public Long createStudentGrade3(StudentGrade3DO studentGrade3) {
        studentGrade3.clean(); // 清理掉创建、更新时间等相关属性值
        studentGrade3Mapper.insert(studentGrade3);
        return studentGrade3.getId();
    }

    @Override
    public void updateStudentGrade3(StudentGrade3DO studentGrade3) {
        // 校验存在
        validateStudentGrade3Exists(studentGrade3.getId());
        // 更新
        studentGrade3.clean(); // 解决更新情况下：updateTime 不更新
        studentGrade3Mapper.updateById(studentGrade3);
    }

    @Override
    public void deleteStudentGrade3(Long id) {
        // 删除
        studentGrade3Mapper.deleteById(id);
    }

	@Override
	public void deleteStudentGrade3ListByIds(List<Long> ids) {
        // 删除
        studentGrade3Mapper.deleteByIds(ids);
	}

    @Override
    public StudentGrade3DO getStudentGrade3(Long id) {
        return studentGrade3Mapper.selectById(id);
    }

    private void validateStudentGrade3Exists(Long id) {
        if (studentGrade3Mapper.selectById(id) == null) {
            throw exception(STUDENT_GRADE3_NOT_EXISTS);
        }
    }

    private void deleteStudentGrade3ByStudentId(Long studentId) {
        studentGrade3Mapper.deleteByStudentId(studentId);
    }

	private void deleteStudentGrade3ByStudentIds(List<Long> studentIds) {
        studentGrade3Mapper.deleteByStudentIds(studentIds);
	}

}