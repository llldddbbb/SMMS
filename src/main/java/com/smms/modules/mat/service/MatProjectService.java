package com.smms.modules.mat.service;

import com.smms.common.entity.Result;
import com.smms.common.exception.MyException;
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



    @Transactional
    public Result save(MatProject project) {
        //判断父级是否有物料
        this.updateMenuType(project);
        //创建菜单
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(project,sysMenu);
        sysMenu.setType(1);
        sysMenuService.save(sysMenu);

        //保存项目
        project.setProjectId(sysMenu.getMenuId());
        matProjectDao.insertSelective(project);

        //更新菜单url
        sysMenu.setUrl("modules/mat/matProject.html?projectId="+project.getProjectId());
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    private void updateMenuType(MatProject project) {
        if(project.getParentId()!=34){
            HashMap<String,Object> map=new HashMap<>();
            map.put("projectId",project.getParentId());

            if(matProjectMaterialService.queryList(map).size()>0){
                throw new MyException("上级项目含有物料，不能添加为父级项目");
            }else {
                sysMenuService.updateParentMenu(project.getParentId(),0);
            }
        }
    }

    public MatProject queryById(Integer projectId) {
        return matProjectDao.selectByPrimaryKey(projectId);
    }

    @Transactional
    public Result update(MatProject project) {
        //判断父级是否有子菜单
        this.updateMenuType(project);
        //更新项目
        matProjectDao.updateByPrimaryKeySelective(project);

        //查询原父级是否有同级的菜单，如果没有则设置原父级的type为1
        List<SysMenu> sameRankByMenuList=sysMenuService.querySameRankByMenuId(project.getProjectId());
        if(sameRankByMenuList.size()==1){
            sysMenuService.updateParentMenu(sameRankByMenuList.get(0).getParentId(),1);
        }

        //更新菜单
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(project,sysMenu);
        sysMenu.setMenuId(project.getProjectId());
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    @Transactional
    public Result delete(Integer projectId) {
        //判断是否是父级菜单
        List<SysMenu> sysMenuList = sysMenuService.queryListParentId(projectId);
        if(sysMenuList.size()>0){
            return Result.error("删除失败，请先删除该项目下的子项目");
        }
        //检查该项目下是否有物料
        HashMap<String ,Object> param=new HashMap<>();
        param.put("projectId",projectId);
        if(matProjectMaterialService.queryList(param).size()>0){
            return Result.error("删除失败，请先删除该项目下的物料");
        }
        //删除类目
        matProjectDao.deleteByPrimaryKey(projectId);
        //查询原父级是否有同级的菜单，如果没有则设置原父级的type为1
        List<SysMenu> sameRankByMenuList=sysMenuService.querySameRankByMenuId(projectId);
        if(sameRankByMenuList.size()==1){
            sysMenuService.updateParentMenu(sameRankByMenuList.get(0).getParentId(),1);
        }
        //删除菜单
        sysMenuService.deleteBatch(new Integer[]{projectId});
        return Result.ok();
    }

}
