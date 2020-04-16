package me.oyurimatheus.nossomercadolivre.shared;

import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.function.BiFunction;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.hasText;

public class UniqueFieldValidator<T, P, R extends Repository<?, ?>> implements Validator {

    private final String field;
    private final String errorCode;
    private final Class<? extends T> classToValidate;
    private final R repository;
    private final BiFunction<R, P, Boolean> existsFunction;

    public UniqueFieldValidator(@NotEmpty String field,
                                @Nullable String errorCode,
                                @NotNull Class<? extends T> classToValidate,
                                @NotNull R repository,
                                @NotNull BiFunction<R, P, Boolean> existsFunction) {

        hasText(field, "field cannot be null");
        requireNonNull(classToValidate, "classToValidate cannot be null");
        requireNonNull(repository, "repository cannot be null");
        requireNonNull(existsFunction, "exists cannot be null");

        this.field = field;
        this.errorCode = errorCode;
        this.classToValidate = classToValidate;
        this.repository = repository;
        this.existsFunction = existsFunction;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return classToValidate.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        try {
            Field fieldToValidate = classToValidate.getDeclaredField(this.field);
            fieldToValidate.setAccessible(true);
            Object fieldValue = fieldToValidate.get(target);

            Boolean hasObject = existsFunction.apply(repository, (P) fieldValue);
            if (hasObject) {
                errors.rejectValue(field, errorCode, format("%s is already registered", field));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
