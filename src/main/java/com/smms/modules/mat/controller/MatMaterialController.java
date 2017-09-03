package com.smms.modules.mat.controller;

import com.smms.common.entity.Query;
import com.smms.common.entity.Result;
import com.smms.common.exception.MyException;
import com.smms.common.util.DateUtils;
import com.smms.common.util.PageUtils;
import com.smms.common.validator.ValidatorUtils;
import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatMaterialService;
import com.smms.modules.sys.entity.SysUser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
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
    public Result listByCategoryId(@RequestParam Map<String, Object> params, @PathVariable Integer categoryId) {
        //查询列表数据
        params.put("categoryId", categoryId);
        Query query = new Query(params);
        List<SysUser> userList = matMaterialService.queryList(query);
        int total = matMaterialService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    @RequestMapping("/file/info/{matId}")
    public Result fileInfo(@PathVariable Integer matId) {
        MatMaterial matMaterial = matMaterialService.getInfoById(matId);
        return Result.ok().put("data", matMaterial);
    }

    @RequestMapping("/productPicture")
    public void showProductPicture(String pictureName,  HttpServletResponse response) throws ServletException, IOException, URISyntaxException {
        String basePath = this.getClass().getClassLoader().getResource("").toURI().getPath();
//        String basePath = request.getSession().getServletContext().getRealPath("/");
        response.setContentType("image/jpeg");   //设置返回的文件类型
        OutputStream output = response.getOutputStream();
        InputStream input = new BufferedInputStream(new FileInputStream(basePath + PRODUCT_PICTURE_PATH + pictureName));
        //读取文件流
        int len = 0;
        byte[] buffer = new byte[1024 * 10];
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        output.close();
        input.close();
    }

    @RequestMapping("/info/{matId}")
    public Result getMaterial(@PathVariable Integer matId) {
        MatMaterial material = matMaterialService.getInfoById(matId);
        return Result.ok().put("material", material);
    }

    @RequestMapping("/save")
    public Result saveMaterial(@RequestBody MatMaterial material) {
        ValidatorUtils.validateEntity(material, AddGroup.class);
        matMaterialService.saveMaterial(material);
        return Result.ok();
    }

    @RequestMapping("/update")
    public Result updateMaterial(@RequestBody MatMaterial material) {
        ValidatorUtils.validateEntity(material, UpdateGroup.class);
        matMaterialService.updateMaterial(material);
        return Result.ok();
    }

    @RequestMapping("/delete")
    public Result deleteMaterial(@RequestBody Integer[] matIds) {
        matMaterialService.deleteBatch(matIds);
        return Result.ok();
    }

    @RequestMapping("/upload/file")
    public Result uploadFile(@RequestParam("file")MultipartFile file,String type) throws Exception{
        if (file.isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String baseFilePath=this.getClass().getClassLoader().getResource("").toURI().getPath()+"/file/";
        switch (type){
            case "productPicture":
                baseFilePath+="productPicture/";
                break;
            case "explodedView":
                baseFilePath+="explodedView/";
                break;
            case "assemblyDrawing2d":
                baseFilePath+="assemblyDrawing2d/";
                break;
            case "assemblyDrawing3d":
                baseFilePath+="assemblyDrawing3d/";
                break;
            case "technicalNote":
                baseFilePath+="technicalNote/";
                break;
            case "relatedExperimentReport":
                baseFilePath+="relatedExperimentReport/";
                break;
            default:
                return Result.error("未知错误，请刷新后重试");
        }
        String fileName = DateUtils.getCurrentTimeStr() + "." + file.getOriginalFilename().split("\\.")[1];
        FileUtils.writeByteArrayToFile(new File(baseFilePath+fileName),file.getBytes());
        return Result.ok().put("url", fileName);
    }

}
