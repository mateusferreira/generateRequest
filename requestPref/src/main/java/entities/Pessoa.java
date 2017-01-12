package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "pessoa")
public class Pessoa implements Serializable{
	private static final long serialVersionUID = -4843755771072252578L;
	
	@Id
	private String cpf;
	
	@Column(nullable = false, length=40)
	private String nome;
	
	@Column
	private char sexo;
	
	@Column(length = 15)
	private String nacionalidade;
	
	@Column(length = 14)
	private String estadoCivil;
	

	@Column(length = 20)
	private String rg;
	
	@Column(nullable = false, length = 50)
	private String endereco;
	
	@Column (length = 8)
	private String numero;
	
	@Column
	private String bairro;
	
	@Column(nullable = false, length = 20)
	private String cidade;
	
	@Column(nullable = false, length = 2)
	private String estado;
	
	public Pessoa(){
		
	}

	public Pessoa(String cpf, String nome, char sexo, String nacionalidade, String rg, String endereco, String numero, String bairro, String cidade, String estado) {
		//super();
		this.cpf = cpf;
		this.nome = nome;
		this.sexo = sexo;
		this.nacionalidade = nacionalidade;
		this.sexo = sexo;
		this.rg = rg;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
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
