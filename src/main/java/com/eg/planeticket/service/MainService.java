package com.eg.planeticket.service;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.entity.Airport;
import com.eg.planeticket.entity.City;
import com.eg.planeticket.entity.Company;
import com.eg.planeticket.entity.Route;
import com.eg.planeticket.repo.CityRepo;
import com.eg.planeticket.repo.CompanyRepo;
import com.eg.planeticket.repo.RouteRepo;
import com.eg.planeticket.util.Dto2Entity;
import com.eg.planeticket.repo.AirportRepo;
import com.eg.planeticket.util.Entity2Dto;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MainService {
    private final AirportRepo airportRepo;
    private final CityRepo cityRepo;
    private final Dto2Entity dto2Entity;
    private final Entity2Dto entity2Dto;
    private final CompanyRepo companyRepo;
    private final RouteRepo routeRepo;

    public MainService(AirportRepo airportRepo, CityRepo cityRepo, Dto2Entity dto2Entity, Entity2Dto entity2Dto, CompanyRepo companyRepo, RouteRepo routeRepo) {
        this.airportRepo = airportRepo;
        this.cityRepo = cityRepo;
        this.dto2Entity = dto2Entity;
        this.entity2Dto = entity2Dto;
        this.companyRepo = companyRepo;
        this.routeRepo = routeRepo;
    }

    public Long createAirport(CreateAirport createAirport) {
        Airport airport = dto2Entity.createAirport2Airport(createAirport);
        airport = airportRepo.save(airport);

        return airport.getId();
    }

    public ReadAirportList readAirports() {
        List<Airport> airportList = airportRepo.findAll();
        ReadAirportList readAirportList = entity2Dto.airportList2ReadAirportList(airportList);

        return readAirportList;
    }

    public ReadAirportList readAirports(Long cityId) {
        List<Airport> airportList = airportRepo.findAllByCityId(cityId);
        ReadAirportList readAirportList = entity2Dto.airportList2ReadAirportList(airportList);

        return readAirportList;
    }

    public Long createCity(CreateCity createCity) {
        City city= dto2Entity.createCity2City(createCity);
        city = cityRepo.save(city);

        return city.getId();
    }

    public Long createCompany(CreateCompany createCompany) {
        Company company = dto2Entity.createCompany2Company(createCompany);
        company = companyRepo.save(company);

        return company.getId();
    }

    public ReadCompanyList readCompanies() {
        List<Company> companyList = companyRepo.findAll();
        ReadCompanyList readCompanyList = entity2Dto.companyList2ReadCompanyList(companyList);

        return readCompanyList;
    }

    public ReadCompanyList readCompanies(String companyName) {
        List<Company> companyList = companyRepo.findAllByName(companyName);
        ReadCompanyList readCompanyList = entity2Dto.companyList2ReadCompanyList(companyList);

        return readCompanyList;
    }

    public Long createRoute(CreateRoute createRoute) {
        Route route = dto2Entity.createRoute2Route(createRoute);
        route = routeRepo.save(route);

        return route.getId();
    }

    public ReadRouteList readRoutes(Long fromId, Long toId, OffsetDateTime departure, OffsetDateTime arrival) {
        List<Route> routeList = routeRepo.findAllByParams(fromId, toId, departure, arrival);
        ReadRouteList readRouteList = entity2Dto.routeList2ReadRouteList(routeList);

        return readRouteList;
    }
}
