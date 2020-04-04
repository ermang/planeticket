package com.eg.planeticket.repo;

import com.eg.planeticket.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {

}
