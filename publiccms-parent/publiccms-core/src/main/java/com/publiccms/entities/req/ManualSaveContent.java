package com.publiccms.entities.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ManualSaveContent implements Serializable {

    // categoryId, modelId, parentId, title, author, stype, wform, product, text
    //uat添加都是空的字段: quoteCategoryId, categoryName, source, sourceUrl, publishDate, expiryDate, cover
    private int categoryId;
    private String modelId;
    private Long parentId;
    private short siteId = 19;
    private long userId;
    private Long quoteCategoryId;
    private String title;
    private String author;
    private String stype;
    private String wform;
    private String product;
    @JsonIgnore
    private String text;
    private String categoryName;
    private String source;
    private String sourceUrl;
    private Boolean checked;
    private Boolean draft;
    /**
     * cover<p>
     * 封面图
     */
    private String cover;
    /**
     * publish date<p>
     * 发布日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    /**
     * expiry date<p>
     * 过期日期
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiryDate;
    @JsonIgnore
    private String token;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public short getSiteId() {
        return siteId;
    }

    public void setSiteId(short siteId) {
        this.siteId = siteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getQuoteCategoryId() {
        return quoteCategoryId;
    }

    public void setQuoteCategoryId(Long quoteCategoryId) {
        this.quoteCategoryId = quoteCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getWform() {
        return wform;
    }

    public void setWform(String wform) {
        this.wform = wform;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "MannualSaveContent{" +
                "categoryId=" + categoryId +
                ", modelId='" + modelId + '\'' +
                ", parentId=" + parentId +
                ", siteId=" + siteId +
                ", userId=" + userId +
                ", quoteCategoryId=" + quoteCategoryId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", stype='" + stype + '\'' +
                ", wform='" + wform + '\'' +
                ", product='" + product + '\'' +
                ", text='" + text + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", source='" + source + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", cover='" + cover + '\'' +
                ", token='" + token + '\'' +
                ", publishDate=" + publishDate +
                ", expiryDate=" + expiryDate +
                ", checked=" + checked +
                ", draft=" + draft +
                '}';
    }
}
