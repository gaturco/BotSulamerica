package model.dao;

import java.util.List;

import model.entities.Transtorno;

public interface TranstornoDAO {
	
	void insert(Transtorno transtorno);
	void update(Transtorno transtorno);
	void deleteById(Integer id);
	Transtorno findById(Integer id);
	List<Transtorno> findAll();
}
