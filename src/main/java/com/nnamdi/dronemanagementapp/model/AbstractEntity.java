package com.nnamdi.dronemanagementapp.model;

import com.nnamdi.dronemanagementapp.util.AppUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Slf4j
@MappedSuperclass
public class AbstractEntity {
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private String id;

    @LastModifiedDate
    @Column(name = "updated_date")
    private ZonedDateTime lastModifiedDate;


    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;


    @PrePersist
    public void abstractPrePersist() {
        log.debug("about to run abstractPrePersist method");
        createdDate = ZonedDateTime.now();
        lastModifiedDate = ZonedDateTime.now();
        if (AppUtil.stringIsNullOrEmpty(id)) {
            id = AppUtil.generateUUIDString();
        }
        log.debug("finished running abstractPrePersist method ");
    }

    @PreUpdate
    public void abstractPreUpdate() {
        lastModifiedDate = ZonedDateTime.now();
    }
}
