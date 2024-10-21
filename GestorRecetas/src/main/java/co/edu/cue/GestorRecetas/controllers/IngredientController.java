package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
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

@Controller
@RequestMapping("/Ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/createForm")
    public ModelAndView createForm() {
        return new ModelAndView("create-ingredient");
    }
    @PostMapping("/create")
    public String create(@RequestParam String name,
                         @RequestParam int quantity,
                         @RequestParam String unit) {
        Character character ='A';
        IngredientDto ingredientDto = IngredientDto.builder()
                .name(name)
                .quantity(quantity)
                .unit(unit)
                .status(character)
                .build();
        ingredientService.save(ingredientDto);
        return "redirect:/Ingredient/get";
    }
    @GetMapping("/updateForm")
    public ModelAndView updateForm() {
        return new ModelAndView("update-ingredient");
    }
    @PostMapping("/update")
    public String update(@RequestParam int idIngredient,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer quantity,
                         @RequestParam(required = false) String unit) {
        IngredientDto ingredientDto = ingredientService.getById(idIngredient);
        IngredientDto newIngredientDto = IngredientDto.builder()
                .idIngredient(ingredientDto.idIngredient())
                .name((name!=null && !name.isEmpty()) ? name : ingredientDto.name())
                .quantity((quantity!=null) ? quantity : ingredientDto.quantity())
                .unit(unit!=null && !unit.isEmpty() ? unit :ingredientDto.unit())
                .status(ingredientDto.status())
                .build();
        ingredientService.save(newIngredientDto);
        return "redirect:/Ingredient/get";
    }
    @GetMapping("/get")
    public ModelAndView get() {
        List<IngredientDto> ingredients = ingredientService.getAll();
        ModelAndView modelAndView = new ModelAndView("list-ingredient");
        modelAndView.addObject("ingredients", ingredients);
        return modelAndView;
    }
    @GetMapping("/getByRecipeForm")
    public ModelAndView getByRecipeForm() {
        return new ModelAndView("get-by-recipe-ingredient");
    }
    @GetMapping("/getByRecipe")
    public ModelAndView getByRecipe(@RequestParam int recipeId) {
        RecipeDto recipeDto = recipeService.getById(recipeId);
        List<IngredientDto> ingredients = ingredientService.getAllByRecipe(recipeDto);
        ModelAndView modelAndView = new ModelAndView("list-ingredient");
        modelAndView.addObject("ingredients", ingredients);
        return modelAndView;
    }
    @GetMapping("/statusForm")
    public ModelAndView updateStatusForm() {
        return new ModelAndView("update-status-ingredient");
    }
    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id,
                                               @RequestParam Character status) {
        try{
            ingredientService.updateStatus(id, status);
            return ResponseEntity.ok("Ingredient status updated");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found");
        }

    }
}
