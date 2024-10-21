package co.edu.cue.GestorRecetas.mapping.mappers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {
    public static MenuDto mapFrom(Menu source) {
        return MenuDto.builder()
                .idMenu(source.getIdMenu())
                .recipes(source.getRecipes())
                .status(source.getStatus())
                .build();
    }

    public static Menu mapFrom(MenuDto source) {
        return Menu.builder()
                .idMenu(source.idMenu())
                .recipes(source.recipes())
                .status(source.status())
                .build();
    }

    public static List<MenuDto> mapFrom(List<Menu> source) {
        return source.stream().map(MenuMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Menu> mapToEntities(List<MenuDto> source) {
        return source.stream().map(MenuMapper::mapFrom).collect(Collectors.toList());
    }
}
