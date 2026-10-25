package cn.iocoder.yudao.module.system.dal.mysql.student2;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentGrade2DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生班级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StudentGrade2Mapper extends BaseMapperX<StudentGrade2DO> {

    default List<StudentGrade2DO> selectListByStudentId(Long studentId) {
        return selectList(StudentGrade2DO::getStudentId, studentId);
    }

    default int deleteByStudentId(Long studentId) {
        return delete(StudentGrade2DO::getStudentId, studentId);
    }

	default int deleteByStudentIds(List<Long> studentIds) {
	    return deleteBatch(StudentGrade2DO::getStudentId, studentIds);
	}

}