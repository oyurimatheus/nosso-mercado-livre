package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody @Valid NewUserRequest newUser) {
        Password password = Password.encode(newUser.getPassword());
        var user = new User(newUser.getLogin(), password);

        userRepository.save(user);

        URI location = URI.create("/api/users/" + user.getId());
        return created(location).build();
    }

    @InitBinder(value = { "newUserRequest" })
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserLoginValidator(userRepository));
    }

}
