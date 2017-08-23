package com.smms.modules.sys.service;

import com.smms.common.entity.Query;
import com.smms.modules.sys.dao.SysLogDao;
import com.smms.modules.sys.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    public void save(SysLog sysLog) {
        sysLogDao.insert(sysLog);
    }

    public List<SysLog> queryList(Map<String,Object> param) {
        return sysLogDao.queryList(param);
    }

    public int queryTotal(Map<String,Object> param) {
        return sysLogDao.queryTotal(param);
    }
}
