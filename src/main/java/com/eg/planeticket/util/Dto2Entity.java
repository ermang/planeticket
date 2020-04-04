package com.eg.planeticket.util;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.entity.*;
import com.eg.planeticket.repo.AirportRepo;
import com.eg.planeticket.repo.CityRepo;
import com.eg.planeticket.repo.CompanyRepo;
import com.eg.planeticket.repo.RouteRepo;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Dto2Entity {

    private final CityRepo cityRepo;
    private final AirportRepo airportRepo;
    private final CompanyRepo companyRepo;
    private final RouteRepo routeRepo;

    public Dto2Entity(CityRepo cityRepo, AirportRepo airportRepo, CompanyRepo companyRepo, RouteRepo routeRepo) {
        this.cityRepo = cityRepo;
        this.airportRepo = airportRepo;
        this.companyRepo = companyRepo;
        this.routeRepo = routeRepo;
    }

    public Airport createAirport2Airport(CreateAirport createAirport) {
        Airport a = new Airport();
        Optional<City> c = cityRepo.findById(createAirport.cityId);

        if(c.isPresent())
            a.setCity(c.get());
        else
            throw new RuntimeException("CTIY WITH ID " + createAirport.cityId + "DOES NOT EXIST");

        a.setName(createAirport.name);

        return a;
    }


    public City createCity2City(CreateCity createCity) {
        City c = new City();
        c.setName(createCity.name);

        return c;
    }

    public Company createCompany2Company(CreateCompany createCompany) {
        Company c = new Company();
        c.setName(createCompany.name);

        return c;
    }

    public Route createRoute2Route(CreateRoute createRoute) {
        Route r = new Route();
        Optional<Airport> from = airportRepo.findById(createRoute.fromId);

        if(from.isPresent())
            r.setFrom(from.get());
        else
            throw new RuntimeException("AIRPORT WITH ID " + createRoute.fromId + " DOES NOT EXIST");

        Optional<Airport> to = airportRepo.findById(createRoute.toId);

        if(to.isPresent())
            r.setTo(to.get());
        else
            throw new RuntimeException("AIRPORT WITH ID " + createRoute.toId + " DOES NOT EXIST");

        return r;
    }

    public CompanyFlight createCompanyFlight2CompanyFlight(CreateCompanyFlight createCompanyFlight) {
        CompanyFlight cf = new CompanyFlight();

        Optional<Company> c = companyRepo.findById(createCompanyFlight.companyId);

        if(c.isPresent())
            cf.setCompany(c.get());
        else
            throw new RuntimeException("COMPANY WITH ID " + createCompanyFlight.companyId + " DOES NOT EXIST");

        Optional<Route> r = routeRepo.findById(createCompanyFlight.routeId);

        if(r.isPresent())
            cf.setRoute(r.get());
        else
            throw new RuntimeException("ROUTE WITH ID " + createCompanyFlight.routeId + " DOES NOT EXIST");

        cf.setMaxCapacity(createCompanyFlight.maxCapacity);
        cf.setPrice(createCompanyFlight.price);
        cf.setDeparture(createCompanyFlight.departure);
        cf.setArrival(createCompanyFlight.arrival);

        return cf;
    }
}
