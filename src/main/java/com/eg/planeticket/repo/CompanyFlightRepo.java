package com.eg.planeticket.repo;

import com.eg.planeticket.entity.CompanyFlight;
import com.eg.planeticket.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface CompanyFlightRepo extends JpaRepository<CompanyFlight, Long> {

    @Query(value = "select cf from CompanyFlight cf where"
            + " (:companyId IS NULL OR cf.company.id = :companyId) AND"
            + " (:routeId IS NULL OR cf.route.id= :routeId) AND"
            + " (:departure IS NULL OR cf.departure >= :departure) AND"
            + " (:arrival IS NULL OR cf.arrival <= :arrival)")
    List<CompanyFlight> findAllByParams(@Param("companyId") Long companyId, @Param("routeId") Long routeId,
                                        @Param("departure") OffsetDateTime departure, @Param("arrival") OffsetDateTime arrival);
}
