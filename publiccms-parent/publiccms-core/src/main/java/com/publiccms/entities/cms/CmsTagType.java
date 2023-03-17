package com.publiccms.entities.cms;

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
 * CmsTagType generated by hbm2java
 */
@Entity
@Table(name = "cms_tag_type")
@DynamicUpdate
public class CmsTagType implements java.io.Serializable {

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
    @GeneratorColumn(title = "名称", condition = true, like = true)
    @NotNull
    @Length(max = 50)
    private String name;
    /**
     * tag count<p>
     * 标签个数
     */
    @GeneratorColumn(title = "标签个数")
    private int count;

    public CmsTagType() {
    }

    public CmsTagType(short siteId, String name, int count) {
        this.siteId = siteId;
        this.name = name;
        this.count = count;
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

    @Column(name = "count", nullable = false)
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
