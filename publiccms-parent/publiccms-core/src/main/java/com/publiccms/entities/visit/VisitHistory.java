package com.publiccms.entities.visit;
// Generated 2021-1-14 22:33:12 by Hibernate Tools 6.0.0-SNAPSHOT

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publiccms.common.database.CmsUpgrader;
import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * VisitLog generated by hbm2java
 */
@Entity
@Table(name = "visit_history")
@DynamicUpdate
public class VisitHistory implements java.io.Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @GeneratorColumn(title = "ID")
    private Long id;
    @JsonIgnore
    @GeneratorColumn(title = "站点", condition = true)
    private short siteId;
    /**
     * session id<p>
     * 会话id
     */
    @GeneratorColumn(title = "会话", condition = true)
    private String sessionId;
    /**
     * visit date<p>
     * 访问日期
     */
    @GeneratorColumn(title = "访问日期", condition = true)
    private Date visitDate;
    /**
     * visit hour<p>
     * 访问小时
     */
    @GeneratorColumn(title = "访问小时", condition = true)
    private byte visitHour;
    /**
     * ip
     */
    @GeneratorColumn(title = "IP")
    private String ip;
    /**
     * user agent<p>
     * 浏览器用户标识
     */
    @GeneratorColumn(title = "UserAgent")
    private String userAgent;
    /**
     * title<p>
     * 标题
     */
    @GeneratorColumn(title = "url")
    @Length(max = 2048)
    private String url;
    /**
     * 
     */
    @GeneratorColumn(title = "标题")
    @Length(max = 255)
    private String title;
    /**
     * screen width<p>
     * 屏幕宽度
     */
    @GeneratorColumn(title = "屏幕宽度")
    private Integer screenWidth;
    /**
     * screen height<p>
     * 屏幕高度
     */
    @GeneratorColumn(title = "屏幕高度")
    private Integer screenHeight;
    /**
     * referer url<p>
     * 来源地址
     */
    @GeneratorColumn(title = "refererUrl")
    private String refererUrl;
    /**
     * item type<p>
     * 项目类型
     */
    @GeneratorColumn(title = "项目类型")
    private String itemType;
    /**
     * item id<p>
     * 项目id
     */
    @GeneratorColumn(title = "项目ID")
    private String itemId;
    /**
     * create date<p>
     * 创建日期
     */
    @GeneratorColumn(title = "创建日期", condition = true, order = true)
    private Date createDate;

    public VisitHistory() {
    }

    public VisitHistory(short siteId, String sessionId, Date visitDate, byte visitHour, String ip, String url, Date createDate) {
        this.siteId = siteId;
        this.sessionId = sessionId;
        this.visitDate = visitDate;
        this.visitHour = visitHour;
        this.ip = ip;
        this.url = url;
        this.createDate = createDate;
    }
    public VisitHistory(short siteId, String sessionId, Date visitDate, byte visitHour, String ip, String userAgent, String url, String title, Integer screenWidth, Integer screenHeight, String refererUrl, String itemType, String itemId, Date createDate) {
       this.siteId = siteId;
       this.sessionId = sessionId;
       this.visitDate = visitDate;
       this.visitHour = visitHour;
       this.ip = ip;
       this.userAgent = userAgent;
       this.url = url;
       this.title = title;
       this.screenWidth = screenWidth;
       this.screenHeight = screenHeight;
       this.refererUrl = refererUrl;
       this.itemType = itemType;
       this.itemId = itemId;
       this.createDate = createDate;
    }

    @Id
    @GeneratedValue(generator = "cmsGenerator")
    @GenericGenerator(name = "cmsGenerator", strategy = CmsUpgrader.IDENTIFIER_GENERATOR)

    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "site_id", nullable = false)
    public short getSiteId() {
        return this.siteId;
    }

    public void setSiteId(short siteId) {
        this.siteId = siteId;
    }

    @Column(name = "session_id", nullable = false, length = 50)
    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "visit_date", nullable = false, length = 10)
    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    @Column(name = "visit_hour", nullable = false)
    public byte getVisitHour() {
        return this.visitHour;
    }

    public void setVisitHour(byte visitHour) {
        this.visitHour = visitHour;
    }

    @Column(name = "ip", nullable = false, length = 130)
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "user_agent", length = 500)
    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Column(name = "url", nullable = false, length = 2048)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="title")
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="screen_width")
    public Integer getScreenWidth() {
        return this.screenWidth;
    }
    
    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }
    
    @Column(name="screen_height")
    public Integer getScreenHeight() {
        return this.screenHeight;
    }
    
    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }
    
    @Column(name="referer_url", length=2048)
    public String getRefererUrl() {
        return this.refererUrl;
    }

    public void setRefererUrl(String refererUrl) {
        this.refererUrl = refererUrl;
    }

    @Column(name = "item_type", length = 50)
    public String getItemType() {
        return this.itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Column(name = "item_id", length = 50)
    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, length = 19)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
