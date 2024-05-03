package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository <InvalidatedToken, String> { }
