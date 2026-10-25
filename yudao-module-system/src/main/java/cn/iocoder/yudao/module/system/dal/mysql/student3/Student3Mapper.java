package cn.iocoder.yudao.module.system.dal.mysql.student3;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student3.Student3DO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.student3.vo.*;

/**
 * 学生 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface Student3Mapper extends BaseMapperX<Student3DO> {

    default PageResult<Student3DO> selectPage(Student3PageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Student3DO>()
                .likeIfPresent(Student3DO::getName, reqVO.getName())
                .eqIfPresent(Student3DO::getBirthday, reqVO.getBirthday())
                .eqIfPresent(Student3DO::getDescription, reqVO.getDescription())
                .betweenIfPresent(Student3DO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(Student3DO::getId));
    }

}