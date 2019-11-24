package model.entities;

import java.util.Date;

public class Usuario {
	
	private String codigoReferenciado;
	
	private String usuario;
	
	private String senha;
	
	private String nomeSolicitante;
	
	private String numeroConselho;
	
	private String codigoCbo;
	
	private String codigoProcedimento;
	
	private String valorConsulta;
	
	private Integer id;
	
	private Date dataInsercao;

	public Usuario() {
	}
	
	public Usuario(Integer id, String codigoReferenciado, String usuario, String senha, String nomeSolicitante,
			String numeroConselho, String codigoCbo, String codigoProcedimento, String valorConsulta, Date dataInsercao) {
		this.codigoReferenciado = codigoReferenciado;
		this.usuario = usuario;
		this.senha = senha;
		this.nomeSolicitante = nomeSolicitante;
		this.numeroConselho = numeroConselho;
		this.codigoCbo = codigoCbo;
		this.codigoProcedimento = codigoProcedimento;
		this.valorConsulta = valorConsulta;
		this.id = id;
		this.dataInsercao= dataInsercao;
	}

	public String getCodigoReferenciado() {
		return codigoReferenciado;
	}

	public void setCodigoReferenciado(String codigoReferenciado) {
		this.codigoReferenciado = codigoReferenciado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public String getNumeroConselho() {
		return numeroConselho;
	}

	public void setNumeroConselho(String numeroConselho) {
		this.numeroConselho = numeroConselho;
	}

	public String getCodigoCbo() {
		return codigoCbo;
	}

	public void setCodigoCbo(String codigoCbo) {
		this.codigoCbo = codigoCbo;
	}

	public String getCodigoProcedimento() {
		return codigoProcedimento;
	}

	public void setCodigoProcedimento(String codigoProcedimento) {
		this.codigoProcedimento = codigoProcedimento;
	}

	public String getValorConsulta() {
		return valorConsulta;
	}

	public void setValorConsulta(String valorConsulta) {
		this.valorConsulta = valorConsulta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [codigoReferenciado=" + codigoReferenciado + ", usuario=" + usuario + ", senha=" + senha
				+ ", nomeSolicitante=" + nomeSolicitante + ", numeroConselho=" + numeroConselho + ", codigoCbo="
				+ codigoCbo + ", codigoProcedimento=" + codigoProcedimento + ", valorConsulta=" + valorConsulta
				+ ", id=" + id + ", dataInsercao=" + dataInsercao + "]";
	}
}
