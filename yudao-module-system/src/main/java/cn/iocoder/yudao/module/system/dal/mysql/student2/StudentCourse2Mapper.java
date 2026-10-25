package cn.iocoder.yudao.module.system.dal.mysql.student2;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.StudentCourse2DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生课程 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StudentCourse2Mapper extends BaseMapperX<StudentCourse2DO> {

    default List<StudentCourse2DO> selectListByStudentId(Long studentId) {
        return selectList(StudentCourse2DO::getStudentId, studentId);
    }

    default int deleteByStudentId(Long studentId) {
        return delete(StudentCourse2DO::getStudentId, studentId);
    }

	default int deleteByStudentIds(List<Long> studentIds) {
	    return deleteBatch(StudentCourse2DO::getStudentId, studentIds);
	}

}