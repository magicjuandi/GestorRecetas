package co.edu.cue.GestorRecetas.services;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> getAll();
    List<IngredientDto> getAllByRecipe(RecipeDto recipe);
    IngredientDto save(IngredientDto ingredient);
    IngredientDto update(IngredientDto ingredient);
    void updateStatus(int id, Character status);
    IngredientDto getById(int id);
}

