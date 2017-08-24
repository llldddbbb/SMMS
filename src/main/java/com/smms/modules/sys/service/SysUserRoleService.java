package com.smms.modules.sys.service;

import com.smms.modules.sys.dao.SysUserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    public void saveOrUpdate(Integer userId, List<Integer> roleIdList) {
        if(roleIdList.size() == 0){
            return ;
        }

        //先删除用户与角色关系
        sysUserRoleDao.deleteByUserId(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        sysUserRoleDao.saveUserRole(map);
    }

    public List<Integer> queryRoleIdList(Integer userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }
}
