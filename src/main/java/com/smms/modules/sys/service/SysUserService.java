package com.smms.modules.sys.service;

import com.smms.common.entity.Constant;
import com.smms.common.entity.Query;
import com.smms.common.exception.MyException;
import com.smms.modules.sys.dao.SysUserDao;
import com.smms.modules.sys.entity.SysRole;
import com.smms.modules.sys.entity.SysUser;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    public SysUser queryByUsername(String username){
        return sysUserDao.queryByUsername(username);
    }

    public List<Integer> queryAllMenuId(Integer userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    public List<SysUser> queryList(Map<String,Object> param) {
        return sysUserDao.queryList(param);
    }

    public int queryTotal(Map<String,Object> param) {
        return sysUserDao.queryTotal(param);
    }

    @Transactional
    public void save(SysUser user) {

        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        sysUserDao.insert(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUser user){
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Integer> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new MyException("新增用户所选角色，不是本人创建");
        }
    }

    public SysUser queryById(Integer userId) {
        return sysUserDao.selectByPrimaryKey(userId);
    }

    public void update(SysUser user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserDao.updateByPrimaryKeySelective(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Transactional
    public void deleteBatch(Integer[] userIds) {
        sysUserDao.deleteBatch(userIds);
    }
}
