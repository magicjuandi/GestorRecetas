package co.edu.cue.GestorRecetas.services;

import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> getAll();
    RecipeDto save(RecipeDto recipe);
    RecipeDto update(RecipeDto recipe);
    void updateStatus(int id, Character status);
    void updateFavorite(int id);
    RecipeDto getById(int id);
    void updateReview(int id, String review);
}
