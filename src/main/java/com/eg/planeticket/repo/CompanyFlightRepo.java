package com.eg.planeticket.repo;

import com.eg.planeticket.entity.CompanyFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyFlightRepo extends JpaRepository<CompanyFlight, Long> {
}
