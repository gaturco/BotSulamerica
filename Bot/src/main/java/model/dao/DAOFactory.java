package model.dao;

import db.DB;
import model.dao.impl.PacienteDAOImpl;

public class DAOFactory {

	public static PacienteDAO createPacienteDAO() {
		return new PacienteDAOImpl(DB.getConnection());
	}
}
