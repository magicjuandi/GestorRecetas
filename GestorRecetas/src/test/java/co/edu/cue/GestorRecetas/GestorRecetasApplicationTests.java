package co.edu.cue.GestorRecetas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestorRecetasApplicationTests {




	private WebDriver driver;


	@BeforeEach
	void getUp(){
		driver = new ChromeDriver();
	}

	@Test
	void createForm() {
		driver.get("http://localhost:8080/Ingredient/createForm");
		driver.findElement(By.id("name")).sendKeys("IngredientePrueba");
		driver.findElement(By.id("quantity")).sendKeys("20");
		driver.findElement(By.id("unit")).sendKeys("Kilograms (kg)");
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary btn-block')and text()='Create Ingredient']")).click();
		driver.close();
	}

	@Test
	void updateForm() {
		driver.get("http://localhost:8080/Ingredient/updateForm");
		driver.findElement(By.id("idIngredient")).sendKeys("8");
		driver.findElement(By.id("name")).sendKeys("IngredienteNuevoPrueba");
		driver.findElement(By.id("quantity")).sendKeys("50");
		driver.findElement(By.id("unit")).sendKeys("Kilograms (kg)");
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary btn-block')and text()='Update Ingredient']")).click();
		driver.close();
	}
	@Test
	void updateStatusForm() {
		driver.get("http://localhost:8080/Ingredient/statusForm");
		driver.findElement(By.id("id")).sendKeys("3");
		driver.findElement(By.id("status")).sendKeys("Inactive (I)");
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary btn-block')and text()='Update Status']")).click();
		driver.close();
	}

}
