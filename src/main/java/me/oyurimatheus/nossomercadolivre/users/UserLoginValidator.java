package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserLoginValidator implements Validator {

    private final UserRepository userRepository;

    UserLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewUserRequest newUser = (NewUserRequest) target;

        boolean userExists = userRepository.existsByEmail(newUser.getLogin());

        if (userExists) {
            errors.rejectValue("login", "user.login.alreadyRegistered", "this login is already registered");
        }
    }
}
