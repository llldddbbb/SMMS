package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleDao extends MyMapper<SysRole> {


    int queryTotal(Map<String, Object> param);

    List<SysRole> queryList(Map<String, Object> param);

    void deleteBatch(Integer[] roleIds);

    List<Integer> queryRoleIdList(Integer createUserId);
}