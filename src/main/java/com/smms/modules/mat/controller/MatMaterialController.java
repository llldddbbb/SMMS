package com.smms.modules.mat.controller;

import com.smms.common.annotation.SysLog;
import com.smms.common.entity.Result;
import com.smms.common.exception.MyException;
import com.smms.common.util.DateUtils;
import com.smms.common.validator.ValidatorUtils;
import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import com.smms.modules.mat.entity.MatMaterial;
import com.smms.modules.mat.service.MatMaterialService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/mat/material")
public class MatMaterialController {

    @Autowired
    private MatMaterialService matMaterialService;

    @Value("${PRODUCT_PICTURE_PATH}")
    private String PRODUCT_PICTURE_PATH;

    @RequestMapping("/info/{matId}")
    @RequiresPermissions("mat:material:info")
    public Result getMaterial(@PathVariable Integer matId) {
        MatMaterial material = matMaterialService.getInfoById(matId);
        return Result.ok().put("material", material);
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

    @SysLog("新增物料")
    @RequestMapping("/save")
    @RequiresPermissions("mat:material:save")
    public Result saveMaterial(@RequestBody MatMaterial material) {
        ValidatorUtils.validateEntity(material, AddGroup.class);
        matMaterialService.saveMaterial(material);
        return Result.ok();
    }

    @SysLog("修改物料")
    @RequestMapping("/update")
    @RequiresPermissions("mat:material:update")
    public Result updateMaterial(@RequestBody MatMaterial material) {
        ValidatorUtils.validateEntity(material, UpdateGroup.class);
        matMaterialService.updateMaterial(material);
        return Result.ok();
    }

    @SysLog("删除物料")
    @RequestMapping("/delete")
    @RequiresPermissions("mat:material:delete")
    public Result deleteMaterial(@RequestBody Integer[] matIds) {
        matMaterialService.deleteBatch(matIds);
        return Result.ok();
    }

    @RequestMapping("/upload/file")
    public Result uploadFile(@RequestParam("file")MultipartFile file, String type) throws Exception{
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

    @SysLog("下载资料")
    @RequestMapping("/download/{matId}")
    @RequiresPermissions("mat:material:download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer matId,String type) throws Exception{
        MatMaterial material = matMaterialService.getInfoById(matId);
        String baseFilePath=this.getClass().getClassLoader().getResource("").toURI().getPath()+"/file/";
        String filePath;
        switch (type){
            case "productPicture":
                filePath=baseFilePath+"productPicture/"+material.getProductPicture();
                break;
            case "explodedView":
                filePath=baseFilePath+"explodedView/"+material.getExplodedView();
                break;
            case "assemblyDrawing2d":
                filePath=baseFilePath+"assemblyDrawing2d/"+material.getAssemblyDrawing2d();
                break;
            case "assemblyDrawing3d":
                filePath=baseFilePath+"assemblyDrawing3d/"+material.getAssemblyDrawing3d();
                break;
            case "technicalNote":
                filePath=baseFilePath+"technicalNote/"+material.getTechnicalNote();
                break;
            case "relatedExperimentReport":
                filePath=baseFilePath+"relatedExperimentReport/"+material.getRelatedExperimentReport();
                break;
            default:
                throw new MyException("文件位置异常");
        }

        File file=new File(filePath);
        String fileName=material.getItem()+" "+type+"."+file.getName().split("\\.")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
