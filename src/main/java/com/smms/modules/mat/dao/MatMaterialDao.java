package com.smms.modules.mat.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatMaterialDao extends MyMapper<MatMaterial> {

    List<SysUser> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Integer[] matIds);
}