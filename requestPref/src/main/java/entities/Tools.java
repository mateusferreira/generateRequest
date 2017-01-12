package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "tools")

public class Tools {
	private static final long serialVersionUID = -4843755771072252578L;
	
	@Id
	private int id;
	
	@Column(nullable = false, length=200)
	private String pathFile;
	
	@Column(length=20)
	private String pronome;
	
	@Column(nullable = false, length=40)
	private String nome;
	
	@Column(length=20)
	private String tratamento;
	
	@Column(length=40)
	private String cargo;
	
	@Column(length=50)
	private String cidadeEstado;
	
	public Tools(){
		
	}

	public Tools(int id, String pathFile, String pronome, String nome, String tratamento, String cargo, String cidadeEstado) {
		
		this.id = id;
		this.pathFile = pathFile;
		this.pronome = pronome;
		this.nome = nome;
		this.tratamento = tratamento;
		this.cargo = cargo;
		this.cidadeEstado = cidadeEstado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getPronome() {
		return pronome;
	}

	public void setPronome(String pronome) {
		this.pronome = pronome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTratamento() {
		return tratamento;
	}

	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCidadeEstado() {
		return cidadeEstado;
	}

	public void setCidadeEstado(String cidadeEstado) {
		this.cidadeEstado = cidadeEstado;
	}
	
	
	
	

}
