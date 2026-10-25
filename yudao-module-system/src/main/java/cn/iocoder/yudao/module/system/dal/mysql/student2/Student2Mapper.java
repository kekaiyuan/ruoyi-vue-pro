package cn.iocoder.yudao.module.system.dal.mysql.student2;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.student2.Student2DO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.student2.vo.*;

/**
 * 学生 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface Student2Mapper extends BaseMapperX<Student2DO> {

    default PageResult<Student2DO> selectPage(Student2PageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Student2DO>()
                .likeIfPresent(Student2DO::getName, reqVO.getName())
                .eqIfPresent(Student2DO::getBirthday, reqVO.getBirthday())
                .eqIfPresent(Student2DO::getDescription, reqVO.getDescription())
                .betweenIfPresent(Student2DO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(Student2DO::getId));
    }

}