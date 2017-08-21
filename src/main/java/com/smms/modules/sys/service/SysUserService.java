package com.smms.modules.sys.service;

import com.smms.modules.sys.dao.SysUserDao;
import com.smms.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    public SysUser queryByUsername(String username){
        return sysUserDao.queryByUsername(username);
    }

    public List<Integer> queryAllMenuId(Integer userId) {
        return sysUserDao.queryAllMenuId(userId);
    }
}
