package model.entities;

import java.io.Serializable;

public class Transtorno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nome;
	private Integer id;
	
	public Transtorno() {
	}

	public Transtorno(String codigo, String nome, Integer id) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.setId(id);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transtorno other = (Transtorno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transtorno [codigo=" + codigo + ", nome=" + nome + ", id=" + id + "]";
	}
}
