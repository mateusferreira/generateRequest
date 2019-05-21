package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import entities.Tools;

public class DialogSelectOptionCopyPast {
	
	private String valor;
	private JButton ok = new JButton("OK");
	private JCheckBox checkAtividade = new JCheckBox("Atividade");
	private JCheckBox checkBombeiro = new JCheckBox("Bombeiro");
	private JCheckBox checkAgua = new JCheckBox("Água");
	private JCheckBox checkPrestador = new JCheckBox("Prestador");

	public DialogSelectOptionCopyPast(){
		
	}
	
	private String alteraBitString(String corrente, int position, boolean estado){
		

		String inicial, fim, questao;
		inicial = "";
		fim = "";

		if(estado == true)questao = "1";
		else questao = "0";

		inicial = corrente.substring(0, position);
		fim = corrente.substring(position + 1, corrente.length());

		return inicial + questao + fim;
	}
	
	private String testaCheckBox(String str){
		
		//Atividade:
		if(checkAtividade.isSelected()==true)
			str = alteraBitString(str, 0, true);
		else
			str = alteraBitString(str,0,false);
		
		//Bombeiro
		if(checkBombeiro.isSelected()==true)
			str = alteraBitString(str, 1, true);
		else
			str = alteraBitString(str,1,false);
		
		//Agua
		if(checkAgua.isSelected()==true)
			str = alteraBitString(str, 2, true);
		else
			str = alteraBitString(str,2,false);
		
		//Prestador
		if(checkPrestador.isSelected()==true)
			str = alteraBitString(str, 3, true);
		else
			str = alteraBitString(str,3,false);
		
		System.out.println("TESTA:::: "+str);
		return str;
	}
	public Tools display(final Tools tools) {
        
		//SETAR AS CONFIGURAÇÕES ATUAIS
		 JPanel painel = new JPanel();
		 painel.setLayout(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.gridx = 0;
		 c.gridy = 0;
		 painel.add(checkAtividade,c);
		 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.gridx = 0;
		 c.gridy = 1;
		 painel.add(checkBombeiro,c);
		 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.gridx = 1;
		 c.gridy = 0;
		 painel.add(checkAgua,c);
		 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.gridx = 1;
		 c.gridy = 1;
		 painel.add(checkPrestador,c);
		String temp;
		valor = tools.getVar();
	
		
		if(valor.substring(0, 1).equals("1"))//Atividade - Posição 0
			checkAtividade.setSelected(true);
		else
			checkAtividade.setSelected(false);
		
		if(valor.substring(1, 2).equals("1"))//Bombeiro - Posição 1
			checkBombeiro.setSelected(true);
		else
			checkBombeiro.setSelected(false);
		
		if(valor.substring(2, 3).equals("1"))//Água - Posição 2
			checkAgua.setSelected(true);
		else
			checkAgua.setSelected(false);
		
		if(valor.substring(3, 4).equals("1"))//Prestador - Posição 3
			checkPrestador.setSelected(true);
		else
			checkPrestador.setSelected(false);
		
		
		 int result = JOptionPane.showConfirmDialog(null, painel, "Configuração Copiar/Colar",
		            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		 if (result == JOptionPane.OK_OPTION) {
			 valor = testaCheckBox(valor);
			 tools.setVar(valor);
			 System.out.println("OK: "+valor);
			 
		 }else {
			 System.out.println("Cancelled");
		 }
        return tools;
		
		

    }

}
