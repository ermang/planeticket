package com.eg.planeticket.repo;

import com.eg.planeticket.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AirportRepo extends JpaRepository<Airport, Long> {
    List<Airport> findAllByCityId(Long cityId);
}
