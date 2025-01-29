package com.bjsxt.service;

import com.bjsxt.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege>{


    /**
     * 通过菜单的Id 获取所有的权限数据
     * @param menuId
     * @return
     */
    List<SysPrivilege> getByMenuId(Long menuId);
}
