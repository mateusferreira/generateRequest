package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Tools;

public class DialogChangeMajor {
	
	public DialogChangeMajor(){
		
	}
	
	public Tools display(Tools tools) {
        
		//SETAR AS CONFIGURAÇÕES ATUAIS
        JTextField pronome = new JTextField(tools.getPronome());
        JTextField nome = new JTextField(tools.getNome());
        JTextField tratamento = new JTextField(tools.getTratamento());
        JTextField cargo = new JTextField(tools.getCargo());
        JTextField cidade = new JTextField(tools.getCidadeEstado());
        JPanel panel = new JPanel(new GridLayout(0, 1));
        //panel.add(combo);
        panel.add(new JLabel("Pronome:"));
        panel.add(pronome);
        panel.add(new JLabel("Nome:"));
        panel.add(nome);
        
        panel.add(new JLabel("trat:"));
        panel.add(tratamento);
        panel.add(new JLabel("Cargo:"));
        panel.add(cargo);
        panel.add(new JLabel("Cidade:"));
        panel.add(cidade);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(cargo.getText()
                + " " + pronome.getText()
                + " " + nome.getText());
            
            tools.setNome(nome.getText());
            tools.setPronome(pronome.getText());
            tools.setTratamento(tratamento.getText());
            tools.setCargo(cargo.getText());
            tools.setCidadeEstado(cidade.getText());
            
       
        } else {
            System.out.println("Cancelled");
        }
        
        return tools;
    }

}
