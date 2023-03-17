package com.publiccms.entities.sys;

import java.util.Date;

// Generated 2016-1-19 11:28:06 by Hibernate Tools 4.3.1

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publiccms.common.database.CmsUpgrader;
import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * SysTask generated by hbm2java
 */
@Entity
@Table(name = "sys_task")
@DynamicUpdate
public class SysTask implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @GeneratorColumn(title = "ID")
    private Integer id;
    @GeneratorColumn(title = "站点", condition = true)
    @JsonIgnore
    private short siteId;
    /**
     * name<p>
     * 名称
     */
    @GeneratorColumn(title = "任务名称")
    @NotNull
    @Length(max = 50)
    private String name;
    /**
     * status(0:ready,1:running,2:paused,3:error)<p>
     * 状态(0:就绪,1:执行中,2:暂停,3:错误)
     */
    @GeneratorColumn(title = "状态", condition = true)
    private int status;
    /**
     * cron expression<p>
     * 计划表达式
     */
    @GeneratorColumn(title = "表达式")
    private String cronExpression;
    /**
     * description<p>
     * 描述
     */
    @GeneratorColumn(title = "描述")
    @Length(max = 300)
    private String description;
    /**
     * file path<p>
     * 文件路径
     */
    @GeneratorColumn(title = "文件路径")
    private String filepath;
    /**
     * update date<p>
     * 更新日期
     */
    @GeneratorColumn(title = "更新日期", condition = true)
    private Date updateDate;

    public SysTask() {
    }

    public SysTask(short siteId, String name, int status, String cronExpression) {
        this.siteId = siteId;
        this.name = name;
        this.status = status;
        this.cronExpression = cronExpression;
    }

    public SysTask(short siteId, String name, int status, String cronExpression, String description, String filepath,
            Date updateDate) {
        this.siteId = siteId;
        this.name = name;
        this.status = status;
        this.cronExpression = cronExpression;
        this.description = description;
        this.filepath = filepath;
        this.updateDate = updateDate;
    }

    @Id
    @GeneratedValue(generator = "cmsGenerator")
    @GenericGenerator(name = "cmsGenerator", strategy = CmsUpgrader.IDENTIFIER_GENERATOR)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "site_id", nullable = false)
    public short getSiteId() {
        return this.siteId;
    }

    public void setSiteId(short siteId) {
        this.siteId = siteId;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "cron_expression", nullable = false, length = 50)
    public String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "description", length = 300)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "file_path")
    public String getFilePath() {
        return this.filepath;
    }

    public void setFilePath(String filepath) {
        this.filepath = filepath;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", length = 19)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
