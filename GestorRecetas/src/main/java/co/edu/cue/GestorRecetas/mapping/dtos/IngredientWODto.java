package co.edu.cue.GestorRecetas.mapping.dtos;

import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import lombok.Builder;

import java.util.List;

@Builder
public record IngredientWODto(int idIngredient,
                            String name,
                            int quantity,
                            String unit,
                            Character status) {
}

