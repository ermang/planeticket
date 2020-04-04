package com.eg.planeticket.util;

import com.eg.planeticket.dto.ReadAirport;
import com.eg.planeticket.dto.ReadAirportList;
import com.eg.planeticket.dto.ReadCompany;
import com.eg.planeticket.dto.ReadCompanyList;
import com.eg.planeticket.entity.Airport;
import com.eg.planeticket.entity.Company;
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
}
