package com.eg.planeticket.service;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.entity.*;
import com.eg.planeticket.repo.*;
import com.eg.planeticket.util.Dto2Entity;
import com.eg.planeticket.util.Entity2Dto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MainService {
    private final AirportRepo airportRepo;
    private final CityRepo cityRepo;
    private final Dto2Entity dto2Entity;
    private final Entity2Dto entity2Dto;
    private final CompanyRepo companyRepo;
    private final RouteRepo routeRepo;
    private final CompanyFlightRepo companyFlightRepo;
    private final TicketRepo ticketRepo;
    private final PriceService priceService;

    public MainService(AirportRepo airportRepo, CityRepo cityRepo, Dto2Entity dto2Entity, Entity2Dto entity2Dto,
                       CompanyRepo companyRepo, RouteRepo routeRepo, CompanyFlightRepo companyFlightRepo,
                       TicketRepo ticketRepo, PriceService priceService) {
        this.airportRepo = airportRepo;
        this.cityRepo = cityRepo;
        this.dto2Entity = dto2Entity;
        this.entity2Dto = entity2Dto;
        this.companyRepo = companyRepo;
        this.routeRepo = routeRepo;
        this.companyFlightRepo = companyFlightRepo;
        this.ticketRepo = ticketRepo;
        this.priceService = priceService;
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

    public ReadRouteList readRoutes(Long fromId, Long toId) {
        List<Route> routeList = routeRepo.findAllByParams(fromId, toId);
        ReadRouteList readRouteList = entity2Dto.routeList2ReadRouteList(routeList);

        return readRouteList;
    }

    public Long createCompanyFlight(CreateCompanyFlight createCompanyFlight) {
        CompanyFlight companyFlight = dto2Entity.createCompanyFlight2CompanyFlight(createCompanyFlight);
        companyFlight = companyFlightRepo.save(companyFlight);

        return companyFlight.getId();
    }


    public ReadCompanyFlightList readCompanyFlights(Long companyId, Long routeId, OffsetDateTime departure, OffsetDateTime arrival) {
        List<CompanyFlight> flightList = companyFlightRepo.findAllByParams(companyId, routeId, departure, arrival);
        ReadCompanyFlightList readCompanyFlightList = entity2Dto.companyFlightList2ReadCompanyFlightList(flightList);

        return readCompanyFlightList;
    }

    public Long buyTicket(BuyTicket buyTicket) {
        Optional<CompanyFlight> cf = companyFlightRepo.findById(buyTicket.companyFlightId);
        if(cf.isPresent()){
            CompanyFlight companyFlight = cf.get();

            if(companyFlight.getCapacity().equals(companyFlight.getMaxCapacity()))
                throw new RuntimeException("ALL SEATS ARE SOLD OUT FOR COMPANYFLIGHT WITH ID " + companyFlight.getId());

            Ticket t = new Ticket();
            t.setCompanyFlight(companyFlight);
            t.setUserId(buyTicket.userId);

            if (priceService.isPriceReCalculationRequired(companyFlight.getCapacity(),
                    companyFlight.getCapacity() + 1, companyFlight.getMaxCapacity())) {
                BigDecimal newPrice = priceService.calculatePrice(companyFlight.getBasePrice(),
                        companyFlight.getCapacity() + 1, companyFlight.getMaxCapacity());
                companyFlight.setPrice(newPrice);
            }

            companyFlight.setCapacity(companyFlight.getCapacity() + 1);
            companyFlight = companyFlightRepo.save(companyFlight);
            t = ticketRepo.save(t);

            return t.getId();
        }
        else
            throw new RuntimeException("COMPANYFLIGHT WITH ID " + buyTicket.companyFlightId + " DOES NOT EXIST");
    }

    public Long deleteTicket(long ticketId) {
        Optional<Ticket> t = ticketRepo.findById(ticketId);
        if(t.isPresent()){
            Ticket ticket = t.get();
            CompanyFlight companyFlight = companyFlightRepo.findById(ticket.getCompanyFlight().getId()).get();

            if (priceService.isPriceReCalculationRequired(companyFlight.getCapacity(),
                    companyFlight.getCapacity() -1, companyFlight.getMaxCapacity())) {
                BigDecimal newPrice = priceService.calculatePrice(companyFlight.getBasePrice(),
                        companyFlight.getCapacity() - 1, companyFlight.getMaxCapacity());
                companyFlight.setPrice(newPrice);
            }

            companyFlight.setCapacity(companyFlight.getCapacity() -1);
            companyFlight = companyFlightRepo.save(companyFlight);
            ticketRepo.deleteById(ticket.getId());

            return ticket.getId();
        }
        else
            throw new RuntimeException("TICKET WITH ID " + ticketId + " DOES NOT EXIST");
    }

    public ReadTicket readTicket(long ticketId) {
        Optional<Ticket> t = ticketRepo.findById(ticketId);
        if(t.isPresent()){
            Ticket ticket = t.get();
            ReadTicket readTicket = entity2Dto.ticket2ReadTicket(ticket);

            return readTicket;
        }else
            throw new RuntimeException("TICKET WITH ID " + ticketId + " DOES NOT EXIST");
    }
}
