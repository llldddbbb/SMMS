package com.smms.modules.sys.dao;

import com.smms.common.util.MyMapper;
import com.smms.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao extends MyMapper<SysUser> {
}