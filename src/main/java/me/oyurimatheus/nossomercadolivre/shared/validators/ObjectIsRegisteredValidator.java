package me.oyurimatheus.nossomercadolivre.shared.validators;

import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.hasText;

/**
 *
 * @param <T> class which will be validated
 * @param <P> parameter type which will be validated
 */
// TODO: This class is very similar to #UniqueFielValidator maybe there's a way to generalize
public class ObjectIsRegisteredValidator<T, P> implements Validator {

    private final String field;
    private final String errorCode;
    private final Class<? extends T> classToValidate;
    private final Function<P, Boolean> existsFunction;

    /**
     *
     * @param field the class field which will be validated
     * @param errorCode the error code that the client will receive, if there's any error
     * @param classToValidate the class type which will be validated
     * @param existsFunction a function that receives the argument #P and returns a boolean
     *
     * @throws IllegalArgumentException if field has no text
     * @throws NullPointerException if classToValidate or existsFunction is null
     */
    public ObjectIsRegisteredValidator(@NotEmpty String field,
                                       @Nullable String errorCode,
                                       @NotNull Class<? extends T> classToValidate,
                                       @NotNull Function<P, Boolean> existsFunction) {

        hasText(field, "field cannot be null");
        requireNonNull(classToValidate, "classToValidate cannot be null");
        requireNonNull(existsFunction, "exists cannot be null");

        this.field = field;
        this.errorCode = errorCode;
        this.classToValidate = classToValidate;
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

            Boolean hasObject = existsFunction.apply((P) fieldValue);
            if (!hasObject) {
                errors.rejectValue(field, errorCode, format("Category %s is not registered", field));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
