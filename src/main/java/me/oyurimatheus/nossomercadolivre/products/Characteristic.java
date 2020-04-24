package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

import static org.springframework.util.Assert.hasText;

@Embeddable
class Characteristic {


    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private Characteristic() { }

    Characteristic(@NotBlank String name,
                   @NotBlank String description) {
        hasText(name, "name must have some text");
        hasText(name, "description must be set");

        this.name = name;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return name.equals(that.name) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Characteristic.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
