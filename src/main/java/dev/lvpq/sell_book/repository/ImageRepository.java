package dev.lvpq.sell_book.repository;

import dev.lvpq.sell_book.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, String> { }
