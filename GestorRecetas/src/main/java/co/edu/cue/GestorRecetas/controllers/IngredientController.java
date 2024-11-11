package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.services.IngredientService;
import co.edu.cue.GestorRecetas.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam String name,
                                         @RequestParam int quantity,
                                         @RequestParam String unit) {
        Character character = 'A';
        IngredientDto ingredientDto = IngredientDto.builder()
                .name(name)
                .quantity(quantity)
                .unit(unit)
                .status(character)
                .build();
        ingredientService.save(ingredientDto);
        return ResponseEntity.ok("Ingredient created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam int idIngredient,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer quantity,
                                         @RequestParam(required = false) String unit) {
        IngredientWODto ingredientDto = ingredientService.getById(idIngredient);
        if (ingredientDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found");
        }

        IngredientDto updatedIngredient = IngredientDto.builder()
                .idIngredient(ingredientDto.idIngredient())
                .name((name != null && !name.isEmpty()) ? name : ingredientDto.name())
                .quantity((quantity != null) ? quantity : ingredientDto.quantity())
                .unit((unit != null && !unit.isEmpty()) ? unit : ingredientDto.unit())
                .status(ingredientDto.status())
                .build();
        ingredientService.save(updatedIngredient);
        return ResponseEntity.ok("Ingredient updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<IngredientWODto>> getAll() {
        List<IngredientWODto> ingredients = ingredientService.getAll();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/byRecipe")
    public ResponseEntity<List<IngredientDto>> getByRecipe(@RequestParam int recipeId) {
        RecipeDto recipeDto = recipeService.getById(recipeId);
        if (recipeDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<IngredientDto> ingredients = ingredientService.getAllByRecipe(recipeDto);
        return ResponseEntity.ok(ingredients);
    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id,
                                               @RequestParam Character status) {
        try {
            ingredientService.updateStatus(id, status);
            return ResponseEntity.ok("Ingredient status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found");
        }
    }
}
