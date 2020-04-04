package com.eg.planeticket.controller;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.service.MainService;
import org.springframework.web.bind.annotation.*;

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

}
