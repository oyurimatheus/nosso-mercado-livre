package me.oyurimatheus.nossomercadolivre.users.security;

import me.oyurimatheus.nossomercadolivre.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;

    UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                             .orElseThrow(() -> new UsernameNotFoundException(format("User %s is not registered", username)));
    }

    public UserDetails loadUserById(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new UsernameNotFoundException(format("User with id: %s is not registered", userId)));
    }
}
