package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

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
	//private JFormattedTextField textBusca = null;
	
	private JRadioButton radioNome = new JRadioButton("Nome");
	private JRadioButton radioCPF = new JRadioButton("CPF");
	
	private ButtonGroup group = new ButtonGroup();
	
	private JButton botaoAdicionarNovoCadastro = new JButton("Adicionar");
	private JButton botaoVoltarTelaInicial = new JButton("Voltar");
	
	private ArrayList<Pessoa> listPessoas;
	
	private boolean representa; // se representa for true quer dizer que não é para abrir o formulario de cadastro, mas retornar o nome do representante.

	
	public PessoaTableView(Controller controler, String titulo, boolean representa){
		super(titulo);
		this.controller = controler;
		this.representa = representa; 
	}
	
	public void init(ArrayList<Pessoa>newList){
		
		listPessoas = newList;
		//super.setContentPane(pnBase);
		super.setSize(new Dimension(300, 200));
		super.setContentPane(getPainelGeral());
		super.setVisible(true);
		super.pack();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/PMG.png")));
		
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
	
	/*private JFormattedTextField mascaraText(){
		//if(textBusca == null){
			try {
				MaskFormatter ms = new MaskFormatter("                 ");
				//ms.setPlaceholderCharacter('_');
				textBusca = new JFormattedTextField(ms);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			textBusca.setPreferredSize(new Dimension(100,20));	
			
		//}
		
		return textBusca;
	}*/
	
	/*private JFormattedTextField mascaraCpf(){
		//if(textBusca == null){
			try {
				MaskFormatter ms = new MaskFormatter("###.###.###-##");
				//ms.setPlaceholderCharacter('_');
				textBusca = new JFormattedTextField(ms);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			textBusca.setPreferredSize(new Dimension(100,20));	
			
		//}
		
		return textBusca;
	}*/
	
	private JPanel getPainelBusca(){
		if(painelBusca == null){
			painelBusca = new JPanel();
			
			group.add(radioCPF);
			group.add(radioNome);
			
			radioNome.setSelected(true);
			painelBusca.add(textBusca);
			painelBusca.add(radioNome);
			painelBusca.add(radioCPF);
			
			radioCPF.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("entrou");
					//mascaraCpf();
					
				}
			});
			
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
			
			if(representa == true)// Para evitar janelas em cascata. Só representa se já for cadastrado.
				botaoAdicionarNovoCadastro.setEnabled(false);
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
		if(representa == false)
			controller.goMakeRequest(pessoa);
		else{
			//System.out.println("REPRESENTOU ......");
			controller.setJString(pessoa);
			dispose();
		}
		
	}
	

}
