package com.smms.modules.mat.entity;

import com.smms.common.validator.group.AddGroup;
import com.smms.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "mat_project")
public class MatProject {
    @Id
    @Column(name = "project_id")
    private Integer projectId;

    @NotBlank(message="项目名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @Column(name = "create_time")
    private Date createTime;

    private Integer orderNum;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}