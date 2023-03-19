package com.publiccms.views.pojo.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.publiccms.entities.cms.CmsContentFile;
import com.publiccms.entities.cms.CmsContentProduct;
import com.publiccms.entities.cms.CmsContentRelated;
import com.publiccms.entities.cms.CmsTag;

/**
 *
 * CmsContentParameters
 * 
 */
public class CmsContentParameters extends ExtendDataParameters implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<List<CmsContentRelated>> contentRelateds;
    private Set<Integer> categoryIds;
    private Set<Long> contentIds;
    private List<CmsContentFile> files;
    private List<CmsContentFile> images;
    private List<CmsContentProduct> products;
    private Map<String, String> extendData;
    private List<CmsTag> tags;

    /**
     * @return
     */
    public List<List<CmsContentRelated>> getContentRelateds() {
        return contentRelateds;
    }

    /**
     * @param contentRelateds
     */
    public void setContentRelateds(List<List<CmsContentRelated>> contentRelateds) {
        this.contentRelateds = contentRelateds;
    }

    /**
     * @return the categoryIds
     */
    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    /**
     * @param categoryIds
     *            the categoryIds to set
     */
    public void setCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    /**
     * @return the contentIds
     */
    public Set<Long> getContentIds() {
        return contentIds;
    }

    /**
     * @param contentIds
     *            the contentIds to set
     */
    public void setContentIds(Set<Long> contentIds) {
        this.contentIds = contentIds;
    }

    /**
     * @return
     */
    public List<CmsContentFile> getFiles() {
        return files;
    }

    /**
     * @param files
     */
    public void setFiles(List<CmsContentFile> files) {
        this.files = files;
    }

    /**
     * @return extend data
     */
    public Map<String, String> getExtendData() {
        return extendData;
    }

    /**
     * @param extendData
     */
    public void setExtendData(Map<String, String> extendData) {
        this.extendData = extendData;
    }

    /**
     * @return
     */
    public List<CmsContentFile> getImages() {
        return images;
    }

    /**
     * @param images
     */
    public void setImages(List<CmsContentFile> images) {
        this.images = images;
    }

    /**
     * @return the products
     */
    public List<CmsContentProduct> getProducts() {
        return products;
    }

    /**
     * @param products
     *            the products to set
     */
    public void setProducts(List<CmsContentProduct> products) {
        this.products = products;
    }

    /**
     * @return
     */
    public List<CmsTag> getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(List<CmsTag> tags) {
        this.tags = tags;
    }
}