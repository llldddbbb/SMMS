package com.smms.modules.mat.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.modules.mat.entity.MatCategory;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatCategoryService;
import com.smms.modules.mat.service.MatMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mat/category")
public class MatCategoryController {

    @Autowired
    private MatMaterialService matMaterialService;

    @Autowired
    private MatCategoryService matCategoryService;



    @RequestMapping("/list/material/{categoryId}")
    public Result listByCategoryId(@RequestParam Map<String, Object> params, @PathVariable Integer categoryId) {
        //查询列表数据
        params.put("categoryId", categoryId);
        Query query = new Query(params);
        List<MatMaterial> materialList = matMaterialService.queryList(query);
        int total = matMaterialService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(materialList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    @RequestMapping("/list")
    public List<MatCategory> list(){
        List<MatCategory> categoryList = matCategoryService.queryList(new HashMap<String, Object>());
        return categoryList;
    }

    @RequestMapping("/select")
    public Result select(){
        //查询列表数据
        List<MatCategory> categoryList = matCategoryService.querySelectList();
        //添加顶级菜单
        MatCategory root = new MatCategory();
        root.setCategoryId(33);
        root.setName("一级类目");
        root.setParentId(-1);
        root.setOpen(true);
        categoryList.add(root);
        return Result.ok().put("categoryList", categoryList);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody MatCategory category) {
        matCategoryService.save(category);
        return Result.ok();
    }

    @RequestMapping("/info/{categoryId}")
    public Result info(@PathVariable Integer categoryId){
        MatCategory matCategory=matCategoryService.getCategoryById(categoryId);
        return Result.ok().put("category",matCategory);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody MatCategory category){
        matCategoryService.update(category);
        return Result.ok();
    }

}
