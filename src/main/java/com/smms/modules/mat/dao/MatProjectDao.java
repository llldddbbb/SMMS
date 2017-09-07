package com.smms.modules.mat.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.mat.entity.MatProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatProjectDao extends MyMapper<MatProject> {
    
    
    List<MatProject> queryList(Map<String, Object> map);

}