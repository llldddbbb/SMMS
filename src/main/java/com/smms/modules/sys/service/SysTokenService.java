package com.smms.modules.sys.service;

import com.smms.common.entity.Result;
import com.smms.modules.sys.dao.SysTokenDao;
import com.smms.modules.sys.entity.SysToken;
import com.smms.modules.sys.oauth2.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class SysTokenService {

    @Autowired
    private SysTokenDao sysTokenDao;

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    public SysToken queryByUserId(Integer userId){
        return sysTokenDao.queryByUserId(userId);
    }

    /**
     * 生成token并保存到数据库
     * @param userId
     * @return
     */
    public Result createToken(Integer userId) {
        String token= TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysToken sysToken=queryByUserId(userId);
        //保存or更新token
        if(sysToken==null){
            sysToken=new SysToken();
            sysToken.setUserId(userId);
            sysToken.setToken(token);
            sysToken.setUpdateTime(now);
            sysToken.setExpireTime(expireTime);
            sysTokenDao.insert(sysToken);
        }else{
            sysToken.setToken(token);
            sysToken.setUpdateTime(now);
            sysToken.setExpireTime(expireTime);
            Example example=new Example(SysToken.class);
            example.createCriteria().andEqualTo("userId",sysToken.getUserId());
            sysTokenDao.updateByExampleSelective(sysToken,example);
        }
        return Result.ok().put("token", token).put("expire", EXPIRE);
    }

}
