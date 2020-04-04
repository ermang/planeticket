package com.eg.planeticket.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ReadCompanyFlight {
    public Long id;
    public Long companyId;
    public Long routeId;
    public OffsetDateTime departure;
    public OffsetDateTime arrival;
    public BigDecimal price;
}
