package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.CategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class CharacteristicResponse {


    private String name;
    private String description;

    /**
     * @deprecated framework eyes only
     */
    @Deprecated
    private CharacteristicResponse() { }

    private CharacteristicResponse(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.description = characteristic.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static List<CharacteristicResponse> from(Set<Characteristic> characteristics) {
        return characteristics.stream()
                              .map(CharacteristicResponse::new)
                              .collect(toList());
    }
}
