package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    boolean existsById(String id);
    List<Post> findByUser(User user);
}
