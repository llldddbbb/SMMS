package com.smms.modules.mat.controller;

import com.google.code.kaptcha.Constants;
import com.smms.common.entity.Constant;
import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.util.PageUtils;
import com.smms.common.util.ShiroUtils;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatMaterialService;
import com.smms.modules.sys.entity.SysUser;
import org.apache.http.HttpResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mat/material")
public class MatMaterialController {

    @Autowired
    private MatMaterialService matMaterialService;

    @Value("${PRODUCT_PICTURE_PATH}")
    private String PRODUCT_PICTURE_PATH;


    @RequestMapping("/list/category/{categoryId}")
    public Result listByCategoryId(@RequestParam Map<String, Object> params,@PathVariable Integer categoryId){
        //查询列表数据
        params.put("categoryId",categoryId);
        Query query = new Query(params);
        List<SysUser> userList = matMaterialService.queryList(query);
        int total = matMaterialService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    @RequestMapping("/file/info/{matId}")
    public Result fileInfo(@PathVariable Integer matId){
        MatMaterial matMaterial=matMaterialService.getInfoById(matId);
        return Result.ok().put("data",matMaterial);
    }

    @RequestMapping("/productPicture")
    public void showProductPicture(String pictureName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, URISyntaxException {
        String basePath=this.getClass().getClassLoader().getResource("").toURI().getPath();
        response.setContentType("image/jpeg");   //设置返回的文件类型
        OutputStream output = response.getOutputStream();
        InputStream input=new BufferedInputStream(new FileInputStream(basePath+PRODUCT_PICTURE_PATH+pictureName));
        //读取文件流
        int len = 0;
        byte[] buffer = new byte[1024 * 10];
        while ((len = input.read(buffer)) != -1){
            output.write(buffer,0,len);
        }
        output.flush();
        output.close();
        input.close();
    }

}
