package com.smms.modules.sys.dao;

import com.smms.common.entity.MyMapper;
import com.smms.modules.sys.entity.SysToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysTokenDao extends MyMapper<SysToken> {

    SysToken queryByToken(String token);

    SysToken queryByUserId(Integer userId);

}