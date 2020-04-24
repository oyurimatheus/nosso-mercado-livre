package me.oyurimatheus.nossomercadolivre.products;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.StringJoiner;

class NewCharacteristicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Characteristic toCharacteristic() {
        return new Characteristic(name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCharacteristicRequest that = (NewCharacteristicRequest) o;
        return name.equals(that.name) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NewCharacteristicRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }

}
