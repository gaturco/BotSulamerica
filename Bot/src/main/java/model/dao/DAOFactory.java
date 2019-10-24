package model.dao;

import model.dao.impl.PacienteDAOImpl;

public class DAOFactory {

	public static PacienteDAO createPacienteDAO() {
		return new PacienteDAOImpl();
	}
}
