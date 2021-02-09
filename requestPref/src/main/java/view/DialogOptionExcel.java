package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Tools;

public class DialogOptionExcel {
	
	private String valor;
	private JButton ok = new JButton("OK");
	private JCheckBox checkRazao = new JCheckBox("Razão");
	private JCheckBox checkFantasia = new JCheckBox("Fantasia");
	private JCheckBox checkInscricao = new JCheckBox("Inscrição");
	private JCheckBox checkFone = new JCheckBox("Fone");
	//private JCheckBox checkEmail = new JCheckBox("Email");


	public DialogOptionExcel(){
		
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
		
		//Razao:
		if(checkRazao.isSelected()==true)
			str = alteraBitString(str, 0, true);
		else
			str = alteraBitString(str,0,false);
		
		//Fantasia
		if(checkFantasia.isSelected()==true)
			str = alteraBitString(str, 1, true);
		else
			str = alteraBitString(str,1,false);
		
		//Inscricao
		if(checkInscricao.isSelected()==true)
			str = alteraBitString(str, 2, true);
		else
			str = alteraBitString(str,2,false);
		
		//Fone
		if(checkFone.isSelected()==true)
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
			 painel.add(checkRazao,c);
			 
			 c.fill = GridBagConstraints.HORIZONTAL;
			 c.gridx = 0;
			 c.gridy = 1;
			 painel.add(checkFantasia,c);
			 
			 c.fill = GridBagConstraints.HORIZONTAL;
			 c.gridx = 1;
			 c.gridy = 0;
			 painel.add(checkInscricao,c);
			 
			 c.fill = GridBagConstraints.HORIZONTAL;
			 c.gridx = 1;
			 c.gridy = 1;
			 painel.add(checkFone,c);
			 
			 valor = tools.getExcel();
			 
			 if(valor.substring(0, 1).equals("1"))//Razao - Posição 0
					checkRazao.setSelected(true);
				else
					checkRazao.setSelected(false);
			 
			 if(valor.substring(1, 2).equals("1"))//Fantasia - Posição 1
					checkFantasia.setSelected(true);
				else
					checkFantasia.setSelected(false);
			 
			 if(valor.substring(2, 3).equals("1"))//Inscricao - Posição 2
					checkInscricao.setSelected(true);
				else
					checkInscricao.setSelected(false);
			 
			 if(valor.substring(3, 4).equals("1"))//Fone - Posição 3
					checkFone.setSelected(true);
				else
					checkFone.setSelected(false);
			 
			 
			 int result = JOptionPane.showConfirmDialog(null, painel, "Configuração Excel",
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			 if (result == JOptionPane.OK_OPTION) {
				 valor = testaCheckBox(valor);
				 tools.setExcel(valor);
				 //System.out.println("OK: "+valor);
				 
			 }else {
				 System.out.println("Cancelled");
			 }
			 
			 return tools;
	}
		
}

