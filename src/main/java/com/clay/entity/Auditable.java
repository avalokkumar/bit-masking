package com.clay.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
public class Auditable {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
