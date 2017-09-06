package com.smms.modules.mat.service;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.modules.mat.dao.MatProjectMaterialDao;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.entity.MatProjectMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatProjectMaterialService {

    @Autowired
    private MatProjectMaterialDao matProjectMaterialDao;

    public List<MatMaterial> queryList(Map map) {
        return matProjectMaterialDao.queryList(map);
    }

    public int queryTotal(Map map) {
        return matProjectMaterialDao.queryTotal(map);
    }

    public void removeMaterial(Integer[] matIds) {
        matProjectMaterialDao.deleteBathByMatId(matIds);
    }

    public Result save(MatProjectMaterial projectMaterial) {
        //查询是否重复添加
        List<MatProjectMaterial> select = matProjectMaterialDao.select(projectMaterial);
        if(select.size()>0){
            return Result.error("项目下已有该物料，请勿重复添加");
        }
        matProjectMaterialDao.insertSelective(projectMaterial);
        return Result.ok();
    }

    public void deleteBatch(Integer[] matIds) {
        matProjectMaterialDao.deleteBathByMatId(matIds);
    }
}
