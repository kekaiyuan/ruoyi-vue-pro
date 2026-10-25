package cn.iocoder.yudao.module.system.service.groupa;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.groupa.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.groupa.GroupADO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.groupa.GroupAMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户组 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class GroupAServiceImpl implements GroupAService {

    @Resource
    private GroupAMapper groupAMapper;

    @Override
    public Long createGroupA(GroupASaveReqVO createReqVO) {
        // 插入
        GroupADO groupA = BeanUtils.toBean(createReqVO, GroupADO.class);
        groupAMapper.insert(groupA);

        // 返回
        return groupA.getId();
    }

    @Override
    public void updateGroupA(GroupASaveReqVO updateReqVO) {
        // 校验存在
        validateGroupAExists(updateReqVO.getId());
        // 更新
        GroupADO updateObj = BeanUtils.toBean(updateReqVO, GroupADO.class);
        groupAMapper.updateById(updateObj);
    }

    @Override
    public void deleteGroupA(Long id) {
        // 校验存在
        validateGroupAExists(id);
        // 删除
        groupAMapper.deleteById(id);
    }

    @Override
        public void deleteGroupAListByIds(List<Long> ids) {
        // 删除
        groupAMapper.deleteByIds(ids);
        }


    private void validateGroupAExists(Long id) {
        if (groupAMapper.selectById(id) == null) {
            throw exception(GROUP_A_NOT_EXISTS);
        }
    }

    @Override
    public GroupADO getGroupA(Long id) {
        return groupAMapper.selectById(id);
    }

    @Override
    public PageResult<GroupADO> getGroupAPage(GroupAPageReqVO pageReqVO) {
        return groupAMapper.selectPage(pageReqVO);
    }

}