package com.smms.modules.sys.service;

import com.smms.common.entity.Constant;
import com.smms.modules.sys.dao.SysMenuDao;
import com.smms.modules.sys.dao.SysTokenDao;
import com.smms.modules.sys.dao.SysUserDao;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.entity.SysToken;
import com.smms.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ShiroService {

    @Autowired
    private SysTokenDao sysTokenDao;

    @Autowired
    private SysUserDao sysUserDao;
    
    @Autowired
    private SysMenuDao sysMenuDao;

    public SysToken queryByToken(String token){
        return sysTokenDao.queryByToken(token);
    }

    public SysUser queryUserById(Integer userId) {
        return sysUserDao.selectByPrimaryKey(userId);
    }

    public Set<String> getUserPermissions(Integer userId) {
        List<String> permsList;
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenu> menuList=sysMenuDao.queryList(new HashMap<>());
            permsList=new ArrayList<>(menuList.size());
            for (SysMenu sysMenu : menuList) {
                permsList.add(sysMenu.getPerms());
            }
        }else{
            permsList=sysUserDao.queryAllPerms(userId);
        }
        //添加用户权限
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isEmpty(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));

        }
        return permsSet;
    }
}
