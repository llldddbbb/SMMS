package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysLogDao extends MyMapper<SysLog> {

    List<SysLog> queryList(Map<String, Object> param);

    int queryTotal(Map<String, Object> param);
}