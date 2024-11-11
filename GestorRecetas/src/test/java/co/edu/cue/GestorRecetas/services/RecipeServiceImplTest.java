package co.edu.cue.GestorRecetas.services;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.repositories.IngredientRepository;
import co.edu.cue.GestorRecetas.repositories.MenuRepository;
import co.edu.cue.GestorRecetas.repositories.RecipeRepository;
import co.edu.cue.GestorRecetas.services.impl.IngredientServiceImpl;
import co.edu.cue.GestorRecetas.services.impl.MenuServiceImpl;
import co.edu.cue.GestorRecetas.services.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository repository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnListOfRecipes() {
        // Arrange
        Recipe recipe1 = Recipe.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        Recipe recipe2 = Recipe.builder()
                .idRecipe(2)
                .name("Tacos")
                .description("Traditional Mexican dish")
                .minutes("20")
                .preparation("Prepare ingredients, assemble tacos.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(true)
                .review("Awesome!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
        when(repository.findAll()).thenReturn(recipes);

        // Act
        List<RecipeDto> result = recipeService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void save_ShouldReturnSavedRecipe() {
        // Arrange
        RecipeDto recipeDto = RecipeDto.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        Recipe recipe = Recipe.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.save(any())).thenReturn(recipe);

        // Act
        RecipeDto result = recipeService.save(recipeDto);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_ShouldReturnUpdatedRecipe() {
        // Arrange
        RecipeDto recipeDto = RecipeDto.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        Recipe recipe = Recipe.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.findById(anyInt())).thenReturn(Optional.of(recipe));
        when(repository.save(any())).thenReturn(recipe);

        // Act
        RecipeDto result = recipeService.update(recipeDto);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateStatus_ShouldUpdateRecipeStatus() {
        // Arrange
        int recipeId = 1;
        Character status = 'D'; // Nuevo estado
        Recipe recipe = Recipe.builder()
                .idRecipe(recipeId)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A') // Estado anterior
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        recipeService.updateStatus(recipeId, status);

        // Assert
        assertEquals(status, recipe.getStatus());
        verify(repository, times(1)).findById(recipeId);
        verify(repository, times(1)).save(recipe);
    }

    @Test
    void updateFavorite_ShouldToggleRecipeFavorite() {
        // Arrange
        int recipeId = 1;
        Recipe recipe = Recipe.builder()
                .idRecipe(recipeId)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false) // Estado inicial
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        recipeService.updateFavorite(recipeId);

        // Assert
        assertTrue(recipe.getFavorite());
        verify(repository, times(1)).findById(recipeId);
        verify(repository, times(1)).save(recipe);
    }

    @Test
    void getById_ShouldReturnRecipe() {
        // Arrange
        int recipeId = 1;
        Recipe recipe = Recipe.builder()
                .idRecipe(recipeId)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        RecipeDto result = recipeService.getById(recipeId);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(recipeId);
    }

    @Test
    void updateReview_ShouldUpdateRecipeReview() {
        // Arrange
        int recipeId = 1;
        String newReview = "Excellent recipe!";
        Recipe recipe = Recipe.builder()
                .idRecipe(recipeId)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(false)
                .review("Great recipe!") // Estado anterior
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        when(repository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        recipeService.updateReview(recipeId, newReview);

        // Assert
        assertEquals(newReview, recipe.getReview());
        verify(repository, times(1)).findById(recipeId);
        verify(repository, times(1)).save(recipe);
    }
}
