package com.smms.modules.mat.service;

import com.smms.common.entity.Result;
import com.smms.common.exception.MyException;
import com.smms.modules.mat.dao.MatCategoryDao;
import com.smms.modules.mat.entity.MatCategory;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.SysMenuService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class MatCategoryService {

    @Autowired
    private MatCategoryDao matCategoryDao;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private MatMaterialService matMaterialService;

    public List<MatCategory> queryList(HashMap<String, Object> map) {
        return matCategoryDao.queryList(map);
    }

    @Transactional
    public void save(MatCategory category){
        //创建菜单
        SysMenu sysMenu=new SysMenu();

        //判断父级是否有物料
        this.updateMenuType(category);

        sysMenu.setType(1);//菜单
        BeanUtils.copyProperties(category,sysMenu);
        sysMenuService.save(sysMenu);

        //保存类别
        category.setCategoryId(sysMenu.getMenuId());
        matCategoryDao.insertSelective(category);

        //更新url
        sysMenu.setUrl("modules/mat/matCategory.html?categoryId="+category.getCategoryId());
        sysMenuService.update(sysMenu);

    }


    public List<MatCategory> querySelectList() {
        List<MatCategory> matCategoryList=matCategoryDao.queryList(new HashMap<>());
        return matCategoryList;
    }

    public MatCategory getCategoryById(Integer categoryId) {
        return matCategoryDao.selectByPrimaryKey(categoryId);
    }

    @Transactional
    public void update(MatCategory category) {
        //判断父级是否有子菜单
        this.updateMenuType(category);
        //更新类别
        matCategoryDao.updateByPrimaryKeySelective(category);


        //查询原父级是否有同级的菜单，如果没有则设置原父级的type为1
        List<SysMenu> sameRankByMenuList=sysMenuService.querySameRankByMenuId(category.getCategoryId());
        if(sameRankByMenuList.size()==1){
            SysMenu parentMenu=new SysMenu();
            parentMenu.setMenuId(sameRankByMenuList.get(0).getParentId());
            parentMenu.setType(1);
            sysMenuService.update(parentMenu);
        }
        //更新菜单
        SysMenu sysMenu=new SysMenu();
        BeanUtils.copyProperties(category,sysMenu);
        sysMenu.setType(1);
        sysMenu.setMenuId(category.getCategoryId());
        sysMenuService.update(sysMenu);
    }

    //判断父级是否有物料
    private void updateMenuType(MatCategory category){
        if(category.getParentId()!=33){
            HashMap<String,Object> map=new HashMap<>();
            map.put("categoryId",category.getParentId());
            if(matMaterialService.queryList(map).size()>0){
                throw new MyException("上级类目含有物料，不能添加为父级类目");
            }else {
                //更新父级菜单type成0：目录
                SysMenu parentMenu=new SysMenu();
                parentMenu.setMenuId(category.getParentId());
                parentMenu.setType(0);
                parentMenu.setIcon("fa fa-circle-o");
                sysMenuService.update(parentMenu);
            }
        }
    }

    @Transactional
    public Result deleteCategory(Integer categoryId) {
        //判断是否是父级菜单
        List<SysMenu> sysMenuList = sysMenuService.queryListParentId(categoryId);
        if(sysMenuList.size()>0){
            return Result.error("删除失败，请先删除该类别下的子类别");
        }
        //判断该类别下是否有物料
        HashMap<String,Object> map=new HashMap<>();
        map.put("categoryId",categoryId);
        if(matMaterialService.queryList(map).size()>0){
            return Result.error("删除失败，请先删除该类别下的物料");
        }
        //删除类别
        matCategoryDao.deleteByPrimaryKey(categoryId);
        //删除菜单
        sysMenuService.deleteBatch(new Integer[]{categoryId});
        return Result.ok();
    }
}
