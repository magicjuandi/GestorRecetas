package co.edu.cue.GestorRecetas.mapping.mappers;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {
    public static RecipeDto mapFrom(Recipe source) {
        return RecipeDto.builder()
                .idRecipe(source.getIdRecipe())
                .name(source.getName())
                .description(source.getDescription())
                .minutes(source.getMinutes())
                .preparation(source.getPreparation())
                .ingredients(source.getIngredients())
                .favorite(source.getFavorite())
                .review(source.getReview())
                .status(source.getStatus())
                .menus(source.getMenus())
                .build();
    }

    public static Recipe mapFrom(RecipeDto source) {
        return Recipe.builder()
                .idRecipe(source.idRecipe())
                .name(source.name())
                .description(source.description())
                .minutes(source.minutes())
                .preparation(source.preparation())
                .ingredients(source.ingredients())
                .favorite(source.favorite())
                .review(source.review())
                .status(source.status())
                .menus(source.menus())
                .build();
    }

    public static List<RecipeDto> mapFrom(List<Recipe> source) {
        return source.stream().map(RecipeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Recipe> mapToEntities(List<RecipeDto> source) {
        return source.stream().map(RecipeMapper::mapFrom).collect(Collectors.toList());
    }
}
