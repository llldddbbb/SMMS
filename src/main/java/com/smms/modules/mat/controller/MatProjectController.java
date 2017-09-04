package com.smms.modules.mat.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatProjectMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mat/project")
public class MatProjectController {

    @Autowired
    private MatProjectMaterialService matProjectMaterialService;


    @RequestMapping("/list/project/{projectId}")
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
    public Result removeMaterial(@RequestBody Integer[] matIds){
        matProjectMaterialService.removeMaterial(matIds);
        return Result.ok();
    }

}
