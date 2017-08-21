package com.smms.modules.sys.controller;

import com.smms.common.entity.Result;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.ShiroService;
import com.smms.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController{

    @Autowired
    private ShiroService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/user")
    public Result getMenu(){
        Set<String> userPermissions = sysUserService.getUserPermissions(getUserId());
        List<SysMenu> userMenuList = sysMenuService.getUserMenuList(getUserId());
        return Result.ok().put("menuList",userMenuList).put("permissions", userPermissions);
    }

}
