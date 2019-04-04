package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import entities.Empresa;
import view.RelationListEmpresaTableView;

public class RelationTableModel extends DefaultTableModel {
	
	private ArrayList<Empresa> internalList;
	private RelationListEmpresaTableView metodo;
	private String[] header = new String[] { "Insc. Mun.", "Razão", "Nome Fantasia", "CNPJ", "Bombeiro", "água", "CADASTUR", "COPAM","IMPOSTO", "LENHA","STATUS", "PRESTADOR?","OBSERVAÇÃO"};

	public RelationTableModel(ArrayList<Empresa> newList) {
		this.internalList = newList;
	}

	
	public int getRowCount() {
		if (internalList == null) {
			return 0;
		}
		return internalList.size(); // quantidade de objetos/linhas na tabela
	}


	public int getColumnCount() {
		return header.length;
	}


	public String getColumnName(int column) {
		return header[column];
	}
	
	public boolean verificaData(String texto){
		char temp;
		if(texto.equals(""))
			return false;
		else{
			temp = texto.charAt(0);
			if(temp < 48 || temp > 57)// O código ANSI para o nº 0 é 48, 1 é 49 e assim por diante até o nº 9 - 57.
				return false; // se não o caracter não for um número não me interessa tratar, por isso retorna falso. Se o primeiro for número o restante também é. Não vou tratar os outros.
			
			
			//***TRABALHANDO COM DATAS:
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			String t = sdf.format(today);
			Date hoje = null;
			Date sistema = null;
			try {
				hoje = sdf.parse(t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String temporario = texto.substring(6, 10);
			t = temporario + "-";
			temporario = texto.substring(3, 5);
			t = t+ temporario + "-";
			t = t + texto.substring(0,2);
			
			try {
				sistema = sdf.parse(t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(hoje.compareTo(sistema) > 0)// Se hoje for depois de data já passada. Ou seja se VENCEU....
				return true;
			
			else if(hoje.compareTo(sistema) < 0)//Se hoje for antes de data futura. Ou seja ainda está no Prazo.
				return false;
			
			else if(hoje.compareTo(sistema) == 0)//Vence hoje. Mas hoje não é vencido ainda. Então tá OK.
				return false;
			
			//System.out.println("Data: " +t);//imprime o código ANSI do caracter.
		}
		
		return true;
	}


	public Object getValueAt(int row, int column) {
		Empresa empresa = internalList.get(row); // objeto student da linha pedida
		
		if(column == 0)return empresa.getInscMunicipal();
		if(column == 1)return empresa.getRazao(); 
		if(column == 2)return empresa.getFantasia();
		if(column == 3)return empresa.getCnpj();
		if(column == 4)return empresa.getBombeiro();
		if(column == 5)return empresa.getAgua();
		if(column == 6)return empresa.getCadastur();
		if(column == 7)return empresa.getCopam();
		if(column == 8)return empresa.getImposto();
		if(column == 9)return empresa.getLenha();
		if(column == 10)return empresa.getStatus();
		if(column == 11)return empresa.getOnlyservices();
		else return empresa.getNotas();
		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		//Exercício: deixar editável apenas as células da coluna email.
		return false;
	}
	
	public Empresa getPessoaAt(int row) {
		return internalList.get(row);
	}
	
	public ArrayList<Empresa> getEmpresaByAtribute (ArrayList<Empresa> p,String atrib, int option){
		//a linha da columa a ser pesquisada não pode estar vazio pois dá pau. (experimentar inserir null pra ver.
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		//System.out.println("CHEGA: "+atrib);
		int i = 0;
		for(; i< p.size(); i++){
			
			switch (option){
			case 0://Inscrição - Pode ser vazio.
				if(p.get(i).getInscMunicipal() == null)break;
				else if(p.get(i).getInscMunicipal().contains(atrib)) novaLista.add(p.get(i));
				break;
			
			case 1://Razão Não tem vazio.
				if(p.get(i).getRazao().contains(atrib)) novaLista.add(p.get(i));
				break;
				
			case 2://nome Fantasia - Pode se vazio
				if(p.get(i).getFantasia() == null)break;
				else if(p.get(i).getFantasia().contains(atrib)) novaLista.add(p.get(i));
				break;
				
			case 3://CNPJ Não tem vazio.
				if(p.get(i).getCnpj().contains(atrib)) novaLista.add(p.get(i));
				break;
			}
			
		}
		
		return novaLista;
	}
	
	public ArrayList<Empresa> getEmpresaPendente (ArrayList<Empresa> p){ // EMPRESA PENDENTE

		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		int i = 0;
		for(; i< p.size(); i++){
			
			System.out.println("TESTE: "+verificaData(p.get(i).getBombeiro()));
					
			if(p.get(i).getAgua() == null || p.get(i).getBombeiro() == null || p.get(i).getCadastur() == null || p.get(i).getCopam() == null || 
					p.get(i).getLenha() == null || p.get(i).getStatus().equals("CRIANDO") || p.get(i).getStatus().equals("BAIXADO")) 
				continue;
			
			else if(verificaData(p.get(i).getBombeiro()) == true || verificaData(p.get(i).getAgua()) == true 
					||verificaData(p.get(i).getCadastur())== true)
				novaLista.add(p.get(i));
			
			else if(p.get(i).getAgua().equals("FALTA DOC") || p.get(i).getAgua().equals("VENCIDO") || p.get(i).getBombeiro().equals("VENCIDO")|| p.get(i).getBombeiro().equals("FALTA DOC") || p.get(i).getBombeiro().equals("PROTOCOLO") || p.get(i).getCadastur().equals("FALTA DOC")
					|| p.get(i).getCadastur().equals("VENCIDO")|| p.get(i).getCopam().equals("FALTA DOC") || p.get(i).getCopam().equals("VENCIDO") || p.get(i).getLenha().equals("FALTA DOC"))
				novaLista.add(p.get(i));
			
		}
		
		return novaLista;
	}
	
	public ArrayList<Empresa> getEmpresaObserva (ArrayList<Empresa> p){ // EMPRESA OBSERVAÇÃO

		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		int i = 0;
		for(; i< p.size(); i++){
					
			if(p.get(i).getNotas() == null || p.get(i).getNotas().equals("")) continue;
			
			else novaLista.add(p.get(i));
			
		}
		
		return novaLista;
	}
	
	public ArrayList<Empresa> getEmpresaRegular (ArrayList<Empresa> p){// EMPRESAS REGULARES
		
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		int i = 0;
		for(; i< p.size(); i++){
					
			if(p.get(i).getAgua() == null || p.get(i).getBombeiro() == null || p.get(i).getCadastur() == null || p.get(i).getCopam() == null || 
					p.get(i).getLenha() == null || p.get(i).getStatus().equals("CRIANDO") || p.get(i).getStatus().equals("BAIXADO")) 
				continue;
			
			else if(p.get(i).getInscMunicipal() == null || p.get(i).getInscMunicipal().equals("ISENTO"))
				continue;
			
			else if(
					/*AGUA*/
					p.get(i).getAgua().equals("FALTA DOC") || p.get(i).getAgua().equals("VENCIDO") || 
					/*BOMBEIRO*/
					p.get(i).getBombeiro().equals("VENCIDO")|| p.get(i).getBombeiro().equals("FALTA DOC") || p.get(i).getBombeiro().equals("PROTOCOLO") || 
					/*CADASTUR*/
					p.get(i).getCadastur().equals("FALTA DOC") || p.get(i).getCadastur().equals("VENCIDO")|| 
					/*COPAM*/
					p.get(i).getCopam().equals("FALTA DOC") || p.get(i).getCopam().equals("VENCIDO") || 
					/*LENHA*/
					p.get(i).getLenha().equals("FALTA DOC")
					)
				continue;
			
			else if(verificaData(p.get(i).getBombeiro()) == true || verificaData(p.get(i).getAgua()) == true 
					||verificaData(p.get(i).getCadastur())== true)
				continue;
			
			else
				novaLista.add(p.get(i));
				
			
		}
		
		return novaLista;
	}
	
	public ArrayList<Empresa> getEmpresasAtivas (ArrayList<Empresa> p){// SOMENTE EMPRESAS ATIVAS. 
		//NÃO QUERO RETORNAR BAIXADAS, OU CRIANDO.
			
			ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
			
			int i = 0;
			for(; i< p.size(); i++){
				
				if(p.get(i).getInscMunicipal() == null || p.get(i).getInscMunicipal().equals("ISENTO"))
					continue;
				
					
				else if(p.get(i).getStatus() == null || p.get(i).getStatus().equals("CRIANDO") || p.get(i).getStatus().equals("BAIXADO")) 
					continue;
				
				else
					novaLista.add(p.get(i));
			}
			return novaLista;
	}
}


