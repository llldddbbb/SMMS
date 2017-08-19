package com.smms.modules.sys.service;

import com.smms.modules.sys.dao.SysTokenDao;
import com.smms.modules.sys.dao.SysUserDao;
import com.smms.modules.sys.entity.SysToken;
import com.smms.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ShiroService {

    @Autowired
    private SysTokenDao sysTokenDao;

    @Autowired
    private SysUserDao sysUserDao;

    public SysToken queryByToken(String token){
        return sysTokenDao.queryByToken(token);
    }

    public SysUser queryUserById(Integer userId) {
        return sysUserDao.selectByPrimaryKey(userId);
    }

    public Set<String> getUserPermissions(Integer userId) {
        return null;
    }
}
