package com.smms.modules.sys.controller;

import com.smms.common.entity.Result;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.ShiroService;
import com.smms.modules.sys.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController{

    @Autowired
    private ShiroService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    //侧边导航获取用户菜单
    @RequestMapping("/user")
    public Result getMenu(){
        Set<String> userPermissions = sysUserService.getUserPermissions(getUserId());
        List<SysMenu> userMenuList = sysMenuService.getUserMenuList(getUserId());
        return Result.ok().put("menuList",userMenuList).put("permissions", userPermissions);
    }

    //菜单管理获取用户菜单列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list(){
        List<SysMenu> menuList = sysMenuService.queryList(new HashMap<String, Object>());
        return menuList;
    }

    /**
     * ztree选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0);
        root.setName("一级菜单");
        root.setParentId(-1);
        root.setOpen(true);
        menuList.add(root);

        return Result.ok().put("menuList", menuList);
    }

}
