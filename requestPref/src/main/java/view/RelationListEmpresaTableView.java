	package view;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
	import javax.swing.JRadioButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;

import control.Controller;
import dao.EmpresaDAO;
import entities.Empresa;
	import model.EmpresaTableModel;
import model.MyTableCellRender;
import model.RelationTableModel;
import requestPref.requestPref.Runner;

import java.io.*;
import java.sql.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.GREEN;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

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
		
		
		
		private JLabel lPesquisa = new JLabel("Pesquisar por:");
		private ButtonGroup group = new ButtonGroup();
		
		private String[] itensPesquisa = {"RAZÃO", "FANTASIA", "INSCRIÇÃO", "CNPJ"};
		JComboBox comboItensPesquisa = new JComboBox(itensPesquisa);
			
		private JMenuBar menu;
		private JMenu menuPrincipal;
		
		private JButton botaoVoltarTelaInicial = new JButton("Voltar");
		private JButton botaoExport = new JButton("Export to excel");
		
		private ArrayList<Empresa> listEmpresas;
		ArrayList<Empresa> listaExport = new ArrayList<Empresa>();
		
		private int controlaOption = 0; //vou usar para controlar o cabeçalho do arquivo gerado na exportação Excel; (Método makeExcel )
		
		private int totalRow = 0;
		
		private JLabel jTotal = new JLabel("0");
		
		private Color corPendencia = new Color(250,120,120);
		
		
		public RelationListEmpresaTableView(Controller controler, String titulo){
			super(titulo);
			this.controller = controler;
		}
		
		public void init(ArrayList<Empresa>newList){
			
			listEmpresas = newList;
			listaExport = newList;
			
			//super.setContentPane(pnBase);
			super.setSize(1530, 700);
			super.setContentPane(getPainelGeral());
			super.setVisible(true);
			//super.pack();
			
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//table.setMinimumSize(new Dimension(1000,1000));
			
			totalRow = newList.size();
			jTotal.setText(String.valueOf(totalRow));
			controlaOption = 0; // todas as empresas;
			
			
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
				
				
				table = new JTable(model){
				//******************
					public Component prepareRenderer(
					        TableCellRenderer renderer, int row, int column)
					    {
					        Component c = super.prepareRenderer(renderer, row, column);

					        //  add custom rendering here
					        if (!isRowSelected(row))
							{
								c.setBackground(getBackground());
								int modelRow = convertRowIndexToModel(row);
								//Ver "RelationTableModel - String header. linha 17
								//0-Insc |1-Razão |2-Fantasia |3-CNPJ |4-Bombeiro |5-Agua |6-Cadastur |7-COPAM |8-Lenha |9-Status |10 -Somente Serviços |11-Classificação
								String marcaBombeiro = (String)getModel().getValueAt(modelRow, 4);// Coluna Bombeiro
								String marcaIsento = (String)getModel().getValueAt(modelRow, 0);// Coluna Inscrição Municipal
								String marcaAgua = (String)getModel().getValueAt(modelRow, 5);// Coluna Agua
								String marcaCadastur = (String)getModel().getValueAt(modelRow, 6);// Coluna CADASTUR
								String marcaCopam = (String)getModel().getValueAt(modelRow, 7);// Coluna COPAM
								String marcaLenha = (String)getModel().getValueAt(modelRow, 8);// Coluna Lenha
								String baixado = (String)getModel().getValueAt(modelRow, 9); // coluna Status. 
								
								
								if ("FALTA DOC".equals(marcaBombeiro)) c.setBackground(corPendencia);
								else if ("VENCIDO".equals(marcaBombeiro)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaAgua)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaCadastur)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaCopam)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaLenha)) c.setBackground(corPendencia);
								
								
								else if ("ISENTO".equals(marcaIsento)) c.setBackground(Color.GREEN);
								//if("PROTOCOLO".equals(marcaBombeiro)) c.setBackground(Color.YELLOW );
								else if("PROTOCOLO".equals(marcaBombeiro)) c.setBackground(Color.YELLOW );
								else if("AGUARDANDO".equals(marcaBombeiro)) c.setBackground(Color.ORANGE);
								
								//System.out.println("Imprime: "+modelRow);
								
								if(model.verificaData(marcaBombeiro) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaAgua) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaCadastur) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaCopam) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								
								if("BAIXADO".equals(baixado)) c.setBackground(Color.GRAY);
								
							}

					        return c;
					    }
					};
				
				//******************
				//table.setDefaultRenderer(Object.class, new MyTableCellRender(table));
				//table.setBackground(new Color(100,200,128));
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
				table.getColumnModel().getColumn(11).setPreferredWidth(20);//Prestador?
				table.getColumnModel().getColumn(12).setPreferredWidth(180);//Observação (notas)
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
		
		
		
		/*
		private JFormattedTextField mascaraCpf(){
			if(textBusca == null){
				try {
					MaskFormatter ms = new MaskFormatter("##.###.###/####-##");
					//ms.setPlaceholderCharacter('_');
					textBusca = new JFormattedTextField(ms);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				textBusca.setPreferredSize(new Dimension(100,20));	
				
			}
			
			return textBusca;
		}
		*/
		
		private int retornaPesquisa(){
			//Implementação antiga com radioButton
			/*
			if(radioInsc.isSelected() == true) return 0;
			else if(radioNome.isSelected() == true) return 1;
			else if(radioFantasy.isSelected() == true) return 2;
			else  return 3;
			*/
			//Nova implementação com comboText:
			//{"RAZÃO", "FANTASIA", "INSCRIÇÃO", "CNPJ"}
			
			switch(comboItensPesquisa.getSelectedIndex()){
			case 0:
				return 1;
			case 1:
				return 2;
			case 2:
				return 0;
			default:
				return 3;
			}
		}
		
		private JPanel getPainelBusca(){
			if(painelBusca == null){
				painelBusca = new JPanel();
				
				
				
				painelBusca.add(textBusca);
				painelBusca.add(lPesquisa);
				painelBusca.add(comboItensPesquisa);
	
				painelBusca.add(getMenu());
				
				
				textBusca.addKeyListener(new KeyListener() {
					
					public void keyTyped(KeyEvent e) {
					}
					
					public void keyReleased(KeyEvent e) {
						
						
						String texto = textBusca.getText().toUpperCase();
						model = new RelationTableModel(model.getEmpresaByAtribute(listEmpresas,texto, retornaPesquisa()));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
					}
					
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				System.out.println("TOTAL: "+totalRow);
				
			}
			
			return painelBusca;
		}
		
		private JPanel getPainelBotoes(){
			if(painelBotoes == null){
				painelBotoes = new JPanel();
				painelBotoes.add(new JLabel("Total: "));
				//Ver isso.... 
				painelBotoes.add(jTotal);
				painelBotoes.add(botaoVoltarTelaInicial);
				
				botaoVoltarTelaInicial.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						dispose();
						controller.init();
						
					}
				});
				
				painelBotoes.add(botaoExport);
				botaoExport.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						makeExcel();
						//dispose();
					}
					
				});
			}
			
			return painelBotoes;
		}
		
		public void tableClicked() {
				
			int row = table.getSelectedRow();
				
			empresa = model.getPessoaAt(row);//implementar este método
			
			//System.out.println(pessoa.getNome());
			//dispose();
			controller.goMakeRequest(empresa, true);//Alterar para empresa
			
		}
		
		private JMenuBar getMenu(){
			
			if(menu == null){
				menu = new JMenuBar();
				menu.add(getMenuPrincipal());
			}
			return menu;
		}
		
		private JMenu getMenuPrincipal(){
			if(menuPrincipal == null){
				menuPrincipal = new JMenu("Filtrar Por:");
				JMenuItem itemAtiva = new JMenuItem("ATIVA");
				JMenuItem itemPendente = new JMenuItem("PENDENTE");
				JMenuItem itemRegular = new JMenuItem("REGULAR");
				JMenuItem itemObserva = new JMenuItem("OBSERVAÇÃO");
				
				JMenu subMenuClassifica = new JMenu("CLASSIFICAÇÃO");
				//{"DIVERSOS", "POUSADA", "RESTAURANTE", "BAR", "PIZZARIA", "ARTESANTO","CASA ALUGUEL", "AGÊNCIA TURISMO"};
				JMenuItem subMPousada = new JMenuItem("POUSADA");
				JMenuItem subMRestaurante = new JMenuItem("RESTAURANTE");
				JMenuItem subMPizzaria = new JMenuItem("PIZZARIA");
				JMenuItem subMCasa = new JMenuItem("CASA ALUGUEL");
				JMenuItem subMTurismo = new JMenuItem("AGÊNCIA TURISMO");
				JMenuItem subMBar = new JMenuItem("BAR");
				JMenuItem subMArtesanato = new JMenuItem("ARTESANATO");
				
				subMenuClassifica.add(subMPousada);
				subMenuClassifica.add(subMRestaurante);
				subMenuClassifica.add(subMPizzaria);
				subMenuClassifica.add(subMCasa);
				subMenuClassifica.add(subMTurismo);
				subMenuClassifica.add(subMBar);
				subMenuClassifica.add(subMArtesanato);
				
				menuPrincipal.add(itemAtiva);
				menuPrincipal.add(itemPendente);
				menuPrincipal.add(itemRegular);
				menuPrincipal.add(itemObserva);
				menuPrincipal.add(subMenuClassifica);
			
				
				itemAtiva.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("ATIVA");
						listaExport = model.getEmpresasAtivas(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 4; // 4 são as empresas ativas;
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});
				
				itemPendente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("PENDENTE");
						listaExport = model.getEmpresaPendente(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 1; // 1 é pendente;
						//model = new RelationTableModel(model.getEmpresaPendente(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});
				
				itemRegular.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("REGULAR");
						listaExport = model.getEmpresaRegular(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 3; // 3 é regular;
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						//****************
						}
					});
				
				itemObserva.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("OBSERVAÇÃO");
						listaExport = model.getEmpresaObserva(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 2; //2 é observação;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});
				
				subMPousada.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("POUSADA");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "POUSADA");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				subMRestaurante.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("RESTAURANTE");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "RESTAURANTE");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				subMPizzaria.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("PIZZARIA");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "PIZZARIA");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});
				subMCasa.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("CASA ALUGUEL");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "CASA ALUGUEL");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				subMTurismo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("AGÊNCIA TURISMO");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "AGÊNCIA TURISMO");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				subMBar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("BAR");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "BAR");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				subMArtesanato.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						menuPrincipal.setText("ARTESANATO");
						listaExport = model.getEmpresaByClassifica(listEmpresas, "ARTESANATO");
						model = new RelationTableModel(listaExport);
						controlaOption = 5; // 5 é Pousada;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						}
					});	
				
			}
			
			
			return menuPrincipal;
		}
		
		private void makeExcel(){
			HSSFWorkbook wb = new HSSFWorkbook();
			String texto = null;
			if (controlaOption == 0) texto = "Todas empresas";
			else if (controlaOption == 1) texto = "Empresas Pendentes";
			else if(controlaOption == 2) texto = "Observações";
			else if(controlaOption == 3) texto = "Empresas Regulares";
			else if(controlaOption == 4) texto = "Empresas Ativas";
			else if(controlaOption == 5) texto = "Pousadas";
			
            HSSFSheet sheet = wb.createSheet(texto);
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Inscrição");
            rowhead.createCell((short) 1).setCellValue("Razão");
            rowhead.createCell((short) 2).setCellValue("Fantasia");
            rowhead.createCell((short) 3).setCellValue("CNPJ");
            rowhead.createCell((short) 4).setCellValue("Bombeiro");
            rowhead.createCell((short) 5).setCellValue("ÁGUA");
            rowhead.createCell((short) 6).setCellValue("CADASTUR");
            rowhead.createCell((short) 7).setCellValue("COPAM");
            rowhead.createCell((short) 8).setCellValue("Lenha");
            rowhead.createCell((short) 9).setCellValue("Observações");
            
           
            
            
            //listaExport = model.getEmpresaPendente(listEmpresas);
            
            for(int index = 0; index < listaExport.size(); index++){
	            HSSFRow row = sheet.createRow((short) index + 1);
	            row.createCell((short) 0).setCellValue(listaExport.get(index).getInscMunicipal());
	            row.createCell((short) 1).setCellValue(listaExport.get(index).getRazao());
	            row.createCell((short) 2).setCellValue(listaExport.get(index).getFantasia());
	            row.createCell((short) 3).setCellValue(listaExport.get(index).getCnpj());
	            row.createCell((short) 4).setCellValue(listaExport.get(index).getBombeiro());
	            row.createCell((short) 5).setCellValue(listaExport.get(index).getAgua());
	            row.createCell((short) 6).setCellValue(listaExport.get(index).getCadastur());
	            row.createCell((short) 7).setCellValue(listaExport.get(index).getCopam());
	            row.createCell((short) 8).setCellValue(listaExport.get(index).getLenha());
	            row.createCell((short) 9).setCellValue(listaExport.get(index).getNotas());
            }
            try{
            	
            
           // FileOutputStream fileOut = new FileOutputStream("D:/excelFile.xls");
            FileOutputStream fileOut = new FileOutputStream("D:/"+texto+".xls");
            wb.write(fileOut);
            fileOut.close();
            System.out.println("exportou?");
            JOptionPane.showMessageDialog(RelationListEmpresaTableView.this, "Arquivo Gerado com sucesso em D:");
            }
    		catch(Exception e){
    			e.printStackTrace();
    		}
            
		}
		

	}


