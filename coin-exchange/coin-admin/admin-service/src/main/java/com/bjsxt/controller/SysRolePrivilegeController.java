package com.bjsxt.controller;


import com.bjsxt.domain.SysMenu;
import com.bjsxt.model.R;
import com.bjsxt.service.SysMenuService;
import com.bjsxt.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysRolePrivilegeController {

    @Autowired
    private SysMenuService sysMenuService ;

    @Autowired
    private SysRoleService sysRoleService ;


    @GetMapping("/roles_privileges")
    public R<List<SysMenu>>  getSysRolePrivilege(Long roleId){
        List<SysMenu> sysRole = sysMenuService.getSysMenuPrivilegesByRoleId(roleId) ;
        return R.ok(sysRole) ;
    }
}
