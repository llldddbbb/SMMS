package com.smms.modules.sys.controller;

import com.smms.common.entity.Result;
import com.smms.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController{

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/info")
    public Result getUserInfo(){
        return new Result().put("user",getUser());
    }


}
