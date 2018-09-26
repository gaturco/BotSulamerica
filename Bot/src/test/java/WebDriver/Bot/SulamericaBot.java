package WebDriver.Bot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SulamericaBot {

	static WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");

		driver = new ChromeDriver();
	}

	@Test
	public void browser() throws Exception {
		// Scanner sc = new Scanner(System.in);
		// System.out.println("Codigo do paciente: ");
		// String codigo = sc.nextLine();
		// String codigo = JOptionPane.showInputDialog(null, "Codigo do segurado: ",
		// "Sulamerica", JOptionPane.PLAIN_MESSAGE);
		String codigo = "59200140006785890107";
		String codigo1 = codigo.substring(0, 3);
		String codigo2 = codigo.substring(3, 8);
		String codigo3 = codigo.substring(8, 12);
		String codigo4 = codigo.substring(12, 16);
		String codigo5 = codigo.substring(16, 20);
		// System.out.println(codigo1);
		// System.out.println(codigo2);
		// System.out.println(codigo3);
		// System.out.println(codigo4);
		// System.out.println(codigo5);

		int time = 10;
		WebDriverWait wait = new WebDriverWait(driver, time);

		// LOGAR
		driver.get("https://saude.sulamericaseguros.com.br/prestador/login/");
		driver.findElement(By.id("code")).sendKeys("00035000SPP0");
		driver.findElement(By.id("user")).sendKeys("master");
		driver.findElement(By.id("senha")).sendKeys("jan2705#");
		driver.findElement(By.id("entrarLogin")).click();

		// FECHA TELAS INDESEJADAS
		driver.findElement(By.id("btnLayerFecharuserDialog")).click();
		driver.findElement(By.className("img-responsive")).click();

		// ENTRA NA PAGINA DE FATURAMENTO
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Serviços Médicos")));
		driver.findElement(By.linkText("Serviços Médicos")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Acessar")));
		driver.findElement(By.linkText("Acessar")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Validar procedimento autorizado")));
		driver.findElement(By.linkText("Validar procedimento autorizado")).click();

		// PRENCHE CODIGO DO PACIENTE

		driver.findElement(By.id("codigo-beneficiario-1")).sendKeys(codigo1);
		driver.findElement(By.id("codigo-beneficiario-2")).sendKeys(codigo2);
		driver.findElement(By.id("codigo-beneficiario-3")).sendKeys(codigo3);
		driver.findElement(By.id("codigo-beneficiario-4")).sendKeys(codigo4);
		driver.findElement(By.id("codigo-beneficiario-5")).sendKeys(codigo5);

		Thread.sleep(1500);
		do {
			driver.findElement(By.id("tipo-pesquisa-codigo-beneficiario")).click();
		} while (!driver.findElement(By.id("tipo-pesquisa-codigo-beneficiario")).isSelected());

		Thread.sleep(3000);
		driver.findElement(By.id("btn-pesquisar-procedimentos")).click();

		Thread.sleep(3000);
		WebElement table = driver.findElement(By.xpath("//div[@class='tab-procAutorizados']/table"));
		WebElement procedimento = table.findElement(By.xpath("//tr/td[contains(text(), 'ALICIA DIAS DE OLIVEIRA')]"));
		procedimento.click();

		if (driver.findElement(By.id("selectTipoGuia")).isDisplayed()) {
			Select dropdown = new Select(driver.findElement(By.id("selectTipoGuia")));
			dropdown.selectByVisibleText("SADT");
			driver.findElement(By.id("btn-confirmar-pesquisa")).click();
		}

		// sc.close();
	}

	@After
	public void tearDown() {
		// driver.close();
	}

}
