//package co.edu.cue.GestorRecetas.services;
//
//import co.edu.cue.GestorRecetas.domain.entities.Ingredient;
//import co.edu.cue.GestorRecetas.domain.entities.Menu;
//import co.edu.cue.GestorRecetas.domain.entities.Recipe;
//import co.edu.cue.GestorRecetas.mapping.dtos.IngredientDto;
//import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
//import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
//import co.edu.cue.GestorRecetas.mapping.mappers.RecipeMapper;
//import co.edu.cue.GestorRecetas.repositories.IngredientRepository;
//import co.edu.cue.GestorRecetas.repositories.MenuRepository;
//import co.edu.cue.GestorRecetas.services.impl.IngredientServiceImpl;
//import co.edu.cue.GestorRecetas.services.impl.MenuServiceImpl;
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
//public class MenuServiceImplTest {
//    @Mock
//    private MenuRepository repository;
//
//    @InjectMocks
//    private MenuServiceImpl menuService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAll_ShouldReturnListOfMenus() {
//        // Arrange
//        Menu menu1 = Menu.builder()
//                .idMenu(1)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        Menu menu2 = Menu.builder()
//                .idMenu(2)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        List<Menu> menus = Arrays.asList(menu1, menu2);
//        when(repository.findAll()).thenReturn(menus);
//
//        // Act
//        List<MenuDto> result = menuService.getAll();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    void save_ShouldReturnSavedMenu() {
//        // Arrange
//        MenuDto menuDto = MenuDto.builder()
//                .idMenu(1)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        Menu menu = Menu.builder()
//                .idMenu(1)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        when(repository.save(any())).thenReturn(menu);
//
//        // Act
//        MenuDto result = menuService.save(menuDto);
//
//        // Assert
//        assertNotNull(result);
//        verify(repository, times(1)).save(any());
//    }
//
//    @Test
//    void update_ShouldReturnUpdatedMenu() {
//        // Arrange
//        MenuDto menuDto = MenuDto.builder()
//                .idMenu(1)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        Menu menu = Menu.builder()
//                .idMenu(1)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        when(repository.findById(anyInt())).thenReturn(Optional.of(menu));
//        when(repository.save(any())).thenReturn(menu);
//
//        // Act
//        MenuDto result = menuService.update(menuDto);
//
//        // Assert
//        assertNotNull(result);
//        verify(repository, times(1)).findById(anyInt());
//        verify(repository, times(1)).save(any());
//    }
//
//    @Test
//    void updateStatus_ShouldUpdateMenuStatus() {
//        // Arrange
//        int menuId = 1;
//        Character status = 'A';
//        Menu menu = Menu.builder()
//                .idMenu(menuId)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('D') // Estado anterior
//                .build();
//
//        when(repository.findById(menuId)).thenReturn(Optional.of(menu));
//
//        // Act
//        menuService.updateStatus(menuId, status);
//
//        // Assert
//        assertEquals(status, menu.getStatus());
//        verify(repository, times(1)).findById(menuId);
//        verify(repository, times(1)).save(menu);
//    }
//
//    @Test
//    void getById_ShouldReturnMenu() {
//        // Arrange
//        int menuId = 1;
//        Menu menu = Menu.builder()
//                .idMenu(menuId)
//                .recipes(Arrays.asList(new Recipe(/* inicializa aquí */)))
//                .status('A')
//                .build();
//
//        when(repository.findById(menuId)).thenReturn(Optional.of(menu));
//
//        // Act
//        MenuDto result = menuService.getById(menuId);
//
//        // Assert
//        assertNotNull(result);
//        verify(repository, times(1)).findById(menuId);
//    }
//}
