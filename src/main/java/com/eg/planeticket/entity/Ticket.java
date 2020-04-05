package com.eg.planeticket.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Ticket extends BaseEntity{

    @NotNull
    private Long userId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CompanyFlight companyFlight;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CompanyFlight getCompanyFlight() {
        return companyFlight;
    }

    public void setCompanyFlight(CompanyFlight companyFlight) {
        this.companyFlight = companyFlight;
    }
}
