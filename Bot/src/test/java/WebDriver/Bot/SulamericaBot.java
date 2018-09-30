package WebDriver.Bot;

import java.util.List;

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
		String data = "26092018";
		String indicacao = "DEPRESSAO";
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

		Thread.sleep(1000);
		WebElement table = driver.findElement(By.xpath("//div[@class='tab-procAutorizados']/table"));
		List<WebElement> tr = table.findElements(By.cssSelector("tr"));
		List<WebElement> td = table.findElements(By.cssSelector("td"));
		String autorizacao = td.get(0).getText();
		// System.out.println(autorizacao);
		// WebElement procedimento =
		// table.findElement(By.xpath("//tr/td[contains(text(), 'ALICIA DIAS DE
		// OLIVEIRA')]"));
		tr.get(1).click();

		if (driver.findElement(By.id("selectTipoGuia")).isDisplayed()) {
			Select dropdownGuia = new Select(driver.findElement(By.id("selectTipoGuia")));
			dropdownGuia.selectByVisibleText("SADT");
			driver.findElement(By.id("btn-confirmar-pesquisa")).click();
		}

		// PREENCHE A TELA DO RERENCIADO
		driver.findElement(By.id("numero-guia-principal")).sendKeys(autorizacao);
		driver.findElement(By.id("numero-profissional-operadora")).sendKeys("00035000SPP0");
		driver.findElement(By.id("nome-contratado-solicitante")).sendKeys("JANETE ESPOSITO");
		driver.findElement(By.name("guia-sadt.profissional-solicitante.nome")).sendKeys("JANETE ESPOSITO");
		Select dropdownConselho = new Select(driver.findElement(By.id("conselho-profissional")));
		dropdownConselho.selectByVisibleText("CRP");
		Select dropdownUf = new Select(driver.findElement(By.id("uf-conselho-profissional")));
		dropdownUf.selectByVisibleText("SP");
		driver.findElement(By.id("numero-registro-conselho")).sendKeys("350003");
		driver.findElement(By.id("cbo")).sendKeys("251510");
		Thread.sleep(3000);
		WebElement autoOptions = driver.findElement(By.id("ui-id-1"));

		List<WebElement> options = autoOptions.findElements(By.tagName("a"));
		for(WebElement optionToSelect : options){
	        if(optionToSelect.getText().equals("251510 - PSICOLOGO CLINICO")) {
	            optionToSelect.click();
	            break;
	        }
		}
		
		Select dropdownCarater = new Select(driver.findElement(By.id("carater-atendimento")));
		dropdownCarater.selectByVisibleText("Eletivo");
		
		driver.findElement(By.id("data-solicitacao")).sendKeys(data);
		
		Select dropdownRn = new Select(driver.findElement(By.id("flag-atendimento-rn")));
		dropdownRn.selectByVisibleText("Não");
		
		driver.findElement(By.id("indicacao-clinica")).sendKeys(indicacao);
		
		driver.findElement(By.name("pes.descricao-procedimento")).sendKeys("consulta em");
		Thread.sleep(3000);
		WebElement autoOptionsProcedimento = driver.findElement(By.id("ui-id-3"));

		List<WebElement> optionsProcedimento = autoOptionsProcedimento.findElements(By.tagName("a"));
		for(WebElement optionToSelectProcedimento : optionsProcedimento){
	        if(optionToSelectProcedimento.getText().equals("CONSULTA EM PSICOLOGIA")) {
	            optionToSelectProcedimento.click();
	            break;
	        }
		}
		
		driver.findElement(By.name("pes.quantidade-solicitada")).sendKeys("6");
		driver.findElement(By.name("pes.quantidade-autorizada")).sendKeys("6");
		driver.findElement(By.id("incluiPes")).click();
		// sc.close();
	}

	@After
	public void tearDown() {
		// driver.close();
	}

}
