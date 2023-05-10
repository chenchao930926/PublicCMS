package com.publiccms.entities.cms;
// Generated 2020-3-26 11:25:55 by Hibernate Tools 6.0.0-SNAPSHOT

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * CmsUserCollectionId generated by hbm2java
 */
@Embeddable
public class CmsUserCollectionId implements java.io.Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 1L;
    /**
     * user id<p>
     * 用户id
     */
    @GeneratorColumn(title = "用户", condition = true)
    private long userId;
    /**
     * content id<p>
     * 内容id
     */
    @GeneratorColumn(title = "内容id", condition = true)
    private long contentId;

    public CmsUserCollectionId() {
    }

    public CmsUserCollectionId(long userId, long contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "content_id", nullable = false)
    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof CmsUserCollectionId))
            return false;
        CmsUserCollectionId castOther = (CmsUserCollectionId) other;

        return (this.getUserId() == castOther.getUserId())
                && (this.getContentId() == castOther.getContentId());
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) this.getUserId();
        result = 37 * result + (int) this.getContentId();
        return result;
    }

}
