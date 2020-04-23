package me.oyurimatheus.nossomercadolivre.products;

import javax.persistence.Embeddable;
import java.util.StringJoiner;

@Embeddable
class Characteristic {


    private String name;
    private String description;

    private Characteristic() { }

    Characteristic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Characteristic.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
