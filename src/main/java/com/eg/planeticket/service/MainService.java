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

    public MainService(AirportRepo airportRepo, CityRepo cityRepo, Dto2Entity dto2Entity, Entity2Dto entity2Dto,
                       CompanyRepo companyRepo, RouteRepo routeRepo, CompanyFlightRepo companyFlightRepo,
                       TicketRepo ticketRepo) {
        this.airportRepo = airportRepo;
        this.cityRepo = cityRepo;
        this.dto2Entity = dto2Entity;
        this.entity2Dto = entity2Dto;
        this.companyRepo = companyRepo;
        this.routeRepo = routeRepo;
        this.companyFlightRepo = companyFlightRepo;
        this.ticketRepo = ticketRepo;
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
            t = ticketRepo.save(t);

            BigDecimal currentCapacityRatio = new BigDecimal(companyFlight.getCapacity()).divide(new BigDecimal(companyFlight.getMaxCapacity()));
            currentCapacityRatio = currentCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);
            BigDecimal newCapacityRatio = new BigDecimal(companyFlight.getCapacity() +1).divide(new BigDecimal(companyFlight.getMaxCapacity()));
            newCapacityRatio = newCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);

            if (isPriceReCalculationRequired(currentCapacityRatio, newCapacityRatio)) {
                BigDecimal newPrice = calculatePrice(companyFlight.getBasePrice(), currentCapacityRatio);
                companyFlight.setPrice(newPrice);
            }

            companyFlight.setCapacity(companyFlight.getCapacity() + 1);
            companyFlight = companyFlightRepo.save(companyFlight);


            return t.getId();
        }
        else
            throw new RuntimeException("COMPANYFLIGHT WITH ID " + buyTicket.companyFlightId + " DOES NOT EXIST");
    }

    private boolean isPriceReCalculationRequired(BigDecimal currentCapacityRatio, BigDecimal newCapacityRatio) {
        return currentCapacityRatio.compareTo(newCapacityRatio) != 0;
    }

    public Long deleteTicket(long ticketId) {
        Optional<Ticket> t = ticketRepo.findById(ticketId);
        if(t.isPresent()){
            Ticket ticket = t.get();
            CompanyFlight companyFlight = companyFlightRepo.findById(ticket.getCompanyFlight().getId()).get();
            companyFlight.setCapacity(companyFlight.getCapacity() -1);

            BigDecimal currentCapacityRatio = new BigDecimal(companyFlight.getCapacity()).divide(new BigDecimal(companyFlight.getMaxCapacity()));
            currentCapacityRatio = currentCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);
            BigDecimal newCapacityRatio = new BigDecimal(companyFlight.getCapacity() -1).divide(new BigDecimal(companyFlight.getMaxCapacity()));
            newCapacityRatio = newCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);

            if (isPriceReCalculationRequired(currentCapacityRatio, newCapacityRatio)) {
                BigDecimal newPrice = calculatePrice(companyFlight.getBasePrice(), currentCapacityRatio);
                companyFlight.setPrice(newPrice);
            }

            companyFlightRepo.save(companyFlight);
            ticketRepo.deleteById(ticket.getId());

            return ticket.getId();
        }
        else
            throw new RuntimeException("TICKET WITH ID " + ticketId + " DOES NOT EXIST");
    }

    private BigDecimal calculatePrice(BigDecimal basePrice, BigDecimal currentCapacityRatio) {
        BigDecimal newPrice = basePrice;

        if(currentCapacityRatio.compareTo(new BigDecimal("0.9")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.9"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.8")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.8"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.7")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.7"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.6")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.6"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.5")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.5"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.4")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.4"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.3")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.3"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.2")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.2"));
        else if(currentCapacityRatio.compareTo(new BigDecimal("0.1")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.1"));
        return newPrice;
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
