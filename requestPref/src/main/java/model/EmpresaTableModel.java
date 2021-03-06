package model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import entities.Empresa;

public class EmpresaTableModel extends DefaultTableModel {
	
	private ArrayList<Empresa> internalList;
	private String[] header = new String[] { "Razão", "CNPJ" };

	public EmpresaTableModel(ArrayList<Empresa> newList) {
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
		if (column == 0) {
			return empresa.getRazao();
		} else {
			return empresa.getCnpj();
		}
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

