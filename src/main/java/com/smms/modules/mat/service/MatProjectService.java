package com.smms.modules.mat.service;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.modules.mat.dao.MatProjectDao;
import com.smms.modules.mat.entity.MatProject;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatProjectService {

    @Autowired
    private MatProjectDao matProjectDao;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private MatProjectMaterialService matProjectMaterialService;

    public List<MatProject> queryList(Map<String,Object> map) {
         return matProjectDao.queryList(map);
    }

    public int queryTotal(Map<String,Object> map) {
        return matProjectDao.queryTotal(map);
    }

    @Transactional
    public Result save(MatProject project) {
        //创建菜单
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(project,sysMenu);
        sysMenu.setType(1);
        sysMenu.setParentId(34);
        sysMenuService.save(sysMenu);

        //保存项目
        project.setProjectId(sysMenu.getMenuId());
        matProjectDao.insertSelective(project);

        //更新菜单url
        sysMenu.setUrl("modules/mat/matProject.html?projectId="+project.getProjectId());
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    public MatProject queryById(Integer projectId) {
        return matProjectDao.selectByPrimaryKey(projectId);
    }

    @Transactional
    public Result update(MatProject project) {
        //更新项目
        matProjectDao.updateByPrimaryKeySelective(project);
        //更新菜单
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(project,sysMenu);
        sysMenu.setMenuId(project.getProjectId());
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    @Transactional
    public Result delete(Integer projectId) {
        //检查该项目下是否有物料
        HashMap<String ,Object> param=new HashMap<>();
        param.put("projectId",projectId);
        if(matProjectMaterialService.queryList(param).size()>0){
            return Result.error("删除失败，请先删除该项目下的物料");
        }
        //删除类目
        matProjectDao.deleteByPrimaryKey(projectId);
        //删除菜单
        sysMenuService.deleteBatch(new Integer[]{projectId});
        return Result.ok();
    }
}
