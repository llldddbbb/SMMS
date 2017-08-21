package com.smms.modules.sys.service;

import com.smms.common.entity.Constant;
import com.smms.modules.sys.dao.SysMenuDao;
import com.smms.modules.sys.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysUserService sysUserService;

    //获取用户菜单
    public List<SysMenu> getUserMenuList(Integer userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            //所有菜单
            return getAllMenuList(null);
        }

        //查询用户所有的menuId
        List<Integer> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    private List<SysMenu> getAllMenuList(List<Integer> menuIdList) {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }
    //递归查询：获取跟目录和菜单，未获取按钮
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Integer> menuIdList) {
        List<SysMenu> subSysMenuList=new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            if(sysMenu.getType()==Constant.MenuType.CATALOG.getValue()){
                sysMenu.setList(getMenuTreeList(queryListParentId(sysMenu.getMenuId(),menuIdList),menuIdList));
            }
            subSysMenuList.add(sysMenu);
        }
        return subSysMenuList;
    }
    //查询用户的菜单
    public List<SysMenu> queryListParentId(Integer parentId, List<Integer> menuIdList) {
        List<SysMenu> menuList=queryListParentId(parentId);

        if(menuIdList == null){
            return menuList;
        }
        List<SysMenu> userMenuList=new ArrayList<>();
        for (SysMenu menu : menuList) {
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    public List<SysMenu> queryListParentId(Integer parentId) {
        return sysMenuDao.queryListByParentId(parentId);
    }


    //获取菜单列表
    public List<SysMenu> queryList(HashMap<String, Object> param) {
        return sysMenuDao.queryList(param);
    }

    public List<SysMenu> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }
}
