package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UsuarioDAO;
import model.entities.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	private Connection conn;

	public UsuarioDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Usuario usuario) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into usuarios " + "(cod_referenciado, usuario, senha, nome_solicitante, numero_conselho, cod_cbo, cod_procedimento, valor_consulta) "
					+ "values " + "(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, usuario.getCodigoReferenciado());
			st.setString(2, usuario.getUsuario());
			st.setString(3, usuario.getSenha());
			st.setString(4, usuario.getNomeSolicitante());
			st.setString(5, usuario.getNumeroConselho());
			st.setString(6, usuario.getCodigoCbo());
			st.setString(7, usuario.getCodigoProcedimento());
			st.setString(8, usuario.getValorConsulta());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					usuario.setId(id);
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
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
