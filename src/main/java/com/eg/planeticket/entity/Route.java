package com.eg.planeticket.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Route extends BaseEntity{

    @NotNull
    @ManyToOne
    private Airport from;

    @NotNull
    @ManyToOne
    private Airport to;

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }
}
