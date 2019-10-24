package model.dao;

import java.util.List;

import model.entities.Paciente;

public interface PacienteDAO {

	void insert(Paciente paciente);
	void update(Paciente paciente);
	void deleteById(Integer id);
	Paciente findById(Integer id);
	List<Paciente> findAll();
}
