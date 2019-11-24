package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.TranstornoDAO;
import model.entities.Transtorno;
import model.mapper.EntitiesMapper;

public class TranstornoDAOImpl implements TranstornoDAO {

	private Connection conn;

	public TranstornoDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Transtorno transtorno) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into transtornos " + "(cod_transtorno, nome) " + "values " + "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, transtorno.getCodigo());
			st.setString(2, transtorno.getNome());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					transtorno.setId(id);
				}

				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi afeteada!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Transtorno transtorno) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update transtornos "
					+ "set cod_transtorno = ?, nome = ? "
					+ "where id_transtorno = ? ");
			
			st.setString(1, transtorno.getCodigo());
			st.setString(2, transtorno .getNome());
			st.setInt(3, transtorno.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("delete from transtornos where id_transtorno = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Transtorno inexistente");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Transtorno findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from transtornos " + "where id_transtorno = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Transtorno transtorno = EntitiesMapper.instantiateTranstorno(rs);

				return transtorno;
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
	public List<Transtorno> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from transtornos order by id_transtorno");

			rs = st.executeQuery();

			List<Transtorno> list = new ArrayList<>();

			while (rs.next()) {

				Transtorno transtorno = EntitiesMapper.instantiateTranstorno(rs);
				list.add(transtorno);
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
