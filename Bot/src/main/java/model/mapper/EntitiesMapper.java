package model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.entities.Paciente;
import model.entities.Transtorno;
import model.entities.Usuario;

public class EntitiesMapper {
	
	public EntitiesMapper() {}
	
	public static Paciente instantiatePaciente(ResultSet rs, Transtorno transtorno) throws SQLException {
		Paciente paciente = new Paciente();
		paciente.setCodigo(rs.getString("cod_paciente"));
		paciente.setId(rs.getInt("id"));
		paciente.setNome(rs.getString("nome_paciente"));
		paciente.setTranstorno(transtorno);
		return paciente;
	}

	public static Transtorno instantiateTranstorno(ResultSet rs) throws SQLException {
		Transtorno transtorno = new Transtorno();
		transtorno.setCodigo(rs.getString("cod_transtorno"));
		transtorno.setNome(rs.getString("nome"));
		transtorno.setId(rs.getInt("id_transtorno"));
		return transtorno;
	}
	
	public static Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		
		Usuario usuario = new Usuario();
		usuario.setCodigoCbo(rs.getString("cod_cbo"));
		usuario.setCodigoProcedimento(rs.getString("cod_procedimento"));
		usuario.setCodigoReferenciado(rs.getString("cod_referenciado"));
		usuario.setId(rs.getInt("id"));
		usuario.setNomeSolicitante(rs.getString("nome_solicitante"));
		usuario.setNumeroConselho(rs.getString("numero_conselho"));
		usuario.setSenha(rs.getString("senha"));
		usuario.setUsuario(rs.getString("usuario"));
		usuario.setValorConsulta(rs.getString("valor_consulta"));
		usuario.setDataInsercao(rs.getDate("data_insercao"));
		
		return usuario;
	}
}
