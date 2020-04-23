package me.oyurimatheus.nossomercadolivre.products;

import javax.validation.constraints.NotBlank;
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
    public String toString() {
        return new StringJoiner(", ", NewCharacteristicRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }

}
