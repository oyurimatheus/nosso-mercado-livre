package me.oyurimatheus.nossomercadolivre.categories;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category, Long> {

    Optional<Category> findCategoryById(long id);

    Category save(Category category);

    boolean existsByName(String name);

    boolean existsById(Long id);
}
