package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PacienteDAO;
import model.entities.Paciente;
import model.entities.Transtorno;

public class PacienteDAOImpl implements PacienteDAO {
	
	private Connection conn;
	
	public PacienteDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Paciente paciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Paciente paciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Paciente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"select pacientes.*, transtornos.transtorno "
					+ "from pacientes inner join transtornos "
					+ "on pacientes.cod_transtorno = transtornos.cod_transtorno "
					+ "where pacientes.id = ?"
					);
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Transtorno transtorno = new Transtorno();
				transtorno.setCodigo(rs.getString("cod_transtorno"));
				transtorno.setNome(rs.getString("transtorno"));
				
				Paciente paciente = new Paciente();
				paciente.setCodigo(rs.getString("cod_paciente"));
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome_paciente"));
				paciente.setTranstorno(transtorno);
				
				return paciente;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Paciente> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
