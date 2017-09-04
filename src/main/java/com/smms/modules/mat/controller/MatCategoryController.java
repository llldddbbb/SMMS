package com.smms.modules.mat.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mat/category")
public class MatCategoryController {

    @Autowired
    private MatMaterialService matMaterialService;

    @RequestMapping("/list/category/{categoryId}")
    public Result listByCategoryId(@RequestParam Map<String, Object> params, @PathVariable Integer categoryId) {
        //查询列表数据
        params.put("categoryId", categoryId);
        Query query = new Query(params);
        List<MatMaterial> materialList = matMaterialService.queryList(query);
        int total = matMaterialService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(materialList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

}
