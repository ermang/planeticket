package com.eg.planeticket.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class CreateCompanyFlight {
    public Long companyId;
    public Long routeId;
    public OffsetDateTime departure;
    public OffsetDateTime arrival;
    public Long maxCapacity;
    public BigDecimal basePrice;
}
