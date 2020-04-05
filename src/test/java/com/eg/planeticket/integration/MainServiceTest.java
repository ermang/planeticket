package com.eg.planeticket.integration;

import com.eg.planeticket.TestUtil;
import com.eg.planeticket.dto.*;
import com.eg.planeticket.repo.*;
import com.eg.planeticket.service.MainService;
import com.eg.planeticket.service.PriceService;
import com.eg.planeticket.util.Dto2Entity;
import com.eg.planeticket.util.Entity2Dto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = {Service.class}))
public class MainServiceTest {

    @Autowired
    private AirportRepo airportRepo;
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CompanyFlightRepo companyFlightRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private RouteRepo routeRepo;
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private Dto2Entity dto2Entity;
    @Autowired
    private Entity2Dto entity2Dto;
    @Autowired
    private PriceService priceService;

    private MainService mainService;
    private TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();

        mainService = new MainService(airportRepo, cityRepo, dto2Entity, entity2Dto, companyRepo, routeRepo,
                companyFlightRepo, ticketRepo, priceService);
    }

    @Test
    public void testy() {
        Assert.assertEquals(17, 17);
    }

    @Test
    public void create_city() {
        CreateCity cc = testUtil.createCity_V1();
        Long result = mainService.createCity(cc);

        Assert.assertNotNull(result);
    }

    @Test
    public void create_company() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        Long result = mainService.createCompany(createCompany);

        Assert.assertNotNull(result);
    }

    @Test
    public void read_companies() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        mainService.createCompany(createCompany);

        ReadCompanyList result =mainService.readCompanies();

        Assert.assertEquals("THY", result.list.get(0).name);
    }

    @Test
    public void read_companies_with_name() {
        CreateCompany createCompany = testUtil.createCompany_V2();
        mainService.createCompany(createCompany);

        ReadCompanyList result =mainService.readCompanies("EMIRATES");

        Assert.assertEquals("EMIRATES", result.list.get(0).name);
    }

    @Test
    public void create_airport() {
        CreateCity cc = testUtil.createCity_V1();
        mainService.createCity(cc);
        CreateAirport ca = testUtil.createAirport_V1();
        Long result = mainService.createAirport(ca);

        Assert.assertNotNull(result);
    }

    @Test
    public void read_airports() {
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);

        ReadAirportList result = mainService.readAirports();
        Assert.assertEquals(2, result.list.size());
    }

    @Test
    public void read_airports_with_cityId() {
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);

        ReadAirportList result = mainService.readAirports(2L);
        Assert.assertEquals(1, result.list.size());
    }

    @Test
    public void create_route() {
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();

        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);

        CreateRoute createRoute = testUtil.createRoute_V1();
        Long result = mainService.createRoute(createRoute);

        Assert.assertNotNull(result);
    }

    @Test
    public void read_routes() {
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();

        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);

        ReadRouteList result = mainService.readRoutes(1L, 2L);

        Assert.assertEquals(1L, result.list.size());
    }

    @Test
    public void create_companyFlight() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();

        mainService.createCompany(createCompany);
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);

        CreateCompanyFlight ccf = testUtil.createCompanyFlight_V1();
        Long result = mainService.createCompanyFlight(ccf);

        Assert.assertNotNull(result);
    }

    @Test
    public void read_companyFlights() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();
        CreateCompanyFlight ccf = testUtil.createCompanyFlight_V1();

        mainService.createCompany(createCompany);
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);
        mainService.createCompanyFlight(ccf);


        ReadCompanyFlightList result = mainService.readCompanyFlights(null, null, null, null);

        Assert.assertEquals(1L, result.list.size());
    }

    @Test
    public void buy_ticket() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();
        CreateCompanyFlight ccf = testUtil.createCompanyFlight_V1();
        BuyTicket buyTicket = testUtil.buyTicket_V1();

        mainService.createCompany(createCompany);
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);
        mainService.createCompanyFlight(ccf);

        Long result = mainService.buyTicket(buyTicket);

        Assert.assertNotNull(result);
    }

    @Test
    public void read_ticket() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();
        CreateCompanyFlight ccf = testUtil.createCompanyFlight_V1();
        BuyTicket buyTicket = testUtil.buyTicket_V1();

        mainService.createCompany(createCompany);
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);
        mainService.createCompanyFlight(ccf);
        mainService.buyTicket(buyTicket);

        ReadTicket result = mainService.readTicket(1L);

        Assert.assertNotNull(result);
    }

    @Test
    public void delete_ticket() {
        CreateCompany createCompany = testUtil.createCompany_V1();
        CreateCity cc1 = testUtil.createCity_V1();
        CreateCity cc2 = testUtil.createCity_V2();
        CreateAirport ca1 = testUtil.createAirport_V1();
        CreateAirport ca2 = testUtil.createAirport_V2();
        CreateRoute createRoute = testUtil.createRoute_V1();
        CreateCompanyFlight ccf = testUtil.createCompanyFlight_V1();
        BuyTicket buyTicket = testUtil.buyTicket_V1();

        mainService.createCompany(createCompany);
        mainService.createCity(cc1);
        mainService.createCity(cc2);
        mainService.createAirport(ca1);
        mainService.createAirport(ca2);
        mainService.createRoute(createRoute);
        mainService.createCompanyFlight(ccf);
        mainService.buyTicket(buyTicket);

        Long result = mainService.deleteTicket(1L);

        Assert.assertNotNull(result);
    }



}
