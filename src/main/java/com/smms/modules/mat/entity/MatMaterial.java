package com.smms.modules.mat.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mat_material")
public class MatMaterial {
    @Id
    @Column(name = "mat_id")
    private Integer matId;

    /**
     * 部件的名称
     */
    private String item;

    /**
     * 部件的型号
     */
    private String model;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 价格
     */
    private String price;

    /**
     * 网站地址
     */
    private String website;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 产品图片
     */
    @Column(name = "product_picture")
    private String productPicture;

    /**
     * 爆炸图
     */
    @Column(name = "exploded_view")
    private String explodedView;

    /**
     * 2D组装图
     */
    @Column(name = "assembly_drawing_2d")
    private String assemblyDrawing2d;

    /**
     * 3D组装图
     */
    @Column(name = "assembly_drawing_3d")
    private String assemblyDrawing3d;

    /**
     * 技术文件
     */
    @Column(name = "technical_note")
    private String technicalNote;

    /**
     * 与实验相关报告
     */
    @Column(name = "related_experiment_report")
    private String relatedExperimentReport;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return mat_id
     */
    public Integer getMatId() {
        return matId;
    }

    /**
     * @param matId
     */
    public void setMatId(Integer matId) {
        this.matId = matId;
    }

    /**
     * 获取部件的名称
     *
     * @return item - 部件的名称
     */
    public String getItem() {
        return item;
    }

    /**
     * 设置部件的名称
     *
     * @param item 部件的名称
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 获取部件的型号
     *
     * @return model - 部件的型号
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置部件的型号
     *
     * @param model 部件的型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取生产厂家
     *
     * @return manufacturer - 生产厂家
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置生产厂家
     *
     * @param manufacturer 生产厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 获取网站地址
     *
     * @return website - 网站地址
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 设置网站地址
     *
     * @param website 网站地址
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取联系方式
     *
     * @return contact - 联系方式
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置联系方式
     *
     * @param contact 联系方式
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取产品图片
     *
     * @return product_picture - 产品图片
     */
    public String getProductPicture() {
        return productPicture;
    }

    /**
     * 设置产品图片
     *
     * @param productPicture 产品图片
     */
    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    /**
     * 获取爆炸图
     *
     * @return exploded_view - 爆炸图
     */
    public String getExplodedView() {
        return explodedView;
    }

    /**
     * 设置爆炸图
     *
     * @param explodedView 爆炸图
     */
    public void setExplodedView(String explodedView) {
        this.explodedView = explodedView;
    }

    /**
     * 获取2D组装图
     *
     * @return assembly_drawing_2d - 2D组装图
     */
    public String getAssemblyDrawing2d() {
        return assemblyDrawing2d;
    }

    /**
     * 设置2D组装图
     *
     * @param assemblyDrawing2d 2D组装图
     */
    public void setAssemblyDrawing2d(String assemblyDrawing2d) {
        this.assemblyDrawing2d = assemblyDrawing2d;
    }

    /**
     * 获取3D组装图
     *
     * @return assembly_drawing_3d - 3D组装图
     */
    public String getAssemblyDrawing3d() {
        return assemblyDrawing3d;
    }

    /**
     * 设置3D组装图
     *
     * @param assemblyDrawing3d 3D组装图
     */
    public void setAssemblyDrawing3d(String assemblyDrawing3d) {
        this.assemblyDrawing3d = assemblyDrawing3d;
    }

    /**
     * 获取技术文件
     *
     * @return technical_note - 技术文件
     */
    public String getTechnicalNote() {
        return technicalNote;
    }

    /**
     * 设置技术文件
     *
     * @param technicalNote 技术文件
     */
    public void setTechnicalNote(String technicalNote) {
        this.technicalNote = technicalNote;
    }

    /**
     * 获取与实验相关报告
     *
     * @return related_experiment_report - 与实验相关报告
     */
    public String getRelatedExperimentReport() {
        return relatedExperimentReport;
    }

    /**
     * 设置与实验相关报告
     *
     * @param relatedExperimentReport 与实验相关报告
     */
    public void setRelatedExperimentReport(String relatedExperimentReport) {
        this.relatedExperimentReport = relatedExperimentReport;
    }

    /**
     * @return category_id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}