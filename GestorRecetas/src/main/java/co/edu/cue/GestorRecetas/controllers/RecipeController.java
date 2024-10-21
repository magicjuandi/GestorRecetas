package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.IngredientMapper;
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

@Controller
@RequestMapping("/Recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/createForm")//Metodo get para el formulario de creacion de Recetas.
    public ModelAndView createForm() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll();
        ModelAndView modelAndView = new ModelAndView("create-recipe");
        modelAndView.addObject("ingredients", ingredientDtos);
        return modelAndView;
    }
    @PostMapping("/create")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam String minutes,
                         @RequestParam String preparation,
                         @RequestParam List<Integer> ingredients) {
        Character character ='A';
        List<IngredientDto> selectedIngredients = ingredients.stream()
                .map(id -> ingredientService.getById(id))
                .toList();
        RecipeDto recipeDto = RecipeDto.builder()
                .name(name)
                .description(description)
                .minutes(minutes)
                .preparation(preparation)
                .ingredients(IngredientMapper.mapToEntities(selectedIngredients))
                .status(character)
                .build();
        recipeService.save(recipeDto);
        return "redirect:/Recipe/get";
    }
    @GetMapping("/updateForm")//Metodo get para el formulario de actualización de Recetas.
    public ModelAndView updateForm() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll();
        ModelAndView modelAndView = new ModelAndView("update-recipe");
        modelAndView.addObject("ingredients", ingredientDtos);
        return modelAndView;
    }
    @PostMapping("/update")
    public String update(@RequestParam int idRecipe,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) String minutes,
                         @RequestParam(required = false) String preparation,
                         @RequestParam(required = false) List<Integer> ingredients) {
        RecipeDto recipeDto = recipeService.getById(idRecipe);
        List<IngredientDto> selectedIngredients = ingredients.stream()
                .map(id -> ingredientService.getById(id))
                .toList();
        RecipeDto newRecipeDto = RecipeDto.builder()
                .idRecipe(recipeDto.idRecipe())
                .name((name!=null && !name.isEmpty()) ? name : recipeDto.name())
                .description((description!=null && !description.isEmpty())?description:recipeDto.description())
                .minutes((minutes!=null && !minutes.isEmpty())? minutes:recipeDto.minutes())
                .preparation((preparation!=null && !preparation.isEmpty())? preparation:recipeDto.preparation())
                .ingredients((ingredients.isEmpty())? IngredientMapper.mapToEntities(selectedIngredients):recipeDto.ingredients())
                .status(recipeDto.status())
                .favorite(recipeDto.favorite())
                .review(recipeDto.review())
                .build();
        recipeService.save(newRecipeDto);
        return "redirect:/Recipe/get";
    }
    @GetMapping("/updateReviewForm")//Metodo get para el formulario de creacion de reviews en Recetas.
    public ModelAndView updateReviewForm() {
        return new ModelAndView("update-review-recipe");
    }
    @PostMapping("/updateReview")
    public String update(@RequestParam int idRecipe,
                         @RequestParam String review) {
        RecipeDto recipeDto = recipeService.getById(idRecipe);
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
        return "redirect:/Recipe/get";
    }
    @GetMapping("/get")//Metodo get para el listado de Recetas.
    public ModelAndView get() {
        List<RecipeDto> recipes = recipeService.getAll();
        ModelAndView modelAndView = new ModelAndView("list-recipe");
        modelAndView.addObject("recipes", recipes);
        return modelAndView;
    }
    @GetMapping("/statusForm")//Metodo get para el formulario de actualizacion de estado en Recetas.
    public ModelAndView updateStatusForm() {
        return new ModelAndView("update-status-recipe");
    }
    @PostMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestParam int id,
                                               @RequestParam Character status) {
        try{
            recipeService.updateStatus(id, status);
            return ResponseEntity.ok("Recipe status updated");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }

    }
    @GetMapping("/favoriteForm")//Metodo get para el formulario de favorito en Recetas.
    public ModelAndView updateFavoriteForm() {
        return new ModelAndView("update-favorite-recipe");
    }
    @PostMapping("/favorite")
    public ResponseEntity<String> updateRecipeFavorite(@RequestParam int id) {
        try{
            recipeService.updateFavorite(id);
            return ResponseEntity.ok("Recipe favorite");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
    }

    @GetMapping("/pdfForm")//Metodo get para el formulario de creacion de pdf de Receta.
    public ModelAndView exportToPdfForm() {
        return new ModelAndView("pdf-recipe");
    }
    @PostMapping("/exportToPdf")
    public void exportRecipeToPdf(@RequestParam int id, HttpServletResponse response)throws IOException {
        RecipeDto recipeDto = recipeService.getById(id);
        if(recipeDto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Recipe not found");
            return;
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\""+recipeDto.name()+".pdf\"");
        try(PdfWriter writer = new PdfWriter(response.getOutputStream())) {
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            Paragraph title = new Paragraph(recipeDto.name())
                    .setFontSize(24)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);
            Paragraph description = new Paragraph(recipeDto.description())
                    .setMarginBottom(5);
            document.add(description);
            Paragraph minutes = new Paragraph(recipeDto.minutes()+"mins")
                    .setMarginBottom(5);
            document.add(minutes);
            Paragraph ingredientsTitle = new Paragraph("Ingredients")
                    .setMarginBottom(5)
                    .setBold()
                    .setMarginTop(10);
            document.add(ingredientsTitle);
            com.itextpdf.layout.element.List ingredientsList = new com.itextpdf.layout.element.List();
            for(IngredientDto ingredientDto : IngredientMapper.mapFrom(recipeDto.ingredients())){
                ListItem item = new ListItem(ingredientDto.name()+ " - "+ ingredientDto.quantity()+" - "
                        +ingredientDto.unit());
                ingredientsList.add(item);
            }
            document.add(ingredientsList);
            Paragraph preparationTitle = new Paragraph("Preparation")
                    .setMarginBottom(5)
                    .setMarginTop(10)
                    .setBold();
            document.add(preparationTitle);
            Paragraph preparation = new Paragraph(recipeDto.preparation())
                    .setMarginBottom(5);
            document.add(preparation);
            document.close();
        }catch(IOException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
        }

    }

}
