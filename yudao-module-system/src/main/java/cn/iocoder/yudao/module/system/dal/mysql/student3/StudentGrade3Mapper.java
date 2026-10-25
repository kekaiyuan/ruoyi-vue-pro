package cn.iocoder.yudao.module.system.dal.mysql.student3;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.StudentGrade3DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生班级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StudentGrade3Mapper extends BaseMapperX<StudentGrade3DO> {

    default PageResult<StudentGrade3DO> selectPage(PageParam reqVO, Long studentId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StudentGrade3DO>()
            .eq(StudentGrade3DO::getStudentId, studentId)
            .orderByDesc(StudentGrade3DO::getId));
    }

    default int deleteByStudentId(Long studentId) {
        return delete(StudentGrade3DO::getStudentId, studentId);
    }

	default int deleteByStudentIds(List<Long> studentIds) {
	    return deleteBatch(StudentGrade3DO::getStudentId, studentIds);
	}

}