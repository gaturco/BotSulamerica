
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SulamericaBot {

	static WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/chromedriver_win32/chromedriver.exe");

		driver = new ChromeDriver();

	}

	@Test
	public void browser() throws Exception {

		String codigo;

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
//		do { 
//			
//			codigo = JOptionPane.showInputDialog(null, "Codigo do segurado: ",
//			"Sulamerica", JOptionPane.PLAIN_MESSAGE);
//
//		} while (codigo.length() != 20);
		codigo = "54588888454738550019"; //codigo teste
		String codigo1 = codigo.substring(0, 3);
		String codigo2 = codigo.substring(3, 8);
		String codigo3 = codigo.substring(8, 12);
		String codigo4 = codigo.substring(12, 16);
		String codigo5 = codigo.substring(16, 20);
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
		tr.get(1).click();

		if (driver.findElement(By.id("selectTipoGuia")).isDisplayed()) {
			Select dropdownGuia = new Select(driver.findElement(By.id("selectTipoGuia")));
			dropdownGuia.selectByVisibleText("SADT");
			driver.findElement(By.id("btn-confirmar-pesquisa")).click();
		}

		// PREENCHE A TELA DO REFERENCIADO
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
		for (WebElement optionToSelect : options) {
			if (optionToSelect.getText().equals("251510 - PSICOLOGO CLINICO")) {
				optionToSelect.click();
				break;
			}
		}

		Select dropdownCarater = new Select(driver.findElement(By.id("carater-atendimento")));
		dropdownCarater.selectByVisibleText("Eletivo");

		String data = driver.findElement(By.name("guia-sadt.data-autorizacao")).getAttribute("value");
		
		driver.findElement(By.id("data-solicitacao")).sendKeys(data);

		Select dropdownRn = new Select(driver.findElement(By.id("flag-atendimento-rn")));
		dropdownRn.selectByVisibleText("Não");
		
//		String indicacao = JOptionPane.showInputDialog(null, "Transtorno:",
//		"Sulamerica", JOptionPane.PLAIN_MESSAGE);
		String indicacao = "depressao"; //teste indicacao
		driver.findElement(By.id("indicacao-clinica")).sendKeys(indicacao.toUpperCase());
		
		driver.findElement(By.name("pes.codigo-procedimento")).sendKeys("50000462");
		driver.findElement(By.className("btn-busca-procedimento")).click();
		
//		String quantidade = JOptionPane.showInputDialog(null, "Quantidade:",
//				"Sulamerica", JOptionPane.PLAIN_MESSAGE);
		String quantidade = "6";
		driver.findElement(By.name("pes.quantidade-solicitada")).sendKeys(quantidade);
		driver.findElement(By.name("pes.quantidade-autorizada")).sendKeys(quantidade);
		driver.findElement(By.id("incluiPes")).click();

		driver.findElement(By.className("bt-excluir")).click();

		// DADOS ATENDIMENTO
		Select dropdownTipoAtendimento = new Select(driver.findElement(By.id("tipo-atendimento")));
		dropdownTipoAtendimento.selectByVisibleText("Terapias");
		Select dropdownIndicacaoAcidente = new Select(driver.findElement(By.id("indicador-acidente")));
		dropdownIndicacaoAcidente.selectByVisibleText("Não Acidente");
		Select dropdownTipoConsulta = new Select(driver.findElement(By.id("tipo-consulta")));
		dropdownTipoConsulta.selectByVisibleText("Retorno");

		// PROCEDIMENTOS E EXAMES REALIZADOS 50000462
		List<String> datas = new ArrayList<String>();
		int qtde = Integer.parseInt(quantidade);
		for (int j = 0; j < qtde; j++) {
			
//			datas.add(JOptionPane.showInputDialog(null, "data "+(j+1)+":",
//					"Sulamerica", JOptionPane.PLAIN_MESSAGE));
			datas.add("07012019");
			datas.add("08012019");
			datas.add("09012019");
			datas.add("10012019");
			datas.add("11012019");
			datas.add("12012019");
		}
		
		String adiciona;
		
		for (int i = 0; i < qtde; i++) {
			driver.findElement(By.name("per.data")).sendKeys(datas.get(i));
			driver.findElement(By.xpath("//*[@id=\"formPer\"]/fieldset/div[4]/div[1]/div[1]/input")).sendKeys("50000462");
			driver.findElement(By.xpath("//*[@id=\"formPer\"]/fieldset/div[4]/div[1]/div[2]/a")).click();
			driver.findElement(By.name("per.quantidade")).sendKeys("1");
			driver.findElement(By.name("per.valor-unitario")).sendKeys("5172");
			driver.findElement(By.id("incluirPer")).click();
			if (i == 0) {
				driver.findElement(By.xpath("//*[@id=\"1\"]/td[1]/a")).click();
			}
			adiciona = Integer.toString(i+2);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='" + adiciona + "']/td[13]/span")));
		    driver.findElement(By.xpath("//*[@id='" + adiciona + "']/td[13]/span")).click();
			Thread.sleep(750);
			WebElement mais = driver.findElement(By.xpath("//*[@id='" + adiciona + "']/td[13]/span"));
			Actions action = new Actions(driver);
			action.moveToElement(mais).click().build().perform();
			
			Select dropdownGrauPart = new Select(driver.findElement(By.name("ipe.grau-participacao")));
			dropdownGrauPart.selectByVisibleText("Clínico");
			Select dropdownTipoDoc = new Select(driver.findElement(By.name("ipe.tipo-documento")));
			dropdownTipoDoc.selectByVisibleText("Código na Operadora");
			driver.findElement(By.name("ipe.numero-documento")).sendKeys("00035000SPP0");
			driver.findElement(By.name("ipe.nome-profissional")).sendKeys("JANETE ESPOSITO");
			Select dropdownUF = new Select(driver.findElement(By.name("ipe.uf-conselho")));
			dropdownUF.selectByVisibleText("SP");
			Select dropdownConselhoProfissional = new Select(driver.findElement(By.name("ipe.conselho-profissional")));
			dropdownConselhoProfissional.selectByVisibleText("CRP");
			driver.findElement(By.name("ipe.numero-conselho")).sendKeys("350003");
			driver.findElement(By.name("ipe.busca-codigo-cbo")).sendKeys("251510");
			Thread.sleep(3000);
			WebElement autoOptionsCbo = driver.findElement(By.id("ui-id-3"));

			List<WebElement> optionsCbo = autoOptionsCbo.findElements(By.tagName("a"));
			for (WebElement optionToSelectCbo : optionsCbo) {
				if (optionToSelectCbo.getText().equals("251510 - PSICOLOGO CLINICO")) {
					optionToSelectCbo.click();
					break;
				}
			}
			driver.findElement(By.id("incluirIpe")).click();
		}
	
	}

//	@After
//	public void tearDown() {
//		 driver.close();
//	}

}
