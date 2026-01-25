package com.example.gatherly.entity;

import com.example.gatherly.configs.TenantContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "locationFilter", parameters = @ParamDef(name = "locationId", type = String.class))
@Filter(name = "locationFilter", condition = "location_id = :locationId")
@Getter
@Setter
public abstract class TenantBaseEntity extends AuditEntity{

    @Column(name = "location_id", nullable = false, updatable = false)
    private String locationId;

    @PrePersist
    public void setLocation() {
        this.locationId = TenantContext.getLocation();
    }
}

