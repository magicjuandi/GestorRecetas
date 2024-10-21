package co.edu.cue.GestorRecetas.services.impl;

import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.repositories.RecipeRepository;
import co.edu.cue.GestorRecetas.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository repository;
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository repository, RecipeRepository recipeRepository) {
        this.repository = repository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeDto> getAll() {
        return RecipeMapper.mapFrom((List<Recipe>)repository.findAll());
    }

    @Override
    public RecipeDto save(RecipeDto recipe) {
        return RecipeMapper.mapFrom(repository.save(RecipeMapper.mapFrom(recipe)));
    }

    @Override
    public RecipeDto update(RecipeDto recipe) {
        repository.findById(recipe.idRecipe()).orElseThrow();
        return RecipeMapper.mapFrom(repository.save(RecipeMapper.mapFrom(recipe)));
    }

    @Override
    public void updateStatus(int id, Character status) {
        Recipe recipe = repository.findById(id).orElseThrow();
        recipe.setStatus(status);
        repository.save(recipe);
    }

    @Override
    public void updateFavorite(int id) {
        Recipe recipe = repository.findById(id).orElseThrow();
        if(recipe.getFavorite() == false){
            recipe.setFavorite(true);
        }else if(recipe.getFavorite() == true){
            recipe.setFavorite(false);
        }
        repository.save(recipe);
    }

    @Override
    public RecipeDto getById(int id) {
        Recipe recipe = repository.findById(id).orElseThrow();
        return RecipeMapper.mapFrom(recipe);
    }

    @Override
    public void updateReview(int id, String review) {
        Recipe recipe = repository.findById(id).orElseThrow();
        recipe.setReview(review);
        repository.save(recipe);
    }
}
