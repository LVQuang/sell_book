package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsById(String permission);
}
