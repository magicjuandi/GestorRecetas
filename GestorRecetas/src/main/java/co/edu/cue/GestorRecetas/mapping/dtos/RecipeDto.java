package co.edu.cue.GestorRecetas.mapping.dtos;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Menu;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeDto(int idRecipe,
                        String name,
                        String description,
                        String minutes,
                        String preparation,
                        List<Ingredient> ingredients,
                        Boolean favorite,
                        String review,
                        Character status,
                        List<Menu> menus) {
}
