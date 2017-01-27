package model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import entities.Pessoa;

public class PessoaTableModel extends DefaultTableModel {
	
	private ArrayList<Pessoa> internalList;
	private String[] header = new String[] { "Nome", "CPF" };

	public PessoaTableModel(ArrayList<Pessoa> newList) {
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
		Pessoa pessoa = internalList.get(row); // objeto student da linha pedida
		if (column == 0) {
			return pessoa.getNome();
		} else {
			return pessoa.getCpf();
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		//Exercício: deixar editável apenas as células da coluna email.
		return false;
	}
	
	public Pessoa getPessoaAt(int row) {
		return internalList.get(row);
	}
	
	public ArrayList<Pessoa> getPessaByAtribute (ArrayList<Pessoa> p,String atrib){
		ArrayList<Pessoa> novaLista = new ArrayList<Pessoa>();
		//System.out.println("CHEGA: "+atrib);
		int i = 0;
		for(; i< p.size(); i++){
			if(p.get(i).getNome().contains(atrib)){
				novaLista.add(p.get(i));
			}
		}
		
		return novaLista;
	}

}
