package cn.iocoder.yudao.module.system.dal.mysql.student3;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentCourse3DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生课程 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StudentCourse3Mapper extends BaseMapperX<StudentCourse3DO> {

    default PageResult<StudentCourse3DO> selectPage(PageParam reqVO, Long studentId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StudentCourse3DO>()
            .eq(StudentCourse3DO::getStudentId, studentId)
            .orderByDesc(StudentCourse3DO::getId));
    }

    default int deleteByStudentId(Long studentId) {
        return delete(StudentCourse3DO::getStudentId, studentId);
    }

	default int deleteByStudentIds(List<Long> studentIds) {
	    return deleteBatch(StudentCourse3DO::getStudentId, studentIds);
	}

}