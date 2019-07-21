package com.dev.rest.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * 모든 테이블에 공통 적용되는 항목들
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseSerializable implements Serializable {
    
    @Column(columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createdDate;

    @PrePersist
    public void prePersist() {
        setCreatedDate(Calendar.getInstance().getTime());
    }

    public BaseSerializable() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
