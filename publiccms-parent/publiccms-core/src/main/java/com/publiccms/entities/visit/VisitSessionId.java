package com.publiccms.entities.visit;
// Generated 2021-1-14 22:33:12 by Hibernate Tools 6.0.0-SNAPSHOT

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publiccms.common.generator.annotation.GeneratorColumn;

/**
 * VisitSessionId generated by hbm2java
 */
@Embeddable
public class VisitSessionId implements java.io.Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @GeneratorColumn(title = "站点", condition = true)
    private short siteId;
    /**
     * session id<p>
     * 会话id
     */
    @GeneratorColumn(title = "会话")
    private String sessionId;
    /**
     * visit date<p>
     * 访问日期
     */
    @GeneratorColumn(title = "访问日期", condition = true)
    private Date visitDate;

    public VisitSessionId() {
    }

    public VisitSessionId(short siteId, String sessionId, Date visitDate) {
        this.siteId = siteId;
        this.sessionId = sessionId;
        this.visitDate = visitDate;
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

    @Column(name = "visit_date", nullable = false, length = 10)
    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof VisitSessionId))
            return false;
        VisitSessionId castOther = (VisitSessionId) other;

        return (this.getSiteId() == castOther.getSiteId())
                && ((this.getSessionId() == castOther.getSessionId()) || (this.getSessionId() != null
                        && castOther.getSessionId() != null && this.getSessionId().equals(castOther.getSessionId())))
                && ((this.getVisitDate() == castOther.getVisitDate()) || (this.getVisitDate() != null
                        && castOther.getVisitDate() != null && this.getVisitDate().equals(castOther.getVisitDate())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSiteId();
        result = 37 * result + (getSessionId() == null ? 0 : this.getSessionId().hashCode());
        result = 37 * result + (getVisitDate() == null ? 0 : this.getVisitDate().hashCode());
        return result;
    }

}
