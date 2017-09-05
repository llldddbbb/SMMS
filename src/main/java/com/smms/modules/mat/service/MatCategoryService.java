package com.smms.modules.mat.service;

import com.smms.common.exception.MyException;
import com.smms.modules.mat.dao.MatCategoryDao;
import com.smms.modules.mat.entity.MatCategory;
import com.smms.modules.sys.entity.SysMenu;
import com.smms.modules.sys.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        //查询更新父级菜单
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
}
