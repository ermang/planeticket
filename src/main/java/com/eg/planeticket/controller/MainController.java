package com.eg.planeticket.controller;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.service.MainService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@RestController
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping("/")
    public String greeting() {
        return "Welcome to PlaneTicker";
    }

    @PostMapping("/city")
    public Long createCity(@RequestBody CreateCity createCity){
        Long result = mainService.createCity(createCity);

        return result;
    }

    @PostMapping("/company")
    public Long createCompany(@RequestBody CreateCompany createCompany){
        Long result = mainService.createCompany(createCompany);

        return result;
    }

    @GetMapping("/companies")
    public ReadCompanyList readCompanyList(@RequestParam(required = false) String companyName){
        ReadCompanyList result = null;

        if(companyName != null && !companyName.isEmpty())
            result = mainService.readCompanies(companyName);
        else
            result = mainService.readCompanies();

        return result;
    }

    @PostMapping("/airport")
    public Long createAirport(@RequestBody CreateAirport createAirport) {
        Long result = mainService.createAirport(createAirport);

        return result;
    }

    @GetMapping("/airports")
    public ReadAirportList readAirportList(@RequestParam(required = false) Long cityId){
        ReadAirportList result = null;

        if (cityId != null)
            result = mainService.readAirports(cityId);
        else
            result = mainService.readAirports();

        return result;
    }

    @PostMapping("/route")
    public Long createRoute(@RequestBody CreateRoute createRoute) {
        Long result = mainService.createRoute(createRoute);

        return result;
    }

    @GetMapping("/routes")
    public ReadRouteList readRouteList(@RequestParam(required = false)  Long fromId,
                                       @RequestParam(required = false) Long toId){
        ReadRouteList result = null;

        result = mainService.readRoutes(fromId, toId);

        return result;
    }

    @PostMapping("/companyflight")
    public Long createCompanyFlight(@RequestBody CreateCompanyFlight createCompanyFlight) {
        Long result = mainService.createCompanyFlight(createCompanyFlight);

        return result;
    }

    @GetMapping("/companyflights")
    public ReadCompanyFlightList readCompanyFlightList(@RequestParam(required = false)  Long companyId,
                                               @RequestParam(required = false) Long routeId,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime departure,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime arrival){
        ReadCompanyFlightList result = null;

        result = mainService.readCompanyFlights(companyId, routeId, departure, arrival);

        return result;
    }

}
