package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientMapper;
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

@Controller
@RequestMapping("/Menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/createForm")//Metodo get con el formulario para la creacion de Menús.
    public ModelAndView createForm() {
        List<RecipeDto> recipeDtos = recipeService.getAll();
        ModelAndView modelAndView = new ModelAndView("create-menu");
        modelAndView.addObject("recipes", recipeDtos);
        return modelAndView;
    }
    @PostMapping("/create")
    public String create(@RequestParam List<Integer> recipes) {
        Character character ='A';
        List<RecipeDto> selectedRecipes = recipes.stream()
                .map(id -> recipeService.getById(id))
                .toList();
        MenuDto menuDto = MenuDto.builder()
                .recipes(RecipeMapper.mapToEntities(selectedRecipes))
                .status(character)
                .build();
        menuService.save(menuDto);
        return "redirect:/Menu/get";
    }
    @GetMapping("/updateForm")//Metodo get con el formulario para la actualización de Menús.
    public ModelAndView updateForm() {
        List<RecipeDto> recipeDtos = recipeService.getAll();
        ModelAndView modelAndView = new ModelAndView("update-menu");
        modelAndView.addObject("recipes", recipeDtos);
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(@RequestParam int idMenu,
                         @RequestParam(required = false) List<Integer> recipes) {
        MenuDto menuDto = menuService.getById(idMenu);
        List<RecipeDto> selectedRecipes = recipes.stream()
                .map(id -> recipeService.getById(id))
                .toList();
        MenuDto newMenuDto = MenuDto.builder()
                .idMenu(menuDto.idMenu())
                .recipes((recipes.isEmpty())?RecipeMapper.mapToEntities(selectedRecipes):menuDto.recipes())
                .status(menuDto.status())
                .build();
        menuService.save(newMenuDto);
        return "redirect:/Menu/get";
    }
    @GetMapping("/get")//Metodo get para el listado de Menús.
    public ModelAndView get() {
        List<MenuDto> menus = menuService.getAll();
        ModelAndView modelAndView = new ModelAndView("list-menu");
        modelAndView.addObject("menus", menus);
        return modelAndView;
    }

    @GetMapping("/statusForm")//Metodo get para cambiar el estado de Menú.
    public ModelAndView updateStatusForm() {
        return new ModelAndView("update-status-menu");
    }
    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id,
                                               @RequestParam Character status) {
        try{
            menuService.updateStatus(id, status);
            return ResponseEntity.ok("Menu status updated");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu not found");
        }

    }
    @GetMapping("/excelForm")//Metodo get para llamar el excel de ingredientes del Menú.
    public ModelAndView excelForm() {
        return new ModelAndView("excel-menu");
    }
    @PostMapping("/exportIngredients")
    public ModelAndView exportIngredients(@RequestParam int idMenu, Model model) {
        ModelAndView modelAndView = new ModelAndView("ingredientList");

        MenuDto menuDto = menuService.getById(idMenu);
        if(menuDto ==null){
            modelAndView.setViewName("error");
            modelAndView.addObject("message", "Menu not found");
            return modelAndView;
        }
        Map<String, IngredientDto> ingredientDtoMap = new HashMap<>();
        for(RecipeDto recipeDto : RecipeMapper.mapFrom(menuDto.recipes())) {
            for (IngredientDto ingredientDto : IngredientMapper.mapFrom(recipeDto.ingredients())) {
                String key = ingredientDto.name();
                if (ingredientDtoMap.containsKey(key)) {
                    IngredientDto existingIngredientDto = ingredientDtoMap.get(key);
                    int updatedQuantity = existingIngredientDto.quantity()+ingredientDto.quantity();
                    IngredientDto updatedIngredientDto = IngredientDto.builder()
                            .idIngredient(existingIngredientDto.idIngredient())
                            .name(existingIngredientDto.name())
                            .quantity(updatedQuantity)
                            .unit(existingIngredientDto.unit())
                            .build();
                    ingredientDtoMap.put(key, updatedIngredientDto);
                } else {
                    ingredientDtoMap.put(key, ingredientDto);
                }
            }
        }
        modelAndView.addObject("ingredients", ingredientDtoMap.values());
        return modelAndView;
    }

}
