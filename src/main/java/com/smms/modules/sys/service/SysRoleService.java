package com.smms.modules.sys.service;

import com.smms.common.entity.Constant;
import com.smms.common.exception.MyException;
import com.smms.modules.sys.dao.SysRoleDao;
import com.smms.modules.sys.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserService sysUserService;

    public List<SysRole> queryList(Map<String,Object> param) {
        return sysRoleDao.queryList(param);
    }


    public int queryTotal(Map<String,Object> param) {
        return sysRoleDao.queryTotal(param);
    }

    public void save(SysRole role) {
        role.setCreateTime(new Date());

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleDao.insert(role);
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }
    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRole role){
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if(role.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户所拥有的菜单列表
        List<Integer> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new MyException("新增角色的权限，已超出你的权限范围");
        }
    }

    public SysRole queryById(Integer roleId) {
        return sysRoleDao.selectByPrimaryKey(roleId);
    }

    public void deleteBatch(Integer[] roleIds) {
        sysRoleDao.deleteBatch(roleIds);
    }

    public List<Integer> queryRoleIdList(Integer createUserId) {
        return sysRoleDao.queryRoleIdList(createUserId);
    }

    @Transient
    public void update(SysRole role) {
        sysRoleDao.updateByPrimaryKey(role);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }
}
