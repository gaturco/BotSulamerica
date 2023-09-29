package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.toedter.calendar.JDateChooser;

import model.Usuario;

public class SulamericaBot {

	static WebDriver driver;

	@Test
	public static void browser(Usuario usuario) throws Exception {

		try {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} catch (SessionNotCreatedException e) {
			JOptionPane.showMessageDialog(null, new MessageWithLink("O Chromedriver está desatualizado!"+
					"<br />Para atualizá-lo: <br />1)Cheque a versão do Chrome no link: <a href=\"https://support.google.com/chrome/answer/95414?hl=pt-BR&co=GENIE.Platform%3DDesktop&sjid=1549563071184763881-SA&oco=2\">chrome://settings/help</a>"+
					"<br />2)Baixe a versão correta no link: <a href=\"https://chromedriver.chromium.org/downloads\">https://chromedriver.chromium.org/downloads</a>"+
					"<br />3)Feche todas as intâncias abertas usando o atalho Ctrl+Shift+Esc"+
					"<br />4)Altere o arquivo na pasta do programa"));
		}

		int time = 10;
		WebDriverWait wait = new WebDriverWait(driver, time);

		// LOGAR
		driver.get("https://saude.sulamericaseguros.com.br/prestador/login/");
		//driver.findElement(By.id("sasBannerManutencao")).click();
		driver.findElement(By.id("code")).sendKeys(usuario.getCodigoReferenciado());
		driver.findElement(By.id("user")).sendKeys(usuario.getUsuario());
		driver.findElement(By.id("senha")).sendKeys(usuario.getSenha());
		driver.findElement(By.id("entrarLogin")).click();

		try {

			// FECHA TELAS INDESEJADAS
			driver.findElement(By.id("btnLayerFecharuserDialog")).click();
			driver.findElement(By.className("img-responsive")).click();
			SulamericaBot.proc(usuario, wait);

		} catch (Exception e) {
			SulamericaBot.proc(usuario, wait);
		}

	}

	private static void proc(Usuario usuario, WebDriverWait wait) throws InterruptedException, ParseException {

		String codigo = null;

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);
		// ENTRA NA PAGINA DE FATURAMENTO
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Serviços Médicos")));
		driver.findElement(By.linkText("Serviços Médicos")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Acessar")));
		driver.findElement(By.linkText("Acessar")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Validar procedimento autorizado")));
		driver.findElement(By.linkText("Validar procedimento autorizado")).click();

		// PRENCHE CODIGO DO PACIENTE
		do {

			codigo = JOptionPane.showInputDialog(null, "Codigo do segurado: ", "Sulamerica", JOptionPane.PLAIN_MESSAGE);

		} while (codigo.length() != 20);
		// codigo = "54588888454738550019";
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

//		String autorizacao = "";
//		int linha = 0;
//		for (int i = 0; i < td.size(); i++) {
//			if (td.get(i).getText().contains("/")) {
//				String[] dataSplit = td.get(i).getText().split("/");
//				linha++;
//				if ("10".equals(dataSplit[1]) && "2020".equals(dataSplit[2])) {
//					autorizacao = td.get(i + 1).getText();
//					System.out.println(tr.get(linha).getText());
//					tr.get(linha).click();
//				}
//			}
//		}
//		System.out.println(autorizacao);

		if (driver.findElement(By.id("selectTipoGuia")).isDisplayed()) {
			Select dropdownGuia = new Select(driver.findElement(By.id("selectTipoGuia")));
			dropdownGuia.selectByVisibleText("SADT");
			driver.findElement(By.id("btn-confirmar-pesquisa")).click();
		}

		System.out.println(
				driver.findElement(By.xpath("//*[@id=\"sas-box-lgpd-info\"]/div/div[2]/button")).isDisplayed());
		if (driver.findElement(By.xpath("//*[@id=\"sas-box-lgpd-info\"]/div/div[2]/button")).isDisplayed()) {
			driver.findElement(By.xpath("//*[@id=\"sas-box-lgpd-info\"]/div/div[2]/button")).click();
		}
		// PREENCHE A TELA DO REFERENCIADO
		driver.findElement(By.id("numero-guia-principal")).sendKeys(autorizacao);
		JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String calendarioTituloAutorizacao = String.join("\n", "Selecione a data de autorização:");
		Object[] paramsAutorizacao = { calendarioTituloAutorizacao, dateChooser };
		
		JOptionPane.showConfirmDialog(null, paramsAutorizacao, String.join(" ", "Data", Integer.toString(1)),
				JOptionPane.PLAIN_MESSAGE);
		
		String dataAutorizacao = sdf.format(((JDateChooser) paramsAutorizacao[1]).getDate());
		driver.findElement(By.name("guia-sadt.data-autorizacao")).sendKeys(dataAutorizacao);
		
		JDateChooser dateChooserValidade = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		String calendarioTituloValidade = String.join("\n", "Selecione a data de validade:");
		Object[] paramsValidade = { calendarioTituloValidade, dateChooserValidade };
		
		JOptionPane.showConfirmDialog(null, paramsValidade, String.join(" ", "Data", Integer.toString(1)),
				JOptionPane.PLAIN_MESSAGE);
		String dataValidade = sdf.format(((JDateChooser) paramsValidade[1]).getDate());
		driver.findElement(By.id("data-validade-senha")).sendKeys(dataValidade);
		
		//driver.findElement(By.id("numero-profissional-operadora")).sendKeys(usuario.getCodigoReferenciado());
		//driver.findElement(By.id("nome-contratado-solicitante")).sendKeys(usuario.getNomeSolicitante());
		String profissionalSolicitante = JOptionPane.showInputDialog(null, "Nome do profissional solicitante: ", "Sulamerica", JOptionPane.PLAIN_MESSAGE);
		driver.findElement(By.name("guia-sadt.profissional-solicitante.nome")).sendKeys(profissionalSolicitante);
		Select dropdownConselho = new Select(driver.findElement(By.id("conselho-profissional")));
		dropdownConselho.selectByVisibleText("CRP");
		Select dropdownUf = new Select(driver.findElement(By.id("uf-conselho-profissional")));
		dropdownUf.selectByVisibleText("SP");
		String numeroConselho = JOptionPane.showInputDialog(null, "Número do conselho: ", "Sulamerica", JOptionPane.PLAIN_MESSAGE);
		driver.findElement(By.id("numero-registro-conselho")).sendKeys(numeroConselho);
		driver.findElement(By.id("cbo")).sendKeys(usuario.getCodigoCbo());
		Thread.sleep(3000);
		WebElement autoOptions = driver.findElement(By.id("ui-id-1"));

		List<WebElement> options = autoOptions.findElements(By.tagName("a"));
		jse.executeScript("scroll(0, 1000)");
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

		String indicacao = JOptionPane.showInputDialog(null, "Transtorno:", "Sulamerica", JOptionPane.PLAIN_MESSAGE);
		// String indicacao = "depressao";
		driver.findElement(By.id("indicacao-clinica")).sendKeys(indicacao.toUpperCase());

		//driver.findElement(By.name("pes.codigo-procedimento")).sendKeys(usuario.getCodigoProcedimento());
		//driver.findElement(By.className("btn-busca-procedimento")).click();

		// String quantidade = "6";
		//driver.findElement(By.name("pes.quantidade-solicitada")).sendKeys(quantidade);
		//driver.findElement(By.name("pes.quantidade-autorizada")).sendKeys(quantidade);
		//driver.findElement(By.id("incluiPes")).click();
		//driver.findElement(By.className("bt-excluir")).click();

		// DADOS ATENDIMENTO
		Select dropdownTipoAtendimento = new Select(driver.findElement(By.id("tipo-atendimento")));
		dropdownTipoAtendimento.selectByVisibleText("Outras Terapias");
		Select dropdownIndicacaoAcidente = new Select(driver.findElement(By.id("indicador-acidente")));
		dropdownIndicacaoAcidente.selectByVisibleText("Não Acidente");
		Select dropdownTipoConsulta = new Select(driver.findElement(By.id("tipo-consulta")));
		dropdownTipoConsulta.selectByVisibleText("Retorno");
		Select dropdownRegimeAtendimento = new Select(driver.findElement(By.id("regime-atendimento")));
		dropdownRegimeAtendimento.selectByVisibleText("Ambulatorial");
		
		driver.findElement(By.id("btnLayerFecharuserDialog")).click();
		String quantidade = JOptionPane.showInputDialog(null, "Quantidade:", "Sulamerica", JOptionPane.PLAIN_MESSAGE);

		// PROCEDIMENTOS E EXAMES REALIZADOS 50000462
		List<String> datas = new ArrayList<String>();
		int qtde = Integer.parseInt(quantidade);
		JDateChooser jd = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		
		Date d = sdf.parse(data);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		String dataMax = sdf.format(c.getTime());
		String calendarioTitulo = String.join("\n", "Selecione uma data entre:", data, dataMax);
		Object[] params = { calendarioTitulo, jd };
		String datasAdd = "";
		for (int j = 0; j < qtde; j++) {
			JOptionPane.showConfirmDialog(null, params, String.join(" ", "Data", Integer.toString(j + 1)),
					JOptionPane.PLAIN_MESSAGE);
			datas.add(sdf.format(((JDateChooser) params[1]).getDate()));
			for (int k = 0; k < datas.size(); k++) {
				datasAdd += datas.get(k) + "\n";
			}
			JOptionPane.showMessageDialog(null, "Datas adicionadas:\n" + datasAdd, "Controle de datas",
					JOptionPane.INFORMATION_MESSAGE);
			datasAdd = "";
			// datas.add("07012019");
			// datas.add("08012019");
			// datas.add("09012019");
			// datas.add("10012019");
			// datas.add("11012019");
			// datas.add("12012019");
		}

		String adiciona;

		for (int i = 0; i < qtde; i++) {
			driver.findElement(By.name("per.data")).sendKeys(datas.get(i));
			driver.findElement(By.xpath("//*[@id=\"formPer\"]/fieldset/div[4]/div[1]/div[1]/input"))
					.sendKeys(usuario.getCodigoProcedimento());

			jse.executeScript("scroll(0, 250)"); // if the element is on bottom.
			WebElement element = driver.findElement(By.xpath("//*[@id=\"formPer\"]/fieldset/div[4]/div[1]/div[2]/a"));
			actions.moveToElement(element).click().perform();
			Thread.sleep(2000);
			driver.findElement(By.name("per.quantidade")).sendKeys("1");
			driver.findElement(By.name("per.valor-unitario")).sendKeys(usuario.getValorConsulta());
			jse.executeScript("scroll(0, 1000)"); // if the element is on bottom.
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("incluirPer")));
			driver.findElement(By.id("incluirPer")).click();
			if (i == 0) {
				Thread.sleep(1500);
				driver.findElement(By.xpath("//*[@id=\"1\"]/td[1]/a")).click();
			}
			adiciona = Integer.toString(i + 2);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='" + adiciona + "']/td[12]/span")));
			jse.executeScript("scroll(0, 1000)"); // if the element is on bottom.
			// driver.findElement(By.xpath("//*[@id='" + adiciona +
			// "']/td[13]/span")).click();
			Thread.sleep(750);
			//*[@id="2"]/td[12]/span
			WebElement mais = driver.findElement(By.xpath("//*[@id='" + adiciona + "']/td[12]/span"));
			actions.moveToElement(mais).click().build().perform();

			Select dropdownGrauPart = new Select(driver.findElement(By.name("ipe.grau-participacao")));
			dropdownGrauPart.selectByVisibleText("Clínico");
			Select dropdownTipoDoc = new Select(driver.findElement(By.name("ipe.tipo-documento")));
			dropdownTipoDoc.selectByVisibleText("Código na Operadora");
			driver.findElement(By.name("ipe.numero-documento")).sendKeys(usuario.getCodigoReferenciado());
			driver.findElement(By.name("ipe.nome-profissional")).sendKeys(profissionalSolicitante);
			Select dropdownUF = new Select(driver.findElement(By.name("ipe.uf-conselho")));
			dropdownUF.selectByVisibleText("SP");
			Select dropdownConselhoProfissional = new Select(driver.findElement(By.name("ipe.conselho-profissional")));
			dropdownConselhoProfissional.selectByVisibleText("CRP");
			driver.findElement(By.name("ipe.numero-conselho")).sendKeys(numeroConselho);
			driver.findElement(By.name("ipe.busca-codigo-cbo")).sendKeys(usuario.getCodigoCbo());
			Thread.sleep(3000);
			WebElement autoOptionsCbo = driver.findElement(By.id("ui-id-4"));
			List<WebElement> optionsCbo = autoOptionsCbo.findElements(By.tagName("a"));
			for (WebElement optionToSelectCbo : optionsCbo) {
				if (optionToSelectCbo.getText().equals("251510 - PSICOLOGO CLINICO")) {
					optionToSelectCbo.click();
					break;
				}
			}
			driver.findElement(By.id("incluirIpe")).click();
		}
		JOptionPane.showMessageDialog(null, "Verifique se está tudo certo no pedido e clique em Confirmar");
		

	}

	@After
	public void tearDown() {
		driver.close();
	}

}