package com.eg.planeticket.dto;

import java.time.OffsetDateTime;

public class CreateRoute {
    public Long fromId;
    public Long toId;
    public OffsetDateTime departure;
    public OffsetDateTime arrival;
}
