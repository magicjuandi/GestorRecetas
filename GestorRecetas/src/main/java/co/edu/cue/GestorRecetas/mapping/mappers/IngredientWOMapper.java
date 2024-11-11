package co.edu.cue.GestorRecetas.mapping.mappers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientWOMapper {
    public static IngredientWODto mapFrom(Ingredient source) {
        return IngredientWODto.builder()
                .idIngredient(source.getIdIngredient())
                .name(source.getName())
                .quantity(source.getQuantity())
                .unit(source.getUnit())
                .status(source.getStatus())
                .build();
    }

    public static Ingredient mapFrom(IngredientWODto source) {
        return Ingredient.builder()
                .idIngredient(source.idIngredient())
                .name(source.name())
                .quantity(source.quantity())
                .unit(source.unit())
                .status(source.status())
                .build();
    }

    public static List<IngredientWODto> mapFrom(List<Ingredient> source) {
        return source.stream().map(IngredientWOMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Ingredient> mapToEntities(List<IngredientWODto> source) {
        return source.stream().map(IngredientWOMapper::mapFrom).collect(Collectors.toList());
    }
}
