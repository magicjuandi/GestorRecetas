package co.edu.cue.GestorRecetas.services;

import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
import co.edu.cue.GestorRecetas.domain.entities.Menu;
import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
import co.edu.cue.GestorRecetas.repositories.IngredientRepository;
import co.edu.cue.GestorRecetas.services.impl.IngredientServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
public class IngredientServiceImplTest  {
    @Mock
    private IngredientRepository repository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnListOfIngredients() {
        // Arrange
        Ingredient ingredient1 = Ingredient.builder()
                .idIngredient(1)
                .name("Tomato")
                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .idIngredient(2)
                .name("Spaghetti")
                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(200)
                .unit("grams")
                .status('A')
                .build();

        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);
        when(repository.findAll()).thenReturn(ingredients);

        // Act
        List<IngredientWODto> result = ingredientService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllByRecipe_ShouldReturnListOfIngredients() {
        // Arrange
        RecipeDto recipe = RecipeDto.builder()
                .idRecipe(1)
                .name("Spaghetti Bolognese")
                .description("Delicious spaghetti with meat sauce")
                .minutes("30")
                .preparation("Boil spaghetti, cook sauce, mix together.")
                .ingredients(Arrays.asList(new Ingredient(/* inicializa aquí */)))
                .favorite(true)
                .review("Great recipe!")
                .status('A')
                .menus(Arrays.asList(new Menu(/* inicializa aquí */)))
                .build();

        Ingredient ingredient = Ingredient.builder()
                .idIngredient(1)
                .name("Spaghetti")
                .recipes(Arrays.asList(RecipeMapper.mapFrom(recipe)))
                .quantity(200)
                .unit("grams")
                .status('A')
                .build();

        List<Ingredient> ingredients = Arrays.asList(ingredient);
        when(repository.findAllByRecipes(any())).thenReturn(ingredients);

        // Act
        List<IngredientDto> result = ingredientService.getAllByRecipe(recipe);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAllByRecipes(any());
    }

    @Test
    void save_ShouldReturnSavedIngredient() {
        // Arrange
        IngredientDto ingredientDto = IngredientDto.builder()
                .idIngredient(1)
                .name("Tomato")
                .recipe(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        Ingredient ingredient = Ingredient.builder()
                .idIngredient(1)
                .name("Tomato")
                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        when(repository.save(any())).thenReturn(ingredient);

        // Act
        IngredientDto result = ingredientService.save(ingredientDto);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_ShouldReturnUpdatedIngredient() {
        // Arrange
        IngredientDto ingredientDto = IngredientDto.builder()
                .idIngredient(1)
                .name("Tomato")
                .recipe(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        Ingredient ingredient = Ingredient.builder()
                .idIngredient(1)
                .name("Tomato")
                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        when(repository.findById(anyInt())).thenReturn(Optional.of(ingredient));
        when(repository.save(any())).thenReturn(ingredient);

        // Act
        IngredientDto result = ingredientService.update(ingredientDto);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateStatus_ShouldUpdateIngredientStatus() {
        // Arrange
        int ingredientId = 1;
        Character status = 'A';
        Ingredient ingredient = Ingredient.builder()
                .idIngredient(ingredientId)
                .name("Tomato")
                .quantity(5)
                .unit("pieces")
                .status('D') // Estado anterior
                .build();

        when(repository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Act
        ingredientService.updateStatus(ingredientId, status);

        // Assert
        assertEquals(status, ingredient.getStatus());
        verify(repository, times(1)).findById(ingredientId);
        verify(repository, times(1)).save(ingredient);
    }

    @Test
    void getById_ShouldReturnIngredient() {
        // Arrange
        int ingredientId = 1;
        Ingredient ingredient = Ingredient.builder()
                .idIngredient(ingredientId)
                .name("Tomato")
                .quantity(5)
                .unit("pieces")
                .status('A')
                .build();

        when(repository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Act
        IngredientWODto result = ingredientService.getById(ingredientId);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(ingredientId);
    }
}
