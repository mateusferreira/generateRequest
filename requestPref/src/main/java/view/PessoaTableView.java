package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import control.Controller;
import entities.Pessoa;
import model.PessoaTableModel;

public class PessoaTableView extends JFrame {
	
	private Controller controller;
	private PessoaTableModel model;
	private JTable table;
	private Pessoa pessoa = null;
	
	private JPanel painelGeral;
	private JPanel painelTable;
	private JPanel painelBotoes;
	private JPanel painelBusca;
	
	private JTextField textBusca = new JTextField(30);
	
	private JRadioButton radioNome = new JRadioButton("Nome");
	private JRadioButton radioCPF = new JRadioButton("CPF");
	
	private JButton botaoAdicionarNovoCadastro = new JButton("Adicionar");
	private JButton botaoVoltarTelaInicial = new JButton("Voltar");
	
	private ArrayList<Pessoa> listPessoas;

	
	public PessoaTableView(Controller controler, String titulo){
		super(titulo);
		this.controller = controler;
	}
	
	public void init(ArrayList<Pessoa>newList){
		
		listPessoas = newList;
		//super.setContentPane(pnBase);
		super.setSize(new Dimension(300, 200));
		super.setContentPane(getPainelGeral());
		super.setVisible(true);
		super.pack();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private JPanel getPainelGeral(){
		if(painelGeral == null){
			painelGeral = new JPanel();
			BorderLayout layout = new BorderLayout();
			painelGeral.setLayout(layout);
			
			painelGeral.add(getPainelBusca(), BorderLayout.NORTH);
			painelGeral.add(getPainelTable(), BorderLayout.CENTER);
			painelGeral.add(getPainelBotoes(), BorderLayout.SOUTH);
			
		}
		
		return painelGeral;
	}
	
	private JPanel getPainelTable(){
		if(painelTable == null){
			painelTable = new JPanel();
			
			model = new PessoaTableModel(listPessoas);
			
			table = new JTable(model);
			
			MouseListener listener = new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						//fazer o que precisa aqui
						
						tableClicked();
					}
				}
			};
			
			table.addMouseListener(listener);

			JScrollPane scroll = new JScrollPane(table);
			//JPanel pnBase = new JPanel();
			//pnBase.add(scroll);
			painelTable.add(scroll);
			
		}
		
		return painelTable;
	}
	
	private JPanel getPainelBusca(){
		if(painelBusca == null){
			painelBusca = new JPanel();
			
			radioNome.setSelected(true);
			painelBusca.add(textBusca);
			painelBusca.add(radioNome);
			painelBusca.add(radioCPF);
			
			textBusca.addKeyListener(new KeyListener() {
				
				public void keyTyped(KeyEvent e) {
				}
				
				public void keyReleased(KeyEvent e) {
					String texto = textBusca.getText().toUpperCase();
					model = new PessoaTableModel(model.getPessaByAtribute(listPessoas,texto));
					table.setModel(model);
					//System.out.println(texto);
				}
				
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
				}
			});
			
			/*textBusca.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					listPessoas = controller.procuraPessoa(textBusca.getText());
					System.out.println("VAi");
					
				}
			});
			*/
		}
		
		return painelBusca;
	}
	
	private JPanel getPainelBotoes(){
		if(painelBotoes == null){
			painelBotoes = new JPanel();
			
			painelBotoes.add(botaoAdicionarNovoCadastro);
			
			botaoAdicionarNovoCadastro.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					controller.goMakeRequest(pessoa);
					
				}
			});
			painelBotoes.add(botaoVoltarTelaInicial);
			
			botaoVoltarTelaInicial.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					dispose();
					controller.init();
					
				}
			});
		}
		
		return painelBotoes;
	}
	
	public void tableClicked() {
			
		int row = table.getSelectedRow();
			
		pessoa = model.getPessoaAt(row);//implementar este método
		
		//System.out.println(pessoa.getNome());
		//dispose();
		controller.goMakeRequest(pessoa);
		
	}
	

}
