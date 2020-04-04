package com.eg.planeticket.repo;

import com.eg.planeticket.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface RouteRepo extends JpaRepository<Route, Long> {

    @Query(value = "select r from Route r where"
            + " (:fromId IS NULL OR r.from.id = :fromId) AND"
            + " (:toId IS NULL OR r.to.id= :toId)")
    List<Route> findAllByParams(@Param("fromId") Long fromId, @Param("toId") Long toId);
}
