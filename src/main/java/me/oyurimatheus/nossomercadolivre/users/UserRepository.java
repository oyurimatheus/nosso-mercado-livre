package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
