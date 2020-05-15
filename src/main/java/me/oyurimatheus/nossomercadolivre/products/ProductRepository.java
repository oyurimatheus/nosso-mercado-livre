package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends Repository<Product, UUID> {

    Product save(Product product);

    boolean existsById(UUID id);

    Optional<Product> findById(UUID uuid);

    List<Product> findByUser(User user);
}
