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
	
	@Column(length=40)
	private String fantasia;
	
	@Column(length=6)
	private String inscMunicipal;
	
	@Column(length=100)
	private String atividade;
	
	@Column
	private Date dataInicio;
	
	@Column(nullable = false, length = 50)
	private String endereco;
	
	@Column (length = 14)
	private String numero;
	
	@Column (nullable = false, length = 30)
	private String bairro;
	
	@Column(nullable = false, length = 20)
	private String cidade;
	
	@Column(nullable = false, length = 2)
	private String estado;
	
	@Column(length = 10)
	private String bombeiro;
	
	@Column(length = 10)
	private String agua;
	
	@Column(length = 10)
	private String cadastur;
	
	@Column(length = 10)
	private String copam;
	
	@Column(length = 10)
	private String imposto;
	
	@Column(length = 15)
	private String fone;
	
	@Column(length = 50)
	private String email;
	
	@Column(length = 7)
	private String status;
	
	@Column (length = 10)
	private String lenha;
	
	@Column(length = 100)
	private String notas;// Alguma coisa a ser observada na empresa, tipo algum aviso, ou coisa errada.
	
	@Column (length = 1)
	private String onlyservices;// A idéia aqui é marcar se a empresa é apenas prestadora de serviços, se for, não tem alvará de bombeiros, água, etc.
	
	@Column (length = 30)
	private String classifica; //Classificar a empresa por tipo de atividade. Será muito util para gerar relatório para o pessoal do turismo.
	
	public Empresa(){
		
	}
	
	public Empresa(String cnpj, String razao,  String fantasia, String inscMunicipal,String atividade, Date dataInicio, String endereco, String numero, String bairro, String cidade, String estado,
			String bombeiro, String agua, String cadastur, String copam, String imposto, String fone, String email, String status, String lenha, String notas, String onlyservices, String classifica){
		this.cnpj = cnpj;
		this.razao = razao;
		this.fantasia = fantasia;
		this.inscMunicipal = inscMunicipal;
		this.atividade = atividade;
		this.dataInicio = dataInicio;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.bombeiro = bombeiro;
		this.agua = agua;
		this.cadastur = cadastur;
		this.copam = copam;
		this.imposto = imposto;
		this.fone = fone;
		this.email = email;
		this.status = status;
		this.lenha = lenha;
		this.notas = notas;
		this.onlyservices = onlyservices;
		this.classifica = classifica;
	}
	
	public String getClassifica() {
		return classifica;
	}

	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public String getOnlyservices() {
		return onlyservices;
	}

	public void setOnlyservices(String onlyservices) {
		this.onlyservices = onlyservices;
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
	
	public String getFantasia(){
		return fantasia;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}
	
	public void setFantasia(String fantasia){
		this.fantasia = fantasia;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}
	
	public String getBombeiro() {
		return bombeiro;
	}
	
	public String getAgua () {
		return agua;
	}
	
	public String getCadastur () {
		return cadastur;
	}
	
	public String getCopam() {
		return copam;
	}
	
	public String getImposto() {
		return imposto;
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
	

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getLenha() {
		return lenha;
	}

	public void setLenha(String lenha) {
		this.lenha = lenha;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
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
	
	public void setBombeiro(String bombeiro) {
		this.bombeiro = bombeiro;
	}
	
	public void setAgua(String agua) {
		this.agua = agua;
	}
	
	public void setCadastur(String cadastur) {
		this.cadastur = cadastur;
	}
	
	public void setCopam (String copam ) {
		this.copam = copam;
	}
	
	public void setImposto(String imposto ) {
		this.imposto = imposto;
	}
	
	
	

}
