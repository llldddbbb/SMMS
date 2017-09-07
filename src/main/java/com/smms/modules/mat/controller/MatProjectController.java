package com.smms.modules.mat.controller;

import com.smms.common.annotation.SysLog;
import com.smms.common.entity.Result;
import com.smms.common.validator.ValidatorUtils;
import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import com.smms.modules.mat.entity.MatProject;
import com.smms.modules.mat.service.MatProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mat/project")
public class MatProjectController {

    @Autowired
    private MatProjectService matProjectService;


    @RequestMapping("/list")
    @RequiresPermissions("mat:project:list")
    public List<MatProject>  queryList(){
        List<MatProject> matProjectList = matProjectService.queryList(new HashMap<String, Object>());
        return matProjectList;
    }

    @RequestMapping("/select")
    @RequiresPermissions("mat:project:select")
    public Result select(){
        //查询列表数据
        List<MatProject> projectList = matProjectService.queryList(new HashMap<>());
        //添加顶级菜单
        MatProject root = new MatProject();
        root.setProjectId(34);
        root.setName("一级项目");
        root.setParentId(-1);
        root.setOpen(true);
        projectList.add(root);
        return Result.ok().put("projectList", projectList);
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
    public Result delete(Integer projectId){
       return matProjectService.delete(projectId);
    }


}
