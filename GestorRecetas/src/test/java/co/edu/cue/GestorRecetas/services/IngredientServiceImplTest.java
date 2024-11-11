//package co.edu.cue.GestorRecetas.services;
//
//import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
//import co.edu.cue.GestorRecetas.domain.entities.Menu;
//import co.edu.cue.GestorRecetas.domain.entities.Recipe;
//import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
//import co.edu.cue.GestorRecetas.mapping.dtos.IngredientWODto;
//import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
//import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
//import co.edu.cue.GestorRecetas.repositories.IngredientRepository;
//import co.edu.cue.GestorRecetas.services.impl.IngredientServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//@SpringBootTest
//@ActiveProfiles("test")
//public class IngredientServiceImplTest {
//
//    @Mock
//    private IngredientRepository ingredientRepository;
//
//    @InjectMocks
//    private IngredientServiceImpl ingredientService;
//
//    private Ingredient ingredient1;
//    private Ingredient ingredient2;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Crear ingredientes de ejemplo para las pruebas
//        ingredient1 = new Ingredient();
//        ingredient1.setIdIngredient(1);
//        ingredient1.setName("Tomato");
//        ingredient1.setStatus('A');
//
//        ingredient2 = new Ingredient();
//        ingredient2.setIdIngredient(2);
//        ingredient2.setName("Lettuce");
//        ingredient2.setStatus('A');
//    }
//
//    @Test
//    public void updateStatus_ShouldUpdateIngredientStatus() {
//        // Configurar el mock del repositorio para encontrar un ingrediente
//        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient1));
//
//        // Actuar
//        ingredientService.updateStatus(1, 'I'); // Cambiar el estado de "Tomato" a "Inactivo"
//
//        // Verificar que el estado ha sido actualizado correctamente
//        assertEquals('I', ingredient1.getStatus());
//
//        // Verificar que el repositorio ha sido llamado
//        verify(ingredientRepository, times(1)).save(ingredient1);
//    }
//
//    @Test
//    public void updateStatus_IngredientNotFound_ShouldThrowException() {
//        // Configurar el mock para que el ingrediente no se encuentre
//        when(ingredientRepository.findById(99)).thenReturn(Optional.empty());
//
//        // Verificar que se lance la excepción cuando no se encuentra el ingrediente
//        assertThrows(NoSuchElementException.class, () -> ingredientService.updateStatus(99, 'I'));
//    }
//
//    @Test
//    public void getAll_ShouldReturnListOfIngredients() {
//        // Configurar el mock del repositorio para devolver una lista de ingredientes
//        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));
//
//        // Obtener la lista de ingredientes
//        List<Ingredient> ingredients = ingredientService.getAll();
//
//        // Verificar que la lista devuelta contiene los ingredientes esperados
//        assertEquals(2, ingredients.size());
//        assertTrue(ingredients.contains(ingredient1));
//        assertTrue(ingredients.contains(ingredient2));
//    }
//
//    @Test
//    public void getAll_ShouldReturnEmptyListIfNoIngredients() {
//        // Configurar el mock para devolver una lista vacía
//        when(ingredientRepository.findAll()).thenReturn(Arrays.asList());
//
//        // Obtener la lista de ingredientes
//        List<Ingredient> ingredients = ingredientService.getAll();
//
//        // Verificar que la lista devuelta está vacía
//        assertTrue(ingredients.isEmpty());
//    }
//
//    @Test
//    public void getById_ShouldReturnIngredient() {
//        // Configurar el mock para que devuelva el ingrediente con id 1
//        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient1));
//
//        // Obtener el ingrediente por su id
//        Ingredient result = ingredientService.getById(1);
//
//        // Verificar que el ingrediente devuelto es el correcto
//        assertEquals(ingredient1, result);
//    }
//
//    @Test
//    public void getById_IngredientNotFound_ShouldThrowException() {
//        // Configurar el mock para que no se encuentre el ingrediente
//        when(ingredientRepository.findById(99)).thenReturn(Optional.empty());
//
//        // Verificar que se lance la excepción cuando el ingrediente no existe
//        assertThrows(NoSuchElementException.class, () -> ingredientService.getById(99));
//    }
//
//    @Test
//    public void save_ShouldReturnSavedIngredient() {
//        // Configurar el mock para simular que el ingrediente se guarda
//        when(ingredientRepository.save(ingredient1)).thenReturn(ingredient1);
//
//        // Guardar el ingrediente
//        Ingredient savedIngredient = ingredientService.save(ingredient1);
//
//        // Verificar que el ingrediente guardado es el esperado
//        assertEquals(ingredient1, savedIngredient);
//
//        // Verificar que el repositorio fue llamado para guardar el ingrediente
//        verify(ingredientRepository, times(1)).save(ingredient1);
//    }
//}