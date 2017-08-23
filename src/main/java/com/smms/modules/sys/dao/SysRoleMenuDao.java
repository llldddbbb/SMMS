package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleMenuDao extends MyMapper<SysRoleMenu> {

    List<Integer> queryMenuIdList(Integer roleId);

    int deleteByRoleId(Integer roleId);

    int saveRoleMenu(Map<String, Object> map);
}