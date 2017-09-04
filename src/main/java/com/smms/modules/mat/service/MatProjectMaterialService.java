package com.smms.modules.mat.service;

import com.smms.common.entity.Query;
import com.smms.modules.mat.dao.MatProjectMaterialDao;
import com.smms.modules.mat.entity.MatMaterial;
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
}
