package co.edu.cue.GestorRecetas.services;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;

import java.util.List;

public interface MenuService {
    List<MenuDto> getAll();
    MenuDto save(MenuDto menu);
    MenuDto update(MenuDto menu);
    void updateStatus(int id, Character status);
    MenuDto getById(int id);

}
