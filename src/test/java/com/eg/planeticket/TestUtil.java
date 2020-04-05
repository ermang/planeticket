package com.eg.planeticket;

import com.eg.planeticket.dto.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TestUtil {

    public CreateCity createCity_V1() {
        CreateCity cc = new CreateCity();
        cc.name = "ISTANBUL";

        return cc;
    }

    public CreateCity createCity_V2() {
        CreateCity cc = new CreateCity();
        cc.name = "ANKARA";

        return cc;
    }

    public CreateCompany createCompany_V1() {
        CreateCompany cc = new CreateCompany();
        cc.name = "THY";

        return cc;
    }

    public CreateCompany createCompany_V2() {
        CreateCompany cc = new CreateCompany();
        cc.name = "EMIRATES";

        return cc;
    }

    public CreateAirport createAirport_V1() {
        CreateAirport ca = new CreateAirport();
        ca.cityId = 1L;
        ca.name = "SABIHA";

        return ca;
    }

    public CreateAirport createAirport_V2() {
        CreateAirport ca = new CreateAirport();
        ca.cityId = 2L;
        ca.name = "ESENBOGA";

        return ca;
    }

    public CreateRoute createRoute_V1() {
        CreateRoute cr = new CreateRoute();
        cr.fromId = 1L;
        cr.toId = 2L;

        return cr;
    }

    public CreateCompanyFlight createCompanyFlight_V1() {
        CreateCompanyFlight ccf = new CreateCompanyFlight();
        ccf.routeId = 1L;
        ccf.companyId = 1L;
        ccf.basePrice = BigDecimal.TEN;
        ccf.arrival = OffsetDateTime.now().plusHours(3);
        ccf.departure = OffsetDateTime.now();
        ccf.maxCapacity = 10L;

        return ccf;
    }

    public BuyTicket buyTicket_V1() {
        BuyTicket bt = new BuyTicket();
        bt.companyFlightId = 1L;
        bt.userId = 1L;

        return bt;
    }
}
