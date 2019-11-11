package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.SulamericaBot;
import model.dao.DAOFactory;
import model.dao.PacienteDAO;
import model.entities.Paciente;
import model.entities.Transtorno;
import model.entities.Usuario;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public static void main(String[] args) throws IOException {
		
		PacienteDAO pacienteDAO = DAOFactory.createPacienteDAO();
		
		System.out.println("==== TEST 1: paciente findById ====");
		Paciente paciente = pacienteDAO.findById(2);
		System.out.println(paciente);
		
		System.out.println("\n==== TEST 2: paciente findAll ====");
		List<Paciente> list = pacienteDAO.findAll();
		for (Paciente obj : list) {
			System.out.println(obj);
		}
		
//		System.out.println("\n==== TEST 3: paciente insert ====");
//		Paciente newPaciente = new Paciente(null, "00000000000000000000", "teste", new Transtorno("F32", "DEPRESSAO", null));
//		pacienteDAO.insert(newPaciente);
//		System.out.println("Inserido! New id = " + newPaciente.getId()); 
		
//		System.out.println("\n==== TEST 4: paciente update ====");
//		paciente = pacienteDAO.findById(4);
//		paciente.setNome("teste2");
//		pacienteDAO.update(paciente);
//		System.out.println("Update realizado com sucesso!"); 
		
		System.out.println("\n==== TEST 5: paciente delete ====");
		pacienteDAO.deleteById(5);
		System.out.println("Paciente deletado com sucesso!"); 
		
		Usuario usuario = new Usuario();
		usuario.setCodigoCbo("251510");
		usuario.setCodigoProcedimento("50000462");
		usuario.setCodigoReferenciado("00035000SPP0");
		usuario.setNomeSolicitante("JANETE ESPOSITO");
		usuario.setNumeroConselho("350003");
		usuario.setSenha("jan2705#");
		usuario.setUsuario("master");
		usuario.setValorConsulta("54,31");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View(usuario);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View(Usuario usuario) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnIniciarRob = new JButton("Iniciar Robô");
		btnIniciarRob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SulamericaBot.browser(usuario);

					System.out.println("Test finished successfully.");
//					System.exit(0);
				} catch (Exception er) {
					System.out.println("Test failed." + er);
//					System.exit(0);

				}
			}
		});
		btnIniciarRob.setBounds(319, 215, 105, 23);
		contentPane.add(btnIniciarRob);

		JButton btnAlterarCdigoDo = new JButton("Alterar Código do Referenciado");
		btnAlterarCdigoDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoReferenciado = JOptionPane
						.showInputDialog(null,
								"Digite o novo Código do Referenciado: (o Código atual é "
										+ usuario.getCodigoReferenciado() + ")",
								"Sulamerica", JOptionPane.PLAIN_MESSAGE);
				usuario.setCodigoReferenciado(codigoReferenciado);
			}
		});
		btnAlterarCdigoDo.setBounds(10, 11, 193, 23);
		contentPane.add(btnAlterarCdigoDo);

		JButton btnNewButton = new JButton("Alterar Usuário");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioNovo = JOptionPane.showInputDialog(null,
						"Digite o novo Usuário: (o Usuário atual é " + usuario.getUsuario() + ")", "Sulamerica",
						JOptionPane.PLAIN_MESSAGE);
				usuario.setUsuario(usuarioNovo);
			}
		});
		btnNewButton.setBounds(10, 45, 193, 23);
		contentPane.add(btnNewButton);

		JButton btnAlterarSenha = new JButton("Alterar Senha");
		btnAlterarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String senhaNova = JOptionPane.showInputDialog(null,
						"Digite o nova Senha: (a Senha atual é " + usuario.getSenha() + ")", "Sulamerica",
						JOptionPane.PLAIN_MESSAGE);
				usuario.setSenha(senhaNova);
			}
		});
		btnAlterarSenha.setBounds(10, 79, 193, 23);
		contentPane.add(btnAlterarSenha);

		JButton btnAlterarNomeDo = new JButton("Alterar Nome do Solicitante");
		btnAlterarNomeDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String solicitanteNovo = JOptionPane.showInputDialog(null,
						"Digite o novo Nome do Solicitante: (o Solicitante atual é " + usuario.getNomeSolicitante()
								+ ")",
						"Sulamerica", JOptionPane.PLAIN_MESSAGE);
				usuario.setNomeSolicitante(solicitanteNovo);
			}
		});
		btnAlterarNomeDo.setBounds(10, 113, 193, 23);
		contentPane.add(btnAlterarNomeDo);

		JButton btnAlterarNmeroDo = new JButton("Alterar Número do Conselho");
		btnAlterarNmeroDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String conselhoNovo = JOptionPane.showInputDialog(null,
						"Digite o novo Número do Conselho: (o Número atual é " + usuario.getNumeroConselho() + ")",
						"Sulamerica", JOptionPane.PLAIN_MESSAGE);
				usuario.setNumeroConselho(conselhoNovo);
			}
		});
		btnAlterarNmeroDo.setBounds(10, 147, 193, 23);
		contentPane.add(btnAlterarNmeroDo);

		JButton btnAlterarCdigoCbo = new JButton("Alterar Código CBO");
		btnAlterarCdigoCbo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cboNovo = JOptionPane.showInputDialog(null,
						"Digite o novo Código CBO: (o Código atual é " + usuario.getCodigoCbo() + ")", "Sulamerica",
						JOptionPane.PLAIN_MESSAGE);
				usuario.setCodigoCbo(cboNovo);
			}
		});
		btnAlterarCdigoCbo.setBounds(10, 181, 193, 23);
		contentPane.add(btnAlterarCdigoCbo);

		JButton btnAlterarValorDa = new JButton("Alterar Valor da Consulta");
		btnAlterarValorDa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valorNovo = JOptionPane.showInputDialog(null,
						"Digite o novo Valor de Consulta: (o Valor atual é " + usuario.getValorConsulta() + ")",
						"Sulamerica", JOptionPane.PLAIN_MESSAGE);
				usuario.setValorConsulta(valorNovo);
			}
		});
		btnAlterarValorDa.setBounds(10, 215, 193, 23);
		contentPane.add(btnAlterarValorDa);

		JLabel lblBemVindoAo = new JLabel(
				"<html>Bem Vindo ao Gerador Automático de Guias Sul América!<br><br>Para iniciar o  processo clique no botão abaixo</html>");
		lblBemVindoAo.setVerticalAlignment(SwingConstants.TOP);
		lblBemVindoAo.setBounds(282, 14, 142, 106);
		contentPane.add(lblBemVindoAo);
	}
}
