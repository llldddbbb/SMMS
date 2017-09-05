package com.smms.modules.mat.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.mat.entity.MatCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MatCategoryDao extends MyMapper<MatCategory> {

    List<MatCategory> queryList(HashMap<String, Object> map);
}