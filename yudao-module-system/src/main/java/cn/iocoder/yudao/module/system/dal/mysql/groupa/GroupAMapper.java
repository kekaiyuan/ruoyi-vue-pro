package cn.iocoder.yudao.module.system.dal.mysql.groupa;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.groupa.GroupADO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.groupa.vo.*;

/**
 * 用户组 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface GroupAMapper extends BaseMapperX<GroupADO> {

    default PageResult<GroupADO> selectPage(GroupAPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GroupADO>()
                .likeIfPresent(GroupADO::getName, reqVO.getName())
                .eqIfPresent(GroupADO::getStatus, reqVO.getStatus())
                .betweenIfPresent(GroupADO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(GroupADO::getId));
    }

}