package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    boolean existsById(String id);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:key% OR p.author LIKE %:key% OR p.country LIKE %:key% OR p.category LIKE %:key%")
    public List<Post> search(@Param("key") String key);

}
