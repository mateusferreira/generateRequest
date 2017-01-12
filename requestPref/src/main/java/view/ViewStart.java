package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.Controller;
import entities.Tools;

public class ViewStart extends JFrame{
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	
	private JMenuBar menu;
	private JMenu menuPrincipal;
	private JMenu menuSobre;

	private JLabel lEmpty = new JLabel("");
	
	private JPanel painel;
	private JPanel painelGeral;
	private JPanel painelLogo;
	
	private JRadioButton radioCPF = new JRadioButton("CPF");
	private JRadioButton radioCNPJ = new JRadioButton("CNPJ");
	
	private ButtonGroup group = new ButtonGroup();
	private JButton button;
	private JButton btMakeRequest;
	
	private Controller controller;
	
	private int vTop = 10;
	private int vLeft = 20;
	private int vRight = 20;
	private int vBottom = 10;
	
	private String pathSaveFile;
	private Tools tools;
	
	
	//TODO: Criar o construtor para receber o controller
	public ViewStart(Controller controller, String titulo) {
		super(titulo);
		this.controller = controller;
	}
	
	public void init(){
		super.setSize(500, 300);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		super.setContentPane(getPainelGeral());
		super.setJMenuBar(getMenu());
		super.setVisible(true);
		super.setResizable(false);//Desabilitar o Maximizar
		
		configTools();
	}
	
	private void configTools(){
		
		tools = controller.getTools(1);
		
		if(tools == null){
			tools.setId(1);
			tools.setCargo("Prefeito");
			tools.setPronome("Exm Sr");
			tools.setCidadeEstado("Gonçalves - MG");
			tools.setNome("Fulano");
			tools.setTratamento("DD");
			tools.setPathFile("D:");
		}
		
		controller.guardaTools(tools);
		
	}
	
	private JPanel getPainelGeral(){
		if(painelGeral == null){
			painelGeral = new JPanel();
			BorderLayout layout = new BorderLayout();
			painelGeral.setLayout(layout);
			
			painelGeral.add(getPainelLogo(), BorderLayout.NORTH);
			painelGeral.add(getPainel(), BorderLayout.SOUTH);
		}
		
		return painelGeral;
		
	}
	
	private JPanel getPainelLogo(){
		if(painelLogo == null){
			painelLogo = new JPanel(){
				public void paintComponent(Graphics g) {        
					super.paintComponent(g);      
					Image imagem = new ImageIcon(getClass().getResource("/imagens/logo_timbre.png")).getImage();    
					g.drawImage(imagem, 0, 0, this);
				}
			};    
					getContentPane().add(painelLogo);  
				
		}
			painelLogo.setBackground(Color.BLUE);
			painelLogo.setPreferredSize(new Dimension(500, 86));
		
		
		return painelLogo;
	}
	
	private JPanel getPainel() {
		if (painel == null) {
			painel = new JPanel();
			
			painel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			if (shouldFill) {
				//natural height, maximum width
				c.fill = GridBagConstraints.HORIZONTAL;
				}
			
			radioCPF.setSelected(true);
			
			//painel.add(getMenu());
			group.add(radioCPF);
			group.add(radioCNPJ);
			
			if (shouldWeightX) {
				c.weightx = 0.5;
			}
			//Cada botão terá largura de 2 colunas
			//linha 0
			lEmpty = new JLabel("");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			//c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 0;
			painel.add(lEmpty, c);
			
			lEmpty = new JLabel("");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = 0;
			painel.add(lEmpty, c);
			
			lEmpty = new JLabel("");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 0;
			painel.add(lEmpty, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 3;
			c.gridy = 0;
			painel.add(radioCPF,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			//c.gridwidth = 2;
			c.gridx = 4;
			c.gridy = 0;
			painel.add(radioCNPJ,c);
			
			//Row 1
			button = new JButton("<html><center>CADASTRAR<br/>REQUERIMENTO</center></html>");
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 1;
			painel.add(button, c);
			
			lEmpty = new JLabel("");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 1;
			painel.add(lEmpty, c);
			
			btMakeRequest = new JButton("<html><center>GERAR<br/>REQUERIMENTO</center></html>");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridwidth = 2;
			c.gridx = 3;
			c.gridy = 1;
			painel.add(btMakeRequest, c);
			
			
			btMakeRequest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controller.goShowPersonTable(radioCPF.isSelected());
				}
			});
			
			//lINHA 2
			button = new JButton("<html><center>CONSULTAR<br/>REQUERIMENTO</center></html>");
			if (shouldWeightX) {
			c.weightx = 0.5;
			}
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridwidth = 2;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 2;
			painel.add(button, c);
			
			lEmpty = new JLabel("");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 2;
			painel.add(lEmpty, c);
			
			button = new JButton("<html><center>GERAR<br/>CND</center></html>");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.weightx = 0.5;
			c.gridwidth = 2;
			//c.ipadx = 0;
			c.gridx = 3;
			c.gridy = 2;
			painel.add(button, c);
			
		}
		return painel;
	}
	
	private JMenuBar getMenu(){
		
		if(menu == null){
			menu = new JMenuBar();
			menu.add(getMenuPrincipal());
			menu.add(getMenuSobre());
		}
		return menu;
	}
	
	public JMenu getMenuPrincipal() {
		if(menuPrincipal == null){
			menuPrincipal = new JMenu("Settings");
			
			JMenuItem itemTrocaMajor = new JMenuItem("Trocar Prefeito", new ImageIcon("images/add.png"));
			JMenuItem itemSaveFile = new JMenuItem("Pasta Destino", new ImageIcon("images/add.png"));
			JMenuItem itemSair = new JMenuItem("Sair", new ImageIcon("images/sair.png"));
			
			menuPrincipal.add(itemTrocaMajor);
			menuPrincipal.add(itemSaveFile);
			menuPrincipal.add(itemSair);
			
			itemTrocaMajor.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					//System.out.println("teste...");
					controller.trocarPrefeito(tools);
				}
			});
			
			itemSaveFile.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("Escolher local para salvar arquivos");
					JFileChooser arquivo = new JFileChooser();
					arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION){ 
			              pathSaveFile = arquivo.getSelectedFile().getPath();
			          }
					else{
						pathSaveFile = "D:";
					}
					
					int confirmaPath = JOptionPane.showConfirmDialog(ViewStart.this, 
							"Confirma o caminhao "+pathSaveFile+"?",
							"Sair",
							JOptionPane.YES_NO_OPTION);
					System.out.println(pathSaveFile);
					
					if(confirmaPath == 0){
						//String nomePasta = JOptionPane.showInputDialog(ViewStart.this, "Nome da Pasta", "OBRIGATÓRIO", JOptionPane.PLAIN_MESSAGE);
						
						//File diretorio = new File(pathSaveFile);
						
						File subDiretorioFisica = new File(pathSaveFile+"/P.FISICA");
						File subDiretorioEmpresa = new File(pathSaveFile+"/EMPRESA");
						
						
						if(!subDiretorioFisica.exists()){
							subDiretorioFisica.mkdir();
						}
						
						if(!subDiretorioEmpresa.exists()){
							subDiretorioEmpresa.mkdir();
						}
						
						tools.setPathFile(pathSaveFile);//INCLUIR A PASTA CRIADA
						controller.updateTools(tools);
						
					}
					System.out.println("Dialog: "+confirmaPath);
					
				}
			});
			
			itemSair.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent arg0) {
					
					int resp = JOptionPane.showConfirmDialog(ViewStart.this, 
															"Deseja sair ?",
															"Sair",
															JOptionPane.YES_NO_OPTION);
					
					if(resp == 0){
						System.exit(0);
					}
					return;
				}
			});
		}
		return menuPrincipal;
	}
	
	public JMenu getMenuSobre() {
		if(menuSobre == null){
			menuSobre = new JMenu("Help");
			JMenuItem about = new JMenuItem("About");
			menuSobre.add(about);
			
			about.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("clicked");
					JOptionPane.showMessageDialog(ViewStart.this, "Software para gestão Secretaria PMG!\n"
							+ "Trial Version 1.0.0 - @2017\n\nDeveloped By Mateus Ferreira de Souza\nmateus.ferreira@goncalves.mg.gov.br"
							+"\nseraomateus@hotmail.com");
					
				}
			});
		}
		return menuSobre;
	}
	

}
