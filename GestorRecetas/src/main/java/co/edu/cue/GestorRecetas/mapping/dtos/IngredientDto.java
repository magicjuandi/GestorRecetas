package co.edu.cue.GestorRecetas.mapping.dtos;

import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record IngredientDto(int idIngredient,
                            String name,
                            List<Recipe> recipe,
                            int quantity,
                            String unit,
                            Character status) {
}
