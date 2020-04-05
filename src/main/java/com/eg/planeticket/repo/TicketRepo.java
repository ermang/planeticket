package com.eg.planeticket.repo;

import com.eg.planeticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo  extends JpaRepository<Ticket, Long> {
}
