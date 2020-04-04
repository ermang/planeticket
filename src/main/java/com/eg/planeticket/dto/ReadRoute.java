package com.eg.planeticket.dto;

import java.time.OffsetDateTime;

public class ReadRoute {
    public Long id;
    public Long fromId;
    public Long toId;
    public OffsetDateTime departure;
    public OffsetDateTime arrival;
}
