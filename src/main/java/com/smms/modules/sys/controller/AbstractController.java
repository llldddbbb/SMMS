package com.smms.modules.sys.controller;

import com.smms.common.util.ShiroUtils;
import com.smms.modules.sys.entity.SysUser;

public abstract class AbstractController {

    public SysUser getUser(){
        return (SysUser) ShiroUtils.getSubject().getPrincipal();
    }
}
