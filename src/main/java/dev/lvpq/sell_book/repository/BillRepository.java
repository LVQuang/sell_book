package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> { }
