package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientWOMapper;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.services.IngredientService;
import co.edu.cue.GestorRecetas.services.RecipeService;
import com.itextpdf.kernel.pdf.PdfDocument;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;

    // Método GET para obtener todos los ingredientes disponibles
    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientWODto>> getIngredients() {
        List<IngredientWODto> ingredientDtos = ingredientService.getAll();
        return ResponseEntity.ok(ingredientDtos);
    }

    // Método POST para crear una nueva receta con los detalles proporcionados
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam String minutes,
                                         @RequestParam String preparation,
                                         @RequestParam List<Integer> ingredients) {
        Character character = 'A';
        List<IngredientWODto> selectedIngredients = ingredients.stream()
                .map(id -> ingredientService.getById(id))
                .toList();
        RecipeDto recipeDto = RecipeDto.builder()
                .name(name)
                .description(description)
                .minutes(minutes)
                .preparation(preparation)
                .ingredients(IngredientWOMapper.mapToEntities(selectedIngredients))
                .status(character)
                .build();
        recipeService.save(recipeDto);
        return ResponseEntity.ok("Recipe created successfully");
    }

    // Método POST para actualizar una receta existente
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam int idRecipe,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String description,
                                         @RequestParam(required = false) String minutes,
                                         @RequestParam(required = false) String preparation,
                                         @RequestParam(required = false) List<Integer> ingredients) {
        RecipeDto recipeDto = recipeService.getById(idRecipe);
        if (recipeDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
        List<IngredientWODto> selectedIngredients = (ingredients != null) ?
                ingredients.stream().map(id -> ingredientService.getById(id)).toList() : null;

        RecipeDto newRecipeDto = RecipeDto.builder()
                .idRecipe(recipeDto.idRecipe())
                .name((name != null && !name.isEmpty()) ? name : recipeDto.name())
                .description((description != null && !description.isEmpty()) ? description : recipeDto.description())
                .minutes((minutes != null && !minutes.isEmpty()) ? minutes : recipeDto.minutes())
                .preparation((preparation != null && !preparation.isEmpty()) ? preparation : recipeDto.preparation())
                .ingredients((selectedIngredients != null) ? IngredientWOMapper.mapToEntities(selectedIngredients) : recipeDto.ingredients())
                .status(recipeDto.status())
                .favorite(recipeDto.favorite())
                .review(recipeDto.review())
                .build();
        recipeService.save(newRecipeDto);
        return ResponseEntity.ok("Recipe updated successfully");
    }

    // Método POST para actualizar la reseña de una receta
    @PostMapping("/updateReview")
    public ResponseEntity<String> updateReview(@RequestParam int idRecipe, @RequestParam String review) {
        RecipeDto recipeDto = recipeService.getById(idRecipe);
        if (recipeDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
        RecipeDto newRecipeDto = RecipeDto.builder()
                .idRecipe(recipeDto.idRecipe())
                .name(recipeDto.name())
                .description(recipeDto.description())
                .minutes(recipeDto.minutes())
                .preparation(recipeDto.preparation())
                .ingredients(recipeDto.ingredients())
                .status(recipeDto.status())
                .favorite(recipeDto.favorite())
                .review(review)
                .build();
        recipeService.save(newRecipeDto);
        return ResponseEntity.ok("Recipe review updated");
    }

    // Método GET para listar todas las recetas
    @GetMapping("/get")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAll();
        return ResponseEntity.ok(recipes);
    }

    // Método POST para actualizar el estado de una receta
    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id, @RequestParam Character status) {
        try {
            recipeService.updateStatus(id, status);
            return ResponseEntity.ok("Recipe status updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
    }

    // Método POST para marcar una receta como favorita
    @PostMapping("/favorite")
    public ResponseEntity<String> updateRecipeFavorite(@RequestParam int id) {
        try {
            recipeService.updateFavorite(id);
            return ResponseEntity.ok("Recipe favorite updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
    }

    // Método POST para exportar una receta a PDF
    @PostMapping("/exportToPdf")
    public void exportRecipeToPdf(@RequestParam int id, HttpServletResponse response) throws IOException {
        RecipeDto recipeDto = recipeService.getById(id);
        if (recipeDto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
            return;
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + recipeDto.name() + ".pdf\"");

        try (PdfWriter writer = new PdfWriter(response.getOutputStream())) {
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            document.add(new Paragraph(recipeDto.name()).setFontSize(24).setBold().setMarginBottom(10));
            document.add(new Paragraph(recipeDto.description()).setMarginBottom(5));
            document.add(new Paragraph(recipeDto.minutes() + " mins").setMarginBottom(5));
            document.add(new Paragraph("Ingredients").setBold().setMarginTop(10).setMarginBottom(5));
            com.itextpdf.layout.element.List ingredientsList = new com.itextpdf.layout.element.List();
            for (IngredientDto ingredientDto : IngredientMapper.mapFrom(recipeDto.ingredients())) {
                ingredientsList.add(new ListItem(ingredientDto.name() + " - " + ingredientDto.quantity() + " - " + ingredientDto.unit()));
            }
            document.add(ingredientsList);
            document.add(new Paragraph("Preparation").setBold().setMarginTop(10).setMarginBottom(5));
            document.add(new Paragraph(recipeDto.preparation()).setMarginBottom(5));
            document.close();
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        }
    }
}
