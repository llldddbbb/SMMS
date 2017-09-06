package com.smms.modules.mat.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.common.validator.ValidatorUtils;
import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.entity.MatProject;
import com.smms.modules.mat.service.MatProjectMaterialService;
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
    private MatProjectMaterialService matProjectMaterialService;

    @Autowired
    private MatProjectService matProjectService;


    @RequestMapping("/list/material/{projectId}")
    @RequiresPermissions("mat:matProject:list")
    public Result listByProjectId(@RequestParam Map<String, Object> params, @PathVariable Integer projectId){
        //查询列表数据
        params.put("projectId", projectId);
        Query query = new Query(params);
        List<MatMaterial> materialList = matProjectMaterialService.queryList(query);
        int total = matProjectMaterialService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(materialList, total, query.getLimit(), query.getPage());
        return Result.ok().put("page", pageUtil);
    }

    @RequestMapping("/remove")
    @RequiresPermissions("mat:matProject:remove")
    public Result removeMaterial(@RequestBody Integer[] matIds){
        matProjectMaterialService.removeMaterial(matIds);
        return Result.ok();
    }

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

    @RequestMapping("/update")
    @RequiresPermissions("mat:project:update")
    public Result update(@RequestBody MatProject project){
        ValidatorUtils.validateEntity(project, UpdateGroup.class);
        return matProjectService.update(project);
    }

    @RequestMapping("/delete")
    @RequiresPermissions("mat:project:delete")
    public Result delete(@RequestBody Integer projectId){
       return matProjectService.delete(projectId);
    }



}
