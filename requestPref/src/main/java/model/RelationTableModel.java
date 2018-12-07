package model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import entities.Empresa;

public class RelationTableModel extends DefaultTableModel {
	
	private ArrayList<Empresa> internalList;
	private String[] header = new String[] { "Insc. Mun.", "Razão", "Nome Fantasia", "CNPJ", "Bombeiro", "água", "CADASTUR", "COPAM","IMPOSTO", "LENHA","STATUS", "OBSERVAÇÃO"};

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
	
	public ArrayList<Empresa> getEmpresaByAtribute (ArrayList<Empresa> p,String atrib){
		ArrayList<Empresa> novaLista = new ArrayList<Empresa>();
		//System.out.println("CHEGA: "+atrib);
		int i = 0;
		for(; i< p.size(); i++){
			if(p.get(i).getRazao().contains(atrib)){
				novaLista.add(p.get(i));
			}
		}
		
		return novaLista;
	}

}


