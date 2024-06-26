package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.CartItem;
import dev.lvpq.sell_book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByUser(User user);
}
