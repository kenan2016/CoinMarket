package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.SysMenu;
import com.bjsxt.mapper.SysMenuMapper;
import com.bjsxt.mapper.SysRoleMenuMapper;
import com.bjsxt.mapper.SysRolePrivilegeMapper;
import com.bjsxt.service.SysMenuService;
import com.bjsxt.service.SysPrivilegeService;
import com.bjsxt.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService ;

    @Autowired
    private SysMenuMapper sysMenuMapper ;

    @Autowired
    private SysPrivilegeService sysPrivilegeService ;


    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper ;

    @Autowired
    private SysRolePrivilegeMapper sysRolePrivilegeMapper ;
    /**
     * 通过用户的id 查询用户的菜单数据
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 1 如果该用户是超级管理员->>拥有所有的菜单
        if(sysRoleService.isSuperAdmin(userId)){
            return list() ; // 查询所有
        }
        // 2 如果该用户不是超级管理员->>查询角色->查询菜单
        return sysMenuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<SysMenu> getSysMenuPrivilegesByRoleId(Long roleId) {
        List<SysMenu> sysMenus = list(new LambdaQueryWrapper<SysMenu>()) ;
        if(sysMenus==null || sysMenus.isEmpty()){
            return Collections.emptyList() ;
        }
        List<SysMenu> treeMenu = childes(sysMenus) ;
        return treeMenu;
    }

    /**
     * 将一个菜单转化为树形菜单
     * @param sysMenus
     * @return
     */
    private List<SysMenu> childes(List<SysMenu> sysMenus) {
        List<SysMenu> rootMenus = sysMenus.stream()
                                        .filter(sysMenu -> sysMenu.getParentId()== null)
                                        .collect(Collectors.toList());
        for (SysMenu rootMenu : rootMenus) {
            rootMenu.setChilds(getChilds(rootMenu.getId(),sysMenus));
            rootMenu.setHasChildren(Boolean.TRUE);
        }
        return rootMenus ;
    }

    /**
     * 获取一个父菜单的子菜单
     * @param parentId
     * @param allMenu
     * @return
     */
    private List<SysMenu> getChilds(Long parentId, List<SysMenu> allMenu) {
        List<SysMenu> childs = new ArrayList<>() ;
        for (SysMenu menu : allMenu) {
            if(parentId.equals(menu.getParentId())){
                childs.add(menu) ;
                menu.setChilds(getChilds(menu.getId(),allMenu)); // 递归获取所有的子菜单
                menu.setPrivileges(sysPrivilegeService.getByMenuId(menu.getId())); // 只有子菜单可能有权限对应
                if(menu.getChilds().size()>0){
                    menu.setHasChildren(true);
                }
            }
        }
        return childs ;
    }


}
