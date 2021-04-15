package br.ce.wcaquino.tasks.funtional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Tarefa Teste 1");
			driver.findElement(By.id("dueDate")).sendKeys("21/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Success!", mensagem);

		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();			
			driver.findElement(By.id("dueDate")).sendKeys("21/10/2030");
			driver.findElement(By.id("saveButton")).click();
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the task description", mensagem);

		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();			
			driver.findElement(By.id("task")).sendKeys("Tarefa Teste 1");			
			driver.findElement(By.id("saveButton")).click();
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the due date", mensagem);

		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Tarefa Teste 1");
			driver.findElement(By.id("dueDate")).sendKeys("21/10/1999");
			driver.findElement(By.id("saveButton")).click();
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Due date must not be in past", mensagem);

		} finally {
			driver.quit();
		}
	}

}
