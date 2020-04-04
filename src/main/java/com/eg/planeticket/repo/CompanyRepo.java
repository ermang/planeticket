package com.eg.planeticket.repo;

import com.eg.planeticket.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepo  extends JpaRepository<Company, Long> {
    List<Company> findAllByName(String companyName);
}
