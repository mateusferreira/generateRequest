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

import javax.swing.ButtonGroup;
	import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import java.io.*;
import java.sql.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
		
		private JRadioButton radioInsc = new JRadioButton("Inscrição");
		private JRadioButton radioNome = new JRadioButton("Razão");
		private JRadioButton radioFantasy = new JRadioButton("Fantasia");
		private JRadioButton radioCNPJ = new JRadioButton("CNPJ");
		
		
		private ButtonGroup group = new ButtonGroup();
		
		//private JButton botaoAdicionarNovoCadastro = new JButton("Adicionar");
		private JButton botaoVoltarTelaInicial = new JButton("Voltar");
		private JButton botaoExport = new JButton("Export to excel");
		
		private JButton botaoPendentes = new JButton("Pendentes");
		private JButton botaoRegular = new JButton("Regulares");
		private JButton botaoObserva = new JButton("Observação");
		private JButton botaoEmpresaAtiva = new JButton("Ativas");
		
		private ArrayList<Empresa> listEmpresas;
		ArrayList<Empresa> listaExport = new ArrayList<Empresa>();
		
		private int controlaOption = 0; //vou usar para controlar o cabeçalho do arquivo gerado na exportação Excel;
		
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
								String marcaBombeiro = (String)getModel().getValueAt(modelRow, 4);// Coluna Bombeiro
								String marcaIsento = (String)getModel().getValueAt(modelRow, 0);// Coluna Inscrição Municipal
								String marcaAgua = (String)getModel().getValueAt(modelRow, 5);// Coluna Agua
								String marcaCadastur = (String)getModel().getValueAt(modelRow, 6);// Coluna CADASTUR
								String marcaCopam = (String)getModel().getValueAt(modelRow, 7);// Coluna COPAM
								String marcaLenha = (String)getModel().getValueAt(modelRow, 9);// Coluna Lenha
								//String avisoBombeiro = (String)getModel().getValueAt(modelRow, 4);
								
								if ("FALTA DOC".equals(marcaBombeiro)) c.setBackground(corPendencia);
								else if ("VENCIDO".equals(marcaBombeiro)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaAgua)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaCadastur)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaCopam)) c.setBackground(corPendencia);
								else if ("FALTA DOC".equals(marcaLenha)) c.setBackground(corPendencia);
								
								
								else if ("ISENTO".equals(marcaIsento)) c.setBackground(Color.GREEN);
								//if("PROTOCOLO".equals(marcaBombeiro)) c.setBackground(Color.YELLOW );
								else if("PROTOCOLO".equals(marcaBombeiro)) c.setBackground(Color.YELLOW );
								
								//System.out.println("Imprime: "+modelRow);
								
								if(model.verificaData(marcaBombeiro) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaAgua) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaCadastur) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								else if(model.verificaData(marcaCopam) == true) c.setBackground(corPendencia); //Testa se data já é vencida.
								
								
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
			
			if(radioInsc.isSelected() == true) return 0;
			else if(radioNome.isSelected() == true) return 1;
			else if(radioFantasy.isSelected() == true) return 2;
			else  return 3;
			
		}
		private JPanel getPainelBusca(){
			if(painelBusca == null){
				painelBusca = new JPanel();
				
				group.add(radioInsc);
				group.add(radioNome);
				group.add(radioFantasy);
				group.add(radioCNPJ);
				
				radioNome.setSelected(true);
				painelBusca.add(textBusca);
				painelBusca.add(radioInsc);
				painelBusca.add(radioNome);
				painelBusca.add(radioFantasy);
				painelBusca.add(radioCNPJ);
				painelBusca.add(botaoEmpresaAtiva);
				painelBusca.add(botaoPendentes);
				painelBusca.add(botaoRegular);
				
				botaoRegular.addActionListener(new ActionListener(){ //EMPRESA REGULAR
					public void actionPerformed(ActionEvent e){
						listaExport = model.getEmpresaRegular(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 3; // 3 é regular;
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
					}
				});
				
				painelBusca.add(botaoObserva);
				
				botaoPendentes.addActionListener(new ActionListener() { //EMPRESA PENDENTE
									
									public void actionPerformed(ActionEvent e) {
										listaExport = model.getEmpresaPendente(listEmpresas);
										model = new RelationTableModel(listaExport);
										controlaOption = 1; // 1 é pendente;
										//model = new RelationTableModel(model.getEmpresaPendente(listEmpresas));
										table.setModel(model);
										totalRow = table.getRowCount();
										jTotal.setText(String.valueOf(totalRow));
										
									}
								});
				
				botaoObserva.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						listaExport = model.getEmpresaObserva(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 2; //2 é observação;
						//model = new RelationTableModel(model.getEmpresaObserva(listEmpresas));
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
						
					}
				});
				
				botaoEmpresaAtiva.addActionListener(new ActionListener(){ //EMPRESAS ATIVAS
					public void actionPerformed(ActionEvent e){
						listaExport = model.getEmpresasAtivas(listEmpresas);
						model = new RelationTableModel(listaExport);
						controlaOption = 4; // 4 são as empresas ativas;
						table.setModel(model);
						totalRow = table.getRowCount();
						jTotal.setText(String.valueOf(totalRow));
					}
				});
				
				/*radioCNPJ.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("entrou na busca por CNPJ");
						//mascaraCpf();
						
					}
				});*/
				
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
		
		private void makeExcel(){
			HSSFWorkbook wb = new HSSFWorkbook();
			String texto = null;
			if (controlaOption == 0) texto = "Todas empresas";
			else if (controlaOption == 1) texto = "Empresas Pendentes";
			else if(controlaOption == 2) texto = "Observações";
			else if(controlaOption == 3) texto = "Empresas Regulares";
			else if(controlaOption == 4) texto = "Empresas Ativas";
			
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


