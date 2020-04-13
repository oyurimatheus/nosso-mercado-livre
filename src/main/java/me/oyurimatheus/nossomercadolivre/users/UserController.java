package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserRepository userRepository;
    private final NewUserRequestToUserConverter converter;

    UserController(UserRepository userRepository,
                   NewUserRequestToUserConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody @Valid NewUserRequest newUser) {
        var user = converter.convert(newUser);

        userRepository.save(user);

        URI location = URI.create("/api/users/" + user.getId());
        return created(location).build();
    }

}
