package cn.iocoder.yudao.module.system.service.student;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.student.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student.StudentDO;
import cn.iocoder.yudao.module.system.dal.dataobject.student.StudentCourseDO;
import cn.iocoder.yudao.module.system.dal.dataobject.student.StudentGradeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.student.StudentMapper;
import cn.iocoder.yudao.module.system.dal.mysql.student.StudentCourseMapper;
import cn.iocoder.yudao.module.system.dal.mysql.student.StudentGradeMapper;

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
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentCourseMapper studentCourseMapper;
    @Resource
    private StudentGradeMapper studentGradeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStudent(StudentSaveReqVO createReqVO) {
        // 插入
        StudentDO student = BeanUtils.toBean(createReqVO, StudentDO.class);
        studentMapper.insert(student);


        // 插入子表
        createStudentCourseList(student.getId(), createReqVO.getStudentCourses());
        createStudentGrade(student.getId(), createReqVO.getStudentGrade());
        // 返回
        return student.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudent(StudentSaveReqVO updateReqVO) {
        // 校验存在
        validateStudentExists(updateReqVO.getId());
        // 更新
        StudentDO updateObj = BeanUtils.toBean(updateReqVO, StudentDO.class);
        studentMapper.updateById(updateObj);

        // 更新子表
        updateStudentCourseList(updateReqVO.getId(), updateReqVO.getStudentCourses());
        updateStudentGrade(updateReqVO.getId(), updateReqVO.getStudentGrade());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudent(Long id) {
        // 校验存在
        validateStudentExists(id);
        // 删除
        studentMapper.deleteById(id);

        // 删除子表
        deleteStudentCourseByStudentId(id);
        deleteStudentGradeByStudentId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteStudentListByIds(List<Long> ids) {
        // 删除
        studentMapper.deleteByIds(ids);
    
    // 删除子表
            deleteStudentCourseByStudentIds(ids);
            deleteStudentGradeByStudentIds(ids);
    }


    private void validateStudentExists(Long id) {
        if (studentMapper.selectById(id) == null) {
            throw exception(STUDENT_NOT_EXISTS);
        }
    }

    @Override
    public StudentDO getStudent(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public PageResult<StudentDO> getStudentPage(StudentPageReqVO pageReqVO) {
        return studentMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（学生课程） ====================

    @Override
    public List<StudentCourseDO> getStudentCourseListByStudentId(Long studentId) {
        return studentCourseMapper.selectListByStudentId(studentId);
    }

    private void createStudentCourseList(Long studentId, List<StudentCourseDO> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(o -> o.setStudentId(studentId).clean());
        studentCourseMapper.insertBatch(list);
    }

    private void updateStudentCourseList(Long studentId, List<StudentCourseDO> list) {
	    list.forEach(o -> o.setStudentId(studentId).clean());
	    List<StudentCourseDO> oldList = studentCourseMapper.selectListByStudentId(studentId);
	    List<List<StudentCourseDO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
	    });

	    // 第二步，批量添加、修改、删除
	    if (CollUtil.isNotEmpty(diffList.get(0))) {
	        studentCourseMapper.insertBatch(diffList.get(0));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(1))) {
	        studentCourseMapper.updateBatch(diffList.get(1));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(2))) {
	        studentCourseMapper.deleteByIds(convertList(diffList.get(2), StudentCourseDO::getId));
	    }
    }

    private void deleteStudentCourseByStudentId(Long studentId) {
        studentCourseMapper.deleteByStudentId(studentId);
    }

	private void deleteStudentCourseByStudentIds(List<Long> studentIds) {
        studentCourseMapper.deleteByStudentIds(studentIds);
	}

    // ==================== 子表（学生班级） ====================

    @Override
    public StudentGradeDO getStudentGradeByStudentId(Long studentId) {
        return studentGradeMapper.selectByStudentId(studentId);
    }

    private void createStudentGrade(Long studentId, StudentGradeDO studentGrade) {
        if (studentGrade == null) {
            return;
        }
        studentGrade.setStudentId(studentId);
        studentGradeMapper.insert(studentGrade);
    }

    private void updateStudentGrade(Long studentId, StudentGradeDO studentGrade) {
        if (studentGrade == null) {
			return;
        }
        studentGrade.setStudentId(studentId).clean();// 解决更新情况下：updateTime 不更新
        studentGradeMapper.insertOrUpdate(studentGrade);
    }

    private void deleteStudentGradeByStudentId(Long studentId) {
        studentGradeMapper.deleteByStudentId(studentId);
    }

	private void deleteStudentGradeByStudentIds(List<Long> studentIds) {
        studentGradeMapper.deleteByStudentIds(studentIds);
	}

}