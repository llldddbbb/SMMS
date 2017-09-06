package com.smms.modules.mat.controller;

import com.smms.common.annotation.SysLog;
import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.common.validator.ValidatorUtils;
import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import com.smms.modules.mat.entity.MatProject;
import com.smms.modules.mat.service.MatProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mat/project")
public class MatProjectController {

    @Autowired
    private MatProjectService matProjectService;


    @RequestMapping("/list")
    @RequiresPermissions("mat:project:list")
    public Result queryList(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<MatProject> projectList = matProjectService.queryList(query);
        int total = matProjectService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(projectList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    @SysLog("新增项目")
    @RequestMapping("/save")
    @RequiresPermissions("mat:project:save")
    public Result save(@RequestBody MatProject project){
        ValidatorUtils.validateEntity(project, AddGroup.class);
        return matProjectService.save(project);
    }


    @RequestMapping("/info/{projectId}")
    @RequiresPermissions("mat:project:info")
    public Result info(@PathVariable Integer projectId){
        MatProject project=matProjectService.queryById(projectId);
        return Result.ok().put("project",project);
    }

    @SysLog("修改项目")
    @RequestMapping("/update")
    @RequiresPermissions("mat:project:update")
    public Result update(@RequestBody MatProject project){
        ValidatorUtils.validateEntity(project, UpdateGroup.class);
        return matProjectService.update(project);
    }

    @SysLog("删除项目")
    @RequestMapping("/delete")
    @RequiresPermissions("mat:project:delete")
    public Result delete(@RequestBody Integer projectId){
       return matProjectService.delete(projectId);
    }


}
