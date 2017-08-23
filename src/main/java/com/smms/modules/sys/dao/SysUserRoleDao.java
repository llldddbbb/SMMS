package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserRoleDao extends MyMapper<SysUserRole> {
    
    
    int deleteByUserId(Integer userId);

    int saveUserRole(Map<String, Object> map);

    List<Integer> queryRoleIdList(Integer userId);
}