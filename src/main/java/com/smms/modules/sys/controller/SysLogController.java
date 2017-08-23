package com.smms.modules.sys.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.modules.sys.entity.SysLog;
import com.smms.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Result list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<SysLog> sysLogList = sysLogService.queryList(query);
        int total = sysLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

}
