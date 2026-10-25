package cn.iocoder.yudao.module.system.service.student2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.student2.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.Student2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentCourse2DO;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentGrade2DO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.student2.Student2Mapper;
import cn.iocoder.yudao.module.system.dal.mysql.student2.StudentCourse2Mapper;
import cn.iocoder.yudao.module.system.dal.mysql.student2.StudentGrade2Mapper;

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
public class Student2ServiceImpl implements Student2Service {

    @Resource
    private Student2Mapper student2Mapper;
    @Resource
    private StudentCourse2Mapper studentCourse2Mapper;
    @Resource
    private StudentGrade2Mapper studentGrade2Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStudent2(Student2SaveReqVO createReqVO) {
        // 插入
        Student2DO student2 = BeanUtils.toBean(createReqVO, Student2DO.class);
        student2Mapper.insert(student2);


        // 插入子表
        createStudentCourse2List(student2.getId(), createReqVO.getStudentCourse2s());
        createStudentGrade2List(student2.getId(), createReqVO.getStudentGrade2s());
        // 返回
        return student2.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudent2(Student2SaveReqVO updateReqVO) {
        // 校验存在
        validateStudent2Exists(updateReqVO.getId());
        // 更新
        Student2DO updateObj = BeanUtils.toBean(updateReqVO, Student2DO.class);
        student2Mapper.updateById(updateObj);

        // 更新子表
        updateStudentCourse2List(updateReqVO.getId(), updateReqVO.getStudentCourse2s());
        updateStudentGrade2List(updateReqVO.getId(), updateReqVO.getStudentGrade2s());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudent2(Long id) {
        // 校验存在
        validateStudent2Exists(id);
        // 删除
        student2Mapper.deleteById(id);

        // 删除子表
        deleteStudentCourse2ByStudentId(id);
        deleteStudentGrade2ByStudentId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteStudent2ListByIds(List<Long> ids) {
        // 删除
        student2Mapper.deleteByIds(ids);
    
    // 删除子表
            deleteStudentCourse2ByStudentIds(ids);
            deleteStudentGrade2ByStudentIds(ids);
    }


    private void validateStudent2Exists(Long id) {
        if (student2Mapper.selectById(id) == null) {
            throw exception(STUDENT2_NOT_EXISTS);
        }
    }

    @Override
    public Student2DO getStudent2(Long id) {
        return student2Mapper.selectById(id);
    }

    @Override
    public PageResult<Student2DO> getStudent2Page(Student2PageReqVO pageReqVO) {
        return student2Mapper.selectPage(pageReqVO);
    }

    // ==================== 子表（学生课程） ====================

    @Override
    public List<StudentCourse2DO> getStudentCourse2ListByStudentId(Long studentId) {
        return studentCourse2Mapper.selectListByStudentId(studentId);
    }

    private void createStudentCourse2List(Long studentId, List<StudentCourse2DO> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(o -> o.setStudentId(studentId).clean());
        studentCourse2Mapper.insertBatch(list);
    }

    private void updateStudentCourse2List(Long studentId, List<StudentCourse2DO> list) {
	    list.forEach(o -> o.setStudentId(studentId).clean());
	    List<StudentCourse2DO> oldList = studentCourse2Mapper.selectListByStudentId(studentId);
	    List<List<StudentCourse2DO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
	    });

	    // 第二步，批量添加、修改、删除
	    if (CollUtil.isNotEmpty(diffList.get(0))) {
	        studentCourse2Mapper.insertBatch(diffList.get(0));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(1))) {
	        studentCourse2Mapper.updateBatch(diffList.get(1));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(2))) {
	        studentCourse2Mapper.deleteByIds(convertList(diffList.get(2), StudentCourse2DO::getId));
	    }
    }

    private void deleteStudentCourse2ByStudentId(Long studentId) {
        studentCourse2Mapper.deleteByStudentId(studentId);
    }

	private void deleteStudentCourse2ByStudentIds(List<Long> studentIds) {
        studentCourse2Mapper.deleteByStudentIds(studentIds);
	}

    // ==================== 子表（学生班级） ====================

    @Override
    public List<StudentGrade2DO> getStudentGrade2ListByStudentId(Long studentId) {
        return studentGrade2Mapper.selectListByStudentId(studentId);
    }

    private void createStudentGrade2List(Long studentId, List<StudentGrade2DO> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(o -> o.setStudentId(studentId).clean());
        studentGrade2Mapper.insertBatch(list);
    }

    private void updateStudentGrade2List(Long studentId, List<StudentGrade2DO> list) {
	    list.forEach(o -> o.setStudentId(studentId).clean());
	    List<StudentGrade2DO> oldList = studentGrade2Mapper.selectListByStudentId(studentId);
	    List<List<StudentGrade2DO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
	    });

	    // 第二步，批量添加、修改、删除
	    if (CollUtil.isNotEmpty(diffList.get(0))) {
	        studentGrade2Mapper.insertBatch(diffList.get(0));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(1))) {
	        studentGrade2Mapper.updateBatch(diffList.get(1));
	    }
	    if (CollUtil.isNotEmpty(diffList.get(2))) {
	        studentGrade2Mapper.deleteByIds(convertList(diffList.get(2), StudentGrade2DO::getId));
	    }
    }

    private void deleteStudentGrade2ByStudentId(Long studentId) {
        studentGrade2Mapper.deleteByStudentId(studentId);
    }

	private void deleteStudentGrade2ByStudentIds(List<Long> studentIds) {
        studentGrade2Mapper.deleteByStudentIds(studentIds);
	}

}