package co.edu.cue.GestorRecetas.mapping.mappers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientMapper {
    public static IngredientDto mapFrom(Ingredient source) {
        return IngredientDto.builder()
                .idIngredient(source.getIdIngredient())
                .name(source.getName())
                .recipe(source.getRecipes())
                .quantity(source.getQuantity())
                .unit(source.getUnit())
                .status(source.getStatus())
                .build();
    }

    public static Ingredient mapFrom(IngredientDto source) {
        return Ingredient.builder()
                .idIngredient(source.idIngredient())
                .name(source.name())
                .recipes(source.recipe())
                .quantity(source.quantity())
                .unit(source.unit())
                .status(source.status())
                .build();
    }

    public static List<IngredientDto> mapFrom(List<Ingredient> source) {
        return source.stream().map(IngredientMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Ingredient> mapToEntities(List<IngredientDto> source) {
        return source.stream().map(IngredientMapper::mapFrom).collect(Collectors.toList());
    }
}
