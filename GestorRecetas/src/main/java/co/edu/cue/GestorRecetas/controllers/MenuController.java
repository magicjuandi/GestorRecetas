package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientWOMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.MenuMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.services.IngredientService;
import co.edu.cue.GestorRecetas.services.MenuService;
import co.edu.cue.GestorRecetas.services.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam List<Integer> recipes) {
        Character status = 'A';
        List<RecipeDto> selectedRecipes = recipes.stream()
                .map(id -> recipeService.getById(id))
                .toList();
        MenuDto menuDto = MenuDto.builder()
                .recipes(RecipeMapper.mapToEntities(selectedRecipes))
                .status(status)
                .build();
        menuService.save(menuDto);
        return ResponseEntity.ok("Menu created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam int idMenu,
                                         @RequestParam(required = false) List<Integer> recipes) {
        MenuDto menuDto = menuService.getById(idMenu);
        if (menuDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
        }

        List<RecipeDto> selectedRecipes = recipes != null ?
                recipes.stream().map(id -> recipeService.getById(id)).toList() : null;

        MenuDto updatedMenuDto = MenuDto.builder()
                .idMenu(menuDto.idMenu())
                .recipes((selectedRecipes != null && !selectedRecipes.isEmpty())
                        ? RecipeMapper.mapToEntities(selectedRecipes) : menuDto.recipes())
                .status(menuDto.status())
                .build();
        menuService.save(updatedMenuDto);
        return ResponseEntity.ok("Menu updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuDto>> getAll() {
        List<MenuDto> menus = menuService.getAll();
        return ResponseEntity.ok(menus);
    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id,
                                               @RequestParam Character status) {
        try {
            menuService.updateStatus(id, status);
            return ResponseEntity.ok("Menu status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
        }
    }

    @PostMapping("/exportIngredients")
    public ResponseEntity<?> exportIngredients(@RequestParam int idMenu) {
        MenuDto menuDto = menuService.getById(idMenu);
        if (menuDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
        }

        Map<String, IngredientWODto> ingredientDtoMap = new HashMap<>();
        for (RecipeDto recipeDto : RecipeMapper.mapFrom(menuDto.recipes())) {
            for (IngredientWODto ingredientDto : IngredientWOMapper.mapFrom(recipeDto.ingredients())) {
                String key = ingredientDto.name();
                ingredientDtoMap.merge(key, ingredientDto, (existing, newIngredient) -> {
                    int updatedQuantity = existing.quantity() + newIngredient.quantity();
                    return IngredientWODto.builder()
                            .idIngredient(existing.idIngredient())
                            .name(existing.name())
                            .quantity(updatedQuantity)
                            .unit(existing.unit())
                            .build();
                });
            }
        }

        return ResponseEntity.ok(ingredientDtoMap.values());
    }


}

