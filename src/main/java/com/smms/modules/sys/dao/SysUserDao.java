package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserDao extends MyMapper<SysUser> {

    SysUser queryByUsername(String username);

    List<String> queryAllPerms(Integer userId);

    List<Integer> queryAllMenuId(Integer userId);
}