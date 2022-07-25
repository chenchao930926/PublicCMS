package com.publiccms.entities.cms;

import java.math.BigDecimal;

//Generated 2016-1-19 11:28:06 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * CmsContentAttribute generated by hbm2java
 */
@Entity
@Table(name = "cms_content_attribute")
@DynamicUpdate
public class CmsContentAttribute implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @GeneratorColumn(title = "内容")
    private long contentId;
    @GeneratorColumn(title = "来源")
    private String source;
    @GeneratorColumn(title = "来源地址")
    private String sourceUrl;
    @GeneratorColumn(title = "扩展数据")
    private String data;
    @GeneratorColumn(title = "全文索引文本")
    @JsonIgnore
    private String searchText;
    @GeneratorColumn(title = "数据字典值")
    @JsonIgnore
    private String dictionaryValues;
    @GeneratorColumn(title = "扩展文本")
    private String extendsText;
    @GeneratorColumn(title = "扩展文本字段")
    private String extendsFields;
    @GeneratorColumn(title = "附件文本")
    private String filesText;
    @GeneratorColumn(title = "最低价格")
    private BigDecimal minPrice;
    @GeneratorColumn(title = "最高价格")
    private BigDecimal maxPrice;
    @GeneratorColumn(title = "文本")
    private String text;
    @GeneratorColumn(title = "字数")
    private int wordCount;

    public CmsContentAttribute() {
    }

    public CmsContentAttribute(long contentId, int wordCount) {
        this.contentId = contentId;
        this.wordCount = wordCount;
    }

    public CmsContentAttribute(long contentId, String source, String sourceUrl, String data, int wordCount,BigDecimal minPrice,
            BigDecimal maxPrice) {
        this.contentId = contentId;
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.data = data;
        this.wordCount = wordCount;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public CmsContentAttribute(long contentId, String source, String sourceUrl, String data, String searchText,
            String dictionaryValues, String extendsText,String extendsFields, String filesText, BigDecimal minPrice,
            BigDecimal maxPrice, String text, int wordCount) {
        this.contentId = contentId;
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.data = data;
        this.searchText = searchText;
        this.dictionaryValues = dictionaryValues;
        this.extendsText = extendsText;
        this.extendsFields = extendsFields;
        this.filesText = filesText;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.text = text;
        this.wordCount = wordCount;
    }

    @Id
    @Column(name = "content_id", unique = true, nullable = false)
    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    @Column(name = "source", length = 50)
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "source_url", length = 1000)
    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Column(name = "data")
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Column(name = "search_text")
    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Column(name = "dictionary_values", length = 65535)
    public String getDictionaryValues() {
        return this.dictionaryValues;
    }

    public void setDictionaryValues(String dictionaryValues) {
        this.dictionaryValues = dictionaryValues;
    }

    @Column(name = "extends_text", length = 65535)
    public String getExtendsText() {
        return this.extendsText;
    }

    public void setExtendsText(String extendsText) {
        this.extendsText = extendsText;
    }

    @Column(name = "extends_fields", length = 65535)
    public String getExtendsFields() {
        return this.extendsFields;
    }

    public void setExtendsFields(String extendsFields) {
        this.extendsFields = extendsFields;
    }

    @Column(name = "files_text", length = 65535)
    public String getFilesText() {
        return this.filesText;
    }

    public void setFilesText(String filesText) {
        this.filesText = filesText;
    }

    @Column(name = "min_price", length = 50)
    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Column(name = "max_price", length = 50)
    public BigDecimal getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Column(name = "text")
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "word_count", nullable = false)
    public int getWordCount() {
        return this.wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

}
