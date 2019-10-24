package model.entities;

import java.io.Serializable;

public class Paciente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String codigo;
	private String nome;
	private Transtorno transtorno;
	
	public Paciente() {
	}

	public Paciente(Integer id, String codigo, String nome, Transtorno transtorno) {
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.transtorno = transtorno;
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

	public Transtorno getTranstorno() {
		return transtorno;
	}

	public void setTranstorno(Transtorno transtorno) {
		this.transtorno = transtorno;
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
		Paciente other = (Paciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", codigo=" + codigo + ", nome=" + nome + ", transtorno=" + transtorno + "]";
	}
}
