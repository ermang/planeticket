package com.eg.planeticket.util;

import com.eg.planeticket.dto.CreateAirport;
import com.eg.planeticket.dto.CreateCity;
import com.eg.planeticket.dto.CreateCompany;
import com.eg.planeticket.dto.ReadAirportList;
import com.eg.planeticket.entity.Airport;
import com.eg.planeticket.entity.City;
import com.eg.planeticket.entity.Company;
import com.eg.planeticket.repo.CityRepo;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Dto2Entity {

    private final CityRepo cityRepo;

    public Dto2Entity(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
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
}
