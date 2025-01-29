package com.bjsxt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjsxt.domain.SysPrivilege;

import java.util.List;

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
    /**
     * 使用角色Id 查询权限
     * @param roleId
     * @return
     */
    List<SysPrivilege> selectByRoleId(Long roleId);
}