package com.smms.modules.sys.dao;

import com.smms.common.util.MyMapper;
import com.smms.modules.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SysMenuDao extends MyMapper<SysMenu> {

    List<SysMenu> queryList(HashMap<String, Object> param);

    List<SysMenu> queryListByParentId(Integer parentId);

    List<SysMenu> queryNotButtonList();

    SysMenu queryById(Integer menuId);

    Integer deleteBatch(Integer[] menuIds);
}