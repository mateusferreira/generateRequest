package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "empresa")

public class Empresa implements Serializable{
	private static final long serialVersionUID = -4843755771072252578L;
	
	@Id
	private String cnpj;
	
	@Column(nullable = false, length=40)
	private String razao;
	
	@Column(length=6)
	private String inscMunicipal;
	
	@Column(length=100)
	private String atividade;
	
	@Column
	private Date dataInicio;
	
	@Column(nullable = false, length = 50)
	private String endereco;
	
	@Column (length = 8)
	private String numero;
	
	@Column (nullable = false, length = 30)
	private String bairro;
	
	@Column(nullable = false, length = 20)
	private String cidade;
	
	@Column(nullable = false, length = 2)
	private String estado;
	
	public Empresa(){
		
	}
	
	public Empresa(String cnpj, String razao, String inscMunicipal,String atividade, Date dataInicio, String endereco, String numero, String bairro, String cidade, String estado){
		this.cnpj = cnpj;
		this.razao = razao;
		this.inscMunicipal = inscMunicipal;
		this.atividade = atividade;
		this.dataInicio = dataInicio;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	
	public Date getDataInicio(){
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio){
		this.dataInicio = dataInicio;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	

}
