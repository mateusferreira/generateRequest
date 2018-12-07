	package view;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.util.ArrayList;

	import javax.swing.ButtonGroup;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JRadioButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;

	import control.Controller;
	import entities.Empresa;
	import model.EmpresaTableModel;
import model.RelationTableModel;

	public class RelationListEmpresaTableView extends JFrame {
		
		private Controller controller;
		private RelationTableModel model;
		private JTable table;
		private Empresa empresa = null;
		
		private JPanel painelGeral;
		private JPanel painelTable;
		private JPanel painelBotoes;
		private JPanel painelBusca;
		
		private JTextField textBusca = new JTextField(30);
		//private JFormattedTextField textBusca = null;
		
		private JRadioButton radioNome = new JRadioButton("Nome");
		private JRadioButton radioCNPJ = new JRadioButton("CNPJ");
		
		private ButtonGroup group = new ButtonGroup();
		
		//private JButton botaoAdicionarNovoCadastro = new JButton("Adicionar");
		private JButton botaoVoltarTelaInicial = new JButton("Voltar");
		
		private ArrayList<Empresa> listEmpresas;

		
		public RelationListEmpresaTableView(Controller controler, String titulo){
			super(titulo);
			this.controller = controler;
		}
		
		public void init(ArrayList<Empresa>newList){
			
			listEmpresas = newList;
			
			//super.setContentPane(pnBase);
			super.setSize(1530, 700);
			super.setContentPane(getPainelGeral());
			super.setVisible(true);
			//super.pack();
			
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//table.setMinimumSize(new Dimension(1000,1000));
			
			
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
				
				model = new RelationTableModel(listEmpresas);
				table = new JTable(model);
				//table.setSize(1000,800);
				table.setPreferredScrollableViewportSize(new Dimension(1480,600));
				table.setFillsViewportHeight(true);
				JScrollPane scrollTable = new JScrollPane(table);
				//painelTable.add(scrollTable,BorderLayout.CENTER);
				painelTable.add(scrollTable);
				//painelTable.setSize(1000,500);
				
				
					
				
				//table.setSize(1000,500);
				//table.setSize(painelTable.getWidth(), painelTable.getHeight());
				//table.setPreferredScrollableViewportSize(table.getPreferredSize());
				//table.setPreferredSize(table.getPreferredSize());
				int widthCol = 100;
				table.getColumnModel().getColumn(0).setPreferredWidth(30);//Inscrição
				table.getColumnModel().getColumn(1).setPreferredWidth(180);//Razão
				table.getColumnModel().getColumn(2).setPreferredWidth(180);//Fantasia
				table.getColumnModel().getColumn(3).setPreferredWidth(50);//CNPJ
				table.getColumnModel().getColumn(4).setPreferredWidth(50);//Bombeiro
				table.getColumnModel().getColumn(5).setPreferredWidth(40);//Água
				table.getColumnModel().getColumn(6).setPreferredWidth(60);//CADASTUR
				table.getColumnModel().getColumn(7).setPreferredWidth(50);//COPAM
				table.getColumnModel().getColumn(8).setPreferredWidth(20);//Imposto
				table.getColumnModel().getColumn(9).setPreferredWidth(20);//Lenha
				table.getColumnModel().getColumn(10).setPreferredWidth(20);//Status
				table.getColumnModel().getColumn(11).setPreferredWidth(180);//Observação (notas)
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

			
				
			}
			
			return painelTable;
		}
		
		private JPanel getPainelBusca(){
			if(painelBusca == null){
				painelBusca = new JPanel();
				
				group.add(radioCNPJ);
				group.add(radioNome);
				
				radioNome.setSelected(true);
				painelBusca.add(textBusca);
				painelBusca.add(radioNome);
				painelBusca.add(radioCNPJ);
				
				radioCNPJ.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("entrou na busca por CNPJ");
						//mascaraCpf();
						
					}
				});
				
				textBusca.addKeyListener(new KeyListener() {
					
					public void keyTyped(KeyEvent e) {
					}
					
					public void keyReleased(KeyEvent e) {
						String texto = textBusca.getText().toUpperCase();
						model = new RelationTableModel(model.getEmpresaByAtribute(listEmpresas,texto));
						table.setModel(model);
						//System.out.println(texto);
					}
					
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
			}
			
			return painelBusca;
		}
		
		private JPanel getPainelBotoes(){
			if(painelBotoes == null){
				painelBotoes = new JPanel();
				
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
				
			empresa = model.getPessoaAt(row);//implementar este método
			
			//System.out.println(pessoa.getNome());
			dispose();
			controller.goMakeRequest(empresa);//Alterar para empresa
			
		}
		

	}


