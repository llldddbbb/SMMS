package com.smms.modules.mat.service;

import com.smms.common.entity.Query;
import com.smms.modules.mat.dao.MatMaterialDao;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MatMaterialService {

    @Autowired
    private MatMaterialDao matMaterialDao;

    public List<MatMaterial> queryList(Map<String,Object> map) {
        return matMaterialDao.queryList(map);
    }

    public int queryTotal(Map<String,Object> map) {
        return matMaterialDao.queryTotal(map);
    }

    public MatMaterial getInfoById(Integer matId) {
        return matMaterialDao.selectByPrimaryKey(matId);
    }

    public void saveMaterial(MatMaterial material) {
        material.setCreateTime(new Date());
        matMaterialDao.insertSelective(material);
    }

    public void updateMaterial(MatMaterial material) {
        matMaterialDao.updateByPrimaryKeySelective(material);
    }

    public void deleteBatch(Integer[] matIds) {
        matMaterialDao.deleteBatch(matIds);
    }
}
