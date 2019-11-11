package model.dao;

import db.DB;
import model.dao.impl.PacienteDAOImpl;
import model.dao.impl.UsuarioDAOImpl;

public class DAOFactory {

	public static PacienteDAO createPacienteDAO() {
		return new PacienteDAOImpl(DB.getConnection());
	}
	
	public static UsuarioDAO createUsuarioDAO() {
		return new UsuarioDAOImpl(DB.getConnection());
	}
}
