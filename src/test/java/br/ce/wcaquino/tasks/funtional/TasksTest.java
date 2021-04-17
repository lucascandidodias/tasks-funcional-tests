package br.ce.wcaquino.tasks.funtional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities().chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.24:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.0.24:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
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
	public void deveSalvarTarefaSemDescricao() throws MalformedURLException {
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
	public void deveSalvarTarefaSemData() throws MalformedURLException {
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
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
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
