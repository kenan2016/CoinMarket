package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.SysPrivilege;
import com.bjsxt.mapper.SysPrivilegeMapper;
import com.bjsxt.service.SysPrivilegeService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService {

    /**
     * 通过菜单的Id 获取所有的权限数据
     *
     * @param menuId
     * @return
     */
    @Override
    public List<SysPrivilege> getByMenuId(Long menuId) {
        List<SysPrivilege> privileges = list(new LambdaQueryWrapper<SysPrivilege>()
                .eq(SysPrivilege::getMenuId, menuId));
        return privileges == null ? Collections.emptyList() : privileges;
    }
}
