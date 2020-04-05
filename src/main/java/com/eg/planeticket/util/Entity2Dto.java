package com.eg.planeticket.util;

import com.eg.planeticket.dto.*;
import com.eg.planeticket.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Entity2Dto {

    public ReadAirportList airportList2ReadAirportList(List<Airport> airportList) {
        ReadAirportList readAirportList = new ReadAirportList();
        List<ReadAirport> list = new ArrayList<>();

        for (Airport airport: airportList)
            list.add(airport2ReadAirport(airport));

        readAirportList.list = list;

        return readAirportList;
    }

    private ReadAirport airport2ReadAirport(Airport airport) {
        ReadAirport readAirport = new ReadAirport();
        readAirport.id = airport.getId();
        readAirport.name = airport.getName();

        return readAirport;
    }

    public ReadCompanyList companyList2ReadCompanyList(List<Company> companyList) {
        ReadCompanyList readCompanyList = new ReadCompanyList();
        List<ReadCompany> list = new ArrayList<>();

        for(Company company: companyList)
            list.add(company2ReadCompany(company));

        readCompanyList.list = list;

        return readCompanyList;
    }

    private ReadCompany company2ReadCompany(Company company) {
        ReadCompany rc = new ReadCompany();
        rc.id = company.getId();
        rc.name = company.getName();

        return rc;
    }

    public ReadRouteList routeList2ReadRouteList(List<Route> routeList) {
        ReadRouteList rl = new ReadRouteList();
        List<ReadRoute> list = new ArrayList<>();

        for(Route route: routeList)
            list.add(route2ReadRoute(route));

        rl.list = list;

        return rl;
    }

    private ReadRoute route2ReadRoute(Route route) {
        ReadRoute rr = new ReadRoute();
        rr.id = route.getId();
        rr.fromId = route.getFrom().getId();
        rr.toId = route.getTo().getId();

        return rr;
    }

    public ReadCompanyFlightList companyFlightList2ReadCompanyFlightList(List<CompanyFlight> companyFlightList) {
        ReadCompanyFlightList rcfl = new ReadCompanyFlightList();
        List<ReadCompanyFlight> list = new ArrayList<>();

        for(CompanyFlight companyFlight: companyFlightList)
            list.add(companyFlight2ReadCompanyFlight(companyFlight));

        rcfl.list = list;

        return rcfl;
    }

    private ReadCompanyFlight companyFlight2ReadCompanyFlight(CompanyFlight companyFlight) {
        ReadCompanyFlight rcf = new ReadCompanyFlight();
        rcf.id = companyFlight.getId();
        rcf.companyId = companyFlight.getCompany().getId();
        rcf.routeId = companyFlight.getRoute().getId();
        rcf.departure = companyFlight.getDeparture();
        rcf.arrival = companyFlight.getArrival();
        rcf.price = companyFlight.getPrice();

        return rcf;
    }

    public ReadTicket ticket2ReadTicket(Ticket ticket) {
        ReadTicket rt = new ReadTicket();
        rt.id = ticket.getId();
        rt.userId = ticket.getUserId();
        rt.companyFlightId = ticket.getCompanyFlight().getId();

        return rt;
    }
}
