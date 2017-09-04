package com.smms.modules.mat.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.entity.MatProjectMaterial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatProjectMaterialDao extends MyMapper<MatProjectMaterial> {

    List<MatMaterial> queryList(Map map);

    int queryTotal(Map map);
}