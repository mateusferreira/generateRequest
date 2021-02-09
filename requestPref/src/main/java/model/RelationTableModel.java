package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import control.FerramentasControle;
import entities.Empresa;
import view.RelationListEmpresaTableView;

public class RelationTableModel extends DefaultTableModel {
	
	private FerramentasControle ferramenta = new FerramentasControle();
	private ArrayList<Empresa> internalList;
	private RelationListEmpresaTableView metodo;
	private String[] header = new String[] { "Insc. Mun.", "Razão", "Nome Fantasia", "CNPJ", "Bombeiro", "água", "CADASTUR", "COPAM", "LENHA","STATUS", "PRESTADOR?","CLASSIFICAÇÃO","OBSERVAÇÃO"};

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
	

	public Object getValueAt(int row, int column) {
		Empresa empresa = internalList.get(row); // INDICE DA COLUNA:
		//0-Insc |1-Razão |2-Fantasia |3-CNPJ |4-Bombeiro |5-Agua |6-Cadastur |7-COPAM |8-Lenha |9-Status |10 -Somente Serviços |11-Classificação
		//ATENÇÃO: SE ALTERAR A SEQUENCIA AQUI, TERÁ QUE ALTERAR A SEQUENCIA NAS CORES DE AVISOS LÁ EM VIEW->RelationListEmpresaTableView - Metódo getPainelTable()
		
		if(column == 0)return empresa.getInscMunicipal();
		if(column == 1)return empresa.getRazao(); 
		if(column == 2)return empresa.getFantasia();
		if(column == 3)return empresa.getCnpj();
		if(column == 4)return empresa.getBombeiro();
		if(column == 5)return empresa.getAgua();
		if(column == 6)return empresa.getCadastur();
		if(column == 7)return empresa.getCopam();
		if(column == 8)return empresa.getLenha();
		if(column == 9)return empresa.getStatus();
		if(column == 10)return empresa.getOnlyservices();
		if(column == 11)return empresa.getClassifica();
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
			
			if(ferramenta.testaEmpresaPendente(p.get(i)) == true) // TRUE = entra na lista....
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
					
					if(ferramenta.testaEmpresaPendente(p.get(i)) == false){ // TRUE = entra na lista....
						if(ferramenta.getCodStatusEmpresa() == 4)
							novaLista.add(p.get(i));
					}
					
				}
				
				return novaLista;
			}
			
	public ArrayList<Empresa> getEmpresasBaixadas (ArrayList<Empresa> p){// SOMENTE EMPRESAS BAIXADAS. 
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		int i = 0;
		for(; i< p.size(); i++){
			
			if(ferramenta.testaEmpresaPendente(p.get(i)) == false){ // TRUE = entra na lista....
				if(ferramenta.getCodStatusEmpresa() == 3)// BAIXADAS
					novaLista.add(p.get(i));
			}
			
		}
		
		return novaLista;
	}
	
	public ArrayList<Empresa> getEmpresaCriando (ArrayList<Empresa> p){// SOMENTE EMPRESAS CRIANDO. 
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		int i = 0;
		for(; i< p.size(); i++){
			
			if(ferramenta.testaEmpresaPendente(p.get(i)) == false){ // TRUE = entra na lista....
				if(ferramenta.getCodStatusEmpresa() == 1)// CRIANDO
					novaLista.add(p.get(i));
			}
			
		}
		
		return novaLista;
	}
	
	
	public ArrayList<Empresa> getEmpresaByClassifica (ArrayList<Empresa> p, String nomeClassifica){
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		
		
		int i = 0;
		for(; i< p.size(); i++){
			
			if(p.get(i).getClassifica().equals(nomeClassifica))
				novaLista.add(p.get(i));
			
		}
		return novaLista;
	}
}


