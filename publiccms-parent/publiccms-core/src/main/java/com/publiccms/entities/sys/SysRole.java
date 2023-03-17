package com.publiccms.entities.sys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publiccms.common.database.CmsUpgrader;
import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * SysRole generated by hbm2java
 */
@Entity
@Table(name = "sys_role")
@DynamicUpdate
public class SysRole implements java.io.Serializable {
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
    @GeneratorColumn(title = "名称")
    @NotNull
    @Length(max = 50)
    private String name;
    /**
     * owns all right<p>
     * 拥有全部功能权限
     */
    @GeneratorColumn(title = "拥有全部功能权限")
    private boolean ownsAllRight;
    /**
     * show all modules<p>
     * 显示全部模块
     */
    @GeneratorColumn(title = "显示全部模块")
    private boolean showAllModule;

    public SysRole() {
    }

    public SysRole(short siteId, String name, boolean ownsAllRight, boolean showAllModule) {
        this.siteId = siteId;
        this.name = name;
        this.ownsAllRight = ownsAllRight;
        this.showAllModule = showAllModule;
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

    @Column(name = "owns_all_right", nullable = false)
    public boolean isOwnsAllRight() {
        return this.ownsAllRight;
    }

    public void setOwnsAllRight(boolean ownsAllRight) {
        this.ownsAllRight = ownsAllRight;
    }

    @Column(name = "show_all_module", nullable = false)
    public boolean isShowAllModule() {
        return this.showAllModule;
    }

    public void setShowAllModule(boolean showAllModule) {
        this.showAllModule = showAllModule;
    }

}
