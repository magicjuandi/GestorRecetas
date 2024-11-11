package co.edu.cue.GestorRecetas.controllers;

import co.edu.cue.GestorRecetas.domain.entities.Recipe;
import co.edu.cue.GestorRecetas.mapping.dtos.MenuDto;
import co.edu.cue.GestorRecetas.mapping.dtos.RecipeDto;
import co.edu.cue.GestorRecetas.services.MenuService;
import co.edu.cue.GestorRecetas.services.RecipeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EndPointTests {

    @LocalServerPort
    private int port;

    @MockBean
    private MenuService menuService;

    @MockBean
    private RecipeService recipeService;//

    private final MenuDto menuDto = MenuDto.builder()
            .idMenu(1)
            .status('A')
            .recipes(Collections.emptyList())
            .build();

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void testUpdateMenu() {
        when(menuService.getById(anyInt())).thenReturn(menuDto);
        when(recipeService.getById(anyInt())).thenReturn(RecipeDto.builder().idRecipe(1).build());
        when(menuService.save(any(MenuDto.class))).thenReturn(menuDto);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .param("idMenu", 1)
                .param("recipes", 1, 2, 3)
                .when()
                .put("/api/menus/update")
                .then()
                .statusCode(200)
                .body(equalTo("Menu updated successfully"));
    }

    @Test
    void testUpdateMenuNotFound() {
        when(menuService.getById(anyInt())).thenReturn(null);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .param("idMenu", 1)
                .param("recipes", 1, 2, 3)
                .when()
                .put("/api/menus/update")
                .then()
                .statusCode(404)
                .body(equalTo("Menu not found"));
    }

    @Test
    void testGetAllMenus() {
        when(menuService.getAll()).thenReturn(List.of(menuDto));

        RestAssured.given()
                .when()
                .get("/api/menus/all")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void testUpdateStatus() {
        Mockito.doNothing().when(menuService).updateStatus(anyInt(), any(char.class));

        RestAssured.given()
                .param("id", 1)
                .param("status", 'A')
                .when()
                .patch("/api/menus/status")
                .then()
                .statusCode(200)
                .body(equalTo("Menu status updated successfully"));
    }

    @Test
    void testUpdateStatusNotFound() {
        Mockito.doThrow(new RuntimeException("Menu not found")).when(menuService).updateStatus(anyInt(), any(char.class));

        RestAssured.given()
                .param("id", 1)
                .param("status", 'A')
                .when()
                .patch("/api/menus/status")
                .then()
                .statusCode(404)
                .body(equalTo("Menu not found"));
    }

    @Test
    void testExportIngredientsMenuNotFound() {
        when(menuService.getById(anyInt())).thenReturn(null);

        RestAssured.given()
                .param("idMenu", 1)
                .when()
                .post("/api/menus/exportIngredients")
                .then()
                .statusCode(404)
                .body(equalTo("Menu not found"));
    }
}
