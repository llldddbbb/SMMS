package com.smms.modules.sys.controller;

import com.smms.common.entity.Constant;
import com.smms.common.entity.Result;
import com.smms.common.exception.MyException;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.ShiroService;
import com.smms.modules.sys.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 菜单信息用于修改
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Integer menuId){
        SysMenu menu = sysMenuService.queryById(menuId);
        return Result.ok().put("menu", menu);
    }

    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenu sysMenu){
        verifyForm(sysMenu);
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody SysMenu sysMenu){
        verifyForm(sysMenu);
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(Integer menuId){
        List<SysMenu> sysMenuList = sysMenuService.queryListParentId(menuId);
        if(sysMenuList.size()>0){
            return Result.error("请先删除子菜单或按钮");
        }
        //同时删除角色表的菜单
        sysMenuService.deleteBatch(new Integer[]{menuId});
        return Result.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu){
        if(StringUtils.isEmpty(menu.getName())){
            throw new MyException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new MyException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isEmpty(menu.getUrl())){
                throw new MyException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenu parentMenu = sysMenuService.queryById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new MyException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new MyException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }

}
