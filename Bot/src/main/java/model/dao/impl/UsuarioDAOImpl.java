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
			st = conn.prepareStatement("insert into usuarios " + "(cod_referenciado, usuario, senha, nome_solicitante, numero_conselho, cod_cbo, cod_procedimento, valor_consulta, data_insercao) "
					+ "values " + "(?, ?, ?, ?, ?, ?, ?, ?, now())", Statement.RETURN_GENERATED_KEYS);

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
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"update usuarios "
					+ "set cod_referenciado = ?, usuario = ?, senha = ?, nome_solicitante = ?, numero_conselho = ?, cod_cbo = ?, cod_procedimento = ?, valor_consulta = ?, data_insercao = now() "
					+ "where id = ? ");
			
			st.setString(1, usuario.getCodigoReferenciado());
			st.setString(2, usuario .getUsuario());
			st.setString(3, usuario.getSenha());
			st.setString(4, usuario.getNomeSolicitante());
			st.setString(5, usuario.getNumeroConselho());
			st.setString(6, usuario.getCodigoCbo());
			st.setString(7, usuario.getCodigoProcedimento());
			st.setString(8, usuario.getValorConsulta());
			st.setInt(9, usuario.getId());
			
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
			st = conn.prepareStatement("delete from usuarios where id = ?");
			
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("Usuario inexistente");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Usuario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"select * from usuarios "
							+ "where id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Usuario usuario = instantiateUsuario(rs);
				
				return usuario;
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
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from usuarios order by id");

			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {
				
				Usuario usuario = instantiateUsuario(rs);
				list.add(usuario);
			}

			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		
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
