package com.smms.modules.sys.service;

import com.smms.modules.sys.dao.SysRoleMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuService {

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    public List<Integer> queryMenuIdList(Integer roleId) {
        return sysRoleMenuDao.queryMenuIdList(roleId);
    }

    public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
        //先删除角色与菜单关系
        sysRoleMenuDao.deleteByRoleId(roleId);

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        sysRoleMenuDao.saveRoleMenu(map);
    }
}
