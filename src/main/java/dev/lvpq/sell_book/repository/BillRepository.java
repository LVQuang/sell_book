package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Bill;
import dev.lvpq.sell_book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
    public List<Bill> findByUser(User user);
}
