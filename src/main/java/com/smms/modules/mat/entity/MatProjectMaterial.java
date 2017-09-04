package com.smms.modules.mat.entity;

import javax.persistence.*;

@Table(name = "mat_project_material")
public class MatProjectMaterial {
    @Id
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "mat_id")
    private Integer matId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return project_id
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

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
}