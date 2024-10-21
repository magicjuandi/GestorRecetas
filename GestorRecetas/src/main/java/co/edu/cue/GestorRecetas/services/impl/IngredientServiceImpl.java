package co.edu.cue.GestorRecetas.services.impl;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.repositories.IngredientRepository;
import co.edu.cue.GestorRecetas.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<IngredientDto> getAll() {
        return IngredientMapper.mapFrom((List<Ingredient>)repository.findAll() );
    }

    @Override
    public List<IngredientDto> getAllByRecipe(RecipeDto recipe) {
        return IngredientMapper.mapFrom(repository.findAllByRecipes(RecipeMapper.mapFrom(recipe)));
    }

    @Override
    public IngredientDto save(IngredientDto ingredient) {
        return IngredientMapper.mapFrom(repository.save(IngredientMapper.mapFrom(ingredient)));
    }

    @Override
    public IngredientDto update(IngredientDto ingredient) {
        repository.findById(ingredient.idIngredient()).orElseThrow();
        return IngredientMapper.mapFrom(repository.save(IngredientMapper.mapFrom(ingredient)));
    }

    @Override
    public void updateStatus(int id, Character status) {
        Ingredient ingredient = repository.findById(id).orElseThrow();
        ingredient.setStatus(status);
        repository.save(ingredient);
    }

    @Override
    public IngredientDto getById(int id) {
        Ingredient ingredient = repository.findById(id).orElseThrow();
        return IngredientMapper.mapFrom(ingredient);
    }
}
