package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					"select pacientes.*, transtornos.transtorno " + "from pacientes inner join transtornos "
							+ "on pacientes.cod_transtorno = transtornos.cod_transtorno " + "where pacientes.id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Transtorno transtorno = instantiateTranstorno(rs);

				Paciente paciente = instantiatePaciente(rs, transtorno);

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

	private Paciente instantiatePaciente(ResultSet rs, Transtorno transtorno) throws SQLException {
		Paciente paciente = new Paciente();
		paciente.setCodigo(rs.getString("cod_paciente"));
		paciente.setId(rs.getInt("id"));
		paciente.setNome(rs.getString("nome_paciente"));
		paciente.setTranstorno(transtorno);
		return paciente;
	}

	private Transtorno instantiateTranstorno(ResultSet rs) throws SQLException {
		Transtorno transtorno = new Transtorno();
		transtorno.setCodigo(rs.getString("cod_transtorno"));
		transtorno.setNome(rs.getString("transtorno"));
		return transtorno;
	}

	@Override
	public List<Paciente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"select pacientes.*, transtornos.transtorno " + "from pacientes inner join transtornos "
							+ "on pacientes.cod_transtorno = transtornos.cod_transtorno " + "order by nome_paciente");

			rs = st.executeQuery();
			
			List<Paciente> list = new ArrayList<>();
			Map<String, Transtorno> map = new HashMap<>();

			while (rs.next()) {
				Transtorno transtorno = map.get(rs.getString("cod_transtorno"));
				
				if(transtorno == null) {
					transtorno = instantiateTranstorno(rs);
					map.put(rs.getString("cod_transtorno"), transtorno);
				}
				
				Paciente paciente = instantiatePaciente(rs, transtorno);
				list.add(paciente);
			}

			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
