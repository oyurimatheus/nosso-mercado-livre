package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.data.repository.Repository;

interface UserRepository extends Repository<User, Long> {

    User save(User user);
}
