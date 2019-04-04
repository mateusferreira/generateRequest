package view;

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.Image;
	import java.awt.Insets;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.IOException;
	import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.imageio.ImageIO;
	import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
	import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
	import javax.swing.JFormattedTextField;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
	import javax.swing.JTable;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;
	import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI.ComboBoxLayoutManager;
import javax.swing.text.MaskFormatter;

	import control.Controller;
import entities.Empresa;
import entities.Pessoa;
import model.LimitTextField;
import requestPref.requestPref.Runner;

public class MakeBusinessRequest extends JFrame{
	
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	
	private PessoaTableView pV;
	
	private int vTop = 6;//10
	private int vLeft = 1;//5
	private int vRight = 1;//5
	private int vBottom = 6;//10
	private int multBorda = 20;
	
	private boolean isEmpresaNova;// ao clicar no botão salvar alterações, esta variavel decidirá se irá cadastrar um novo ou se vai editar as informações.
	
	private Controller controller;
		
	//Paineis....
	private JPanel painelGeral;
	private JPanel painelLogo;
	private JPanel painelCentral;
	private JPanel painel;
	private JPanel painelCentroSup;
	private JPanel painelRequest;
		
	//Label....
	private JLabel lcnpj = new JLabel("CNPJ: *");
	private JLabel lfantasia = new JLabel("Nome Fantasia");
	private JLabel lrazao = new JLabel("Razão: *");
	private JLabel lAtividade = new JLabel("Atividade:");
	private JLabel lDataInicio = new JLabel("Data In.");
	private JLabel linscMunicipal = new JLabel("Inscr.:");

	private JLabel lAddress	= new JLabel("Endereço:*");
	private JLabel lNumber = new JLabel("nº");
	private JLabel lBairro = new JLabel("Bairro:*");
	private JLabel lCity = new JLabel("Cidade: *");
	private JLabel lUf = new JLabel("Estado:");
	
	private JLabel lbombeiro = new JLabel("Alv. Bombeiros");
	private JLabel lagua = new JLabel("Água");
	private JLabel lcadastur = new JLabel("CADASTUR");
	private JLabel lcopam = new JLabel("COPAM");
	private JLabel lFone = new JLabel("Fone");
	private JLabel lEmail = new JLabel("E-Mail");
	private JLabel lStatus = new JLabel("Status");
	private JLabel lLenha = new JLabel("Lenha");
	private JLabel lNotas = new JLabel("Observação");
	private JLabel lServices = new JLabel("Somente prestador?");
	
	private JRadioButton radioPrestadorYes = new JRadioButton("Sim");
	private JRadioButton radioPrestadorNo = new JRadioButton("Não");
	
	private ButtonGroup group = new ButtonGroup();
		
		//TextField
		//private JTextField textCpf = new JTextField(20);
		//JFormattedTextField
		private JFormattedTextField textCnpj = null;
		private JFormattedTextField textDataInicio = null;
		
		//TextField
		private JTextField textRazao = new JTextField(30);
		private JTextField textFantasia = new JTextField(30);
		private JTextField textInscricao = new JTextField(10);
		private JTextField textAddress = new JTextField(10);
		private JTextField textAtividade = new JTextField(30);
		//private JTextField textDataInicio = new JTextField(10);
		private JTextField textNum = new JTextField(5);
		private JTextField textBairro = new JTextField(10);
		private JTextField textCity = new JTextField(10);
		
		//Campos novos 
		private JTextField textBombeiro = new JTextField(10);
		private JTextField textAgua = new JTextField(10);
		private JTextField textCadastur = new JTextField(10);
		private JTextField textCopam = new JTextField(10);
		private JTextField textFone = new JTextField(10);
		private JTextField textEmail = new JTextField(10);
		private JTextField textNotas = new JTextField(30);
	
		
		private JTextArea areaRequest = new JTextArea();
		
		

		private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
								"PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
		
		JComboBox comboUf = new JComboBox(uf);
		
		private String[] status = {"CRIANDO", "ATIVO", "BAIXADO"};
		JComboBox comboStatus = new JComboBox(status);
		
		private String[] lenha = {"N/A", "OK", "FALTA DOC"};
		JComboBox comboLenha = new JComboBox(lenha);
		
		
		//JButton
		private JButton editarInformacoesCadastro = new JButton("Editar");
		private JButton botaoDeletarEmpresa = new JButton("Excluir");
		private JButton botaoSalvarAlteracoesCadastro = new JButton("Salvar Alterações");
		private JButton botaoCriarRequerimento = new JButton("Criar");
		
		private JCheckBox checkRequerimento = new JCheckBox("Gerar Req.");
		private JCheckBox checkCND = new JCheckBox("Gerar CND");
		
		private JCheckBox checkCampoDeferido = new JCheckBox("IMPRIMIR LOCAL DEFERIMENTO");
		
		private Empresa empresa;
		
		public MakeBusinessRequest(Controller controller, String titulo, Empresa e){
			super(titulo);
			this.controller = controller;
			empresa = e;
			Runner.LOGGER.setLevel(Level.INFO);
			Runner.LOGGER.info("Make BusinessRequest");
		}
		
		public void init(){
			super.setSize(700, 750);//Largura X Altura
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			super.setContentPane(getPainelGeral());
			//super.setJMenuBar(getMenu());
			super.setVisible(true);
			//super.setResizable(false);//Desabilitar o Maximizar
			
		}
		
		private void limitarTextFields(){
			textBairro.setDocument(new LimitTextField(30));
			textAtividade.setDocument(new LimitTextField(80));
			textRazao.setDocument(new LimitTextField(80));
			textFantasia.setDocument(new LimitTextField(80));
			textAddress.setDocument(new LimitTextField(50));
			textNum.setDocument(new LimitTextField(14));
			textCity.setDocument(new LimitTextField(20));
			textInscricao.setDocument(new LimitTextField(6));

			//Implementar depois os novos campos (bombeiros, agua, etc....)
			textEmail.setDocument(new LimitTextField(50));
			
		}
		
		
		private JPanel getPainelGeral(){
			limitarTextFields();
			
			radioPrestadorNo.setSelected(true);
			group.add(radioPrestadorNo);
			group.add(radioPrestadorYes);
			
			if(painelGeral == null){
				painelGeral = new JPanel();
				BorderLayout layout = new BorderLayout();
				painelGeral.setLayout(layout);
				
				painelGeral.add(getPainelLogo(), BorderLayout.NORTH);
				painelGeral.add(getPainelRequest(), BorderLayout.SOUTH);
				painelGeral.add(getPainel(), BorderLayout.CENTER);
				
			}
			
			return painelGeral;
		}
		
		private JPanel getPainelLogo(){
			if(painelLogo == null){
				painelLogo = new JPanel(){
					public void paintComponent(Graphics g) {        
						super.paintComponent(g);            
						//Image imagem = new ImageIcon("imagens/logo_650.png").getImage();
						Image imagem = new ImageIcon(getClass().getResource("/imagens/logo_650.png")).getImage();    
						//Image imagem = new ImageIcon(MakeRequest.class.getResource("/logo_650.png")).getImage();   
						
						//Image teste = imagem.getImage();

						g.drawImage(imagem, 0, 0, super.getWidth(),87,this);
					}
				};    
						getContentPane().add(painelLogo);  
					
			}
				painelLogo.setBackground(Color.BLUE);
				painelLogo.setPreferredSize(new Dimension(500, 86));
			
			return painelLogo;
		}
		
		private JPanel getPainel(){
			if (painelCentral == null){
				painelCentral = new JPanel();
				
				BorderLayout layout = new BorderLayout();
				painelCentral.setLayout(layout);
				
				painelCentral.add(getPainelCentralSuperior(), BorderLayout.NORTH);
				painelCentral.add(getPainelCentralInferior(), BorderLayout.SOUTH);
			}
			return painelCentral;
		}
		
		public void recebePessoa(Empresa empresa){
			this.empresa = empresa;
		}
		
		private JPanel getPainelCentralSuperior(){
			if(painelCentroSup == null){
				painelCentroSup = new JPanel();
				painelCentroSup.setBackground(new Color(100,200,200));
				painelCentroSup.add(lcnpj);
				painelCentroSup.add(mascaraCpf());
			
							
				if(empresa == null){
					//System.out.println("CPF não encontrado!");
					int opcao = JOptionPane.showConfirmDialog(null, "Deseja cadastrar nova Firma?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
					//System.out.println(opcao);//0 - SIM - 1 - NÃO
					Runner.LOGGER.setLevel(Level.WARNING);
					Runner.LOGGER.info("PERGUNTA: DESEJA CADASTRAR NOVA FIRMA?");
					
					if(opcao == 0){
						//textCpf.setEnabled(false);//trava o CPF;
						botaoSalvarAlteracoesCadastro.setEnabled(true);
						isEmpresaNova = true;//habilito para escolher no clicar do botão Salvar Alterações.
						isFieldsAble(true);//Habilito o Cadastro
						
						//********SUGESTÕES PARA PREENCHIMENTO
					
						textCity.setText("GONÇALVES");
						comboUf.setSelectedIndex(12);
					}
					else{
						dispose();//Fecha a aplicação...
					}
				}
				
				else{
					//System.out.println("NOME: "+pessoa.getNome()+" ENDEREÇO: "+ pessoa.getEndereco());
					botaoDeletarEmpresa.setEnabled(true);
					setPainelDataPessoa();
					editarInformacoesCadastro.setEnabled(true);
					botaoCriarRequerimento.setEnabled(true);
					isEmpresaNova = false;
					isFieldsAble(false);
				}
				
				
				painelCentroSup.add(editarInformacoesCadastro);
				editarInformacoesCadastro.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						botaoCriarRequerimento.setEnabled(false);
						areaRequest.setEnabled(false);
						botaoSalvarAlteracoesCadastro.setEnabled(true);
						textCnpj.setEnabled(false);//Travar o CPF para não modificar....
						isFieldsAble(true);
						Runner.LOGGER.setLevel(Level.WARNING);
						Runner.LOGGER.info("BOTÃO EDITAR INFORMAÇÕES CADASTRO EMPRESA PRESSIONADO");
					}
				});
				
				painelCentroSup.add(botaoDeletarEmpresa);
				
				botaoDeletarEmpresa.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cadastro?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
						//System.out.println(opcao);//0 - SIM - 1 - NÃO
						
						if(opcao == 0){
							//System.out.println("Implementar SIM");
							Runner.LOGGER.setLevel(Level.WARNING);
							Runner.LOGGER.info("DELETANDO EMPRESA: "+empresa.getRazao());
							controller.excluirEmpresaBanco(empresa);
							dispose();
							controller.init();
						}
						else{
							System.out.println("Não fazer nada");
						}
					}
				});
				
				if(empresa == null)//
					editarInformacoesCadastro.setEnabled(false);
				
			}
			
			return painelCentroSup;
		}
		
		private void isFieldsAble (boolean able){
	
			textCnpj.setEditable(able);
			textRazao.setEditable(able);
			textFantasia.setEditable(able);
			textAtividade.setEditable(able);
			textInscricao.setEditable(able);
			
			if(textDataInicio == null){
				textDataInicio = mascaraData();
			}
			textDataInicio.setEditable(able);
			textAddress.setEditable(able);
			textBairro.setEditable(able);
			textCity.setEditable(able);
			textNum.setEditable(able);
			comboUf.setEnabled(able);
			textFone.setEditable(able);
			textEmail.setEditable(able);
			comboStatus.setEnabled(able);
			textNotas.setEditable(able);
			radioPrestadorNo.setEnabled(able);
			radioPrestadorYes.setEnabled(able);
			
			if(radioPrestadorYes.isSelected()){
				textBombeiro.setEditable(false);
				textAgua.setEditable(false);
				textCadastur.setEditable(false);
				textCopam.setEditable(false);
				comboLenha.setEnabled(false);
			}
			
			else{
				textBombeiro.setEditable(able);
				textAgua.setEditable(able);
				textCadastur.setEditable(able);
				textCopam.setEditable(able);
				comboLenha.setEnabled(able);
			}
			
		}
		
		//retirar
		private boolean validarCnpj(){
			return true;//*******************************validar CNPJ assim que der.
			/*String cnpj = textCnpj.getText();
			
			int digitoVerificadorUm;
			int digitoVerificadorDois;
			
			int x = Character.getNumericValue(cnpj.charAt(0));
			boolean numEquals = false;
			
			for(int i = 1; i<18; i++){
				if(x == Character.getNumericValue(cnpj.charAt(i))){
					numEquals = true;
				}
				else{
					break;
				}
				
			}
			
			if(numEquals == true)return false;
			
			digitoVerificadorUm = Character.getNumericValue(cnpj.charAt(0)) + 2 * Character.getNumericValue(cnpj.charAt(1)) + 3 * Character.getNumericValue(cnpj.charAt(2));
			digitoVerificadorUm += 4 * Character.getNumericValue(cnpj.charAt(4)) +  5 * Character.getNumericValue(cnpj.charAt(5)) + 6 * Character.getNumericValue(cnpj.charAt(6));
			digitoVerificadorUm += 7 * Character.getNumericValue(cnpj.charAt(8)) + 8 * Character.getNumericValue(cnpj.charAt(9)) + 9 * Character.getNumericValue(cnpj.charAt(10));
			
			digitoVerificadorUm = digitoVerificadorUm % 11;
			digitoVerificadorUm = digitoVerificadorUm % 10;
			
			digitoVerificadorDois = Character.getNumericValue(cnpj.charAt(1)) + 2 * Character.getNumericValue(cnpj.charAt(2)) + 3 * Character.getNumericValue(cnpj.charAt(4));
			digitoVerificadorDois += 4 * Character.getNumericValue(cnpj.charAt(5)) + 5 * Character.getNumericValue(cnpj.charAt(6)) + 6 * Character.getNumericValue(cnpj.charAt(8));
			digitoVerificadorDois += 7 * Character.getNumericValue(cnpj.charAt(9)) + 8 * Character.getNumericValue(cnpj.charAt(10)) + 9 * digitoVerificadorUm;
			digitoVerificadorDois = digitoVerificadorDois % 11;
			digitoVerificadorDois = digitoVerificadorDois % 10;
			
			//System.out.println("Ver.1: "+digitoVerificadorUm+ " Ver.2: "+digitoVerificadorDois);
			//System.out.println("Penultiomo: "+Character.getNumericValue(cpf.charAt(12))+ " Ultimo:" +Character.getNumericValue(cpf.charAt(13)));
			
			if(digitoVerificadorUm == Character.getNumericValue(cnpj.charAt(12)) && digitoVerificadorDois == Character.getNumericValue(cnpj.charAt(13)))
				return true;
			
			else
				return false;*/
		}
		//retirar
		private JFormattedTextField mascaraCpf(){
			if(textCnpj == null){
				try {
					MaskFormatter ms = new MaskFormatter("##.###.###/####-##");
					//ms.setPlaceholderCharacter('_');
					textCnpj = new JFormattedTextField(ms);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				textCnpj.setPreferredSize(new Dimension(100,20));	
				
			}
			
			return textCnpj;
		}
		
		private JFormattedTextField mascaraData(){
			if(textDataInicio == null){
				try {
					MaskFormatter ms = new MaskFormatter("##/##/####");
					ms.setValidCharacters("0123456789");
					//ms.setPlaceholderCharacter('_');
					textDataInicio = new JFormattedTextField(ms);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				textDataInicio.setPreferredSize(new Dimension(10,20));	
				
			}
			
			return textDataInicio;
		}
		
		private JPanel getPainelCentralInferior(){
				
				if(painel == null){
					painel = new JPanel();
				
				painel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				
				if (shouldFill) {
					//natural height, maximum width
					c.fill = GridBagConstraints.HORIZONTAL;
				}
				if (shouldWeightX) {
					c.weightx = 0.20;
				}
				
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				//c.weightx = 0.25;
				c.gridx = 0;
				c.gridy = 0;
				
				//c.anchor = GridBagConstraints.WEST;
				//lNome.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
				painel.add(lrazao,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				//c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 0;
				c.gridwidth = 4;
				painel.add(textRazao,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.anchor = GridBagConstraints.WEST;
				c.gridx = 5;
				c.gridy = 0;
				painel.add(linscMunicipal,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 6;
				c.gridy = 0;
				c.gridwidth = 2;
				painel.add(textInscricao,c);
				c.gridwidth = 1;
				
				//vamo lá inserir a linha nome fantasia: 08/11/2018
				c.fill = GridBagConstraints.HORIZONTAL;
				//c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 1;
				painel.add(lfantasia,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				//c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 1;
				c.gridwidth = 4;
				painel.add(textFantasia,c);
				
				c.gridwidth = 1;
				
				//******
				
				c.fill = GridBagConstraints.NONE;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				//c.weightx = 0.2;
				c.gridx = 0;
				c.gridy = 2;
				painel.add(lAtividade,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor =GridBagConstraints.WEST;
				//c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 2;
				c.gridwidth = 4;
				painel.add(textAtividade,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor =GridBagConstraints.WEST;
				//c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 2;
				painel.add(lDataInicio,c);
				
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor =GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				//c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 6;
				c.gridy = 2;
				c.gridwidth = 2;
				painel.add(textDataInicio,c);

				
				c.gridwidth = 1;
				//LINHA 2 Endereço - Numero
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 3;
				painel.add(lAddress,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 3;
				c.gridwidth = 4;
				painel.add(textAddress,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 3;
				painel.add(lNumber,c);
				
				c.fill = GridBagConstraints.NONE;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 6;
				c.gridy = 3;
				painel.add(textNum,c);
				
				//Linha 3 Bairro - Cidade - Estado
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 4;
				painel.add(lBairro,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 4;
				painel.add(textBairro,c);
				
				c.fill = GridBagConstraints.NONE;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				//c.weightx = 0.2;
				c.gridx = 2;
				c.gridy = 4;
				painel.add(lCity,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				//c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridwidth = 2;
				c.gridx = 3;
				c.gridy = 4;
				painel.add(textCity,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 4;
				painel.add(lUf,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 6;
				c.gridy = 4;
				painel.add(comboUf,c);
				
				//linha 4
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 5;
				c.gridwidth =7;
				painel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
				
				//*********Separador***********
				
				//linha 5 Bombeiros - água cadastur
				c.gridwidth = 1;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 6;
				painel.add(lServices, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 6;
				painel.add(radioPrestadorYes,c);
				
				c.fill = GridBagConstraints.NONE;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				//c.weightx = 0.2;
				//c.gridwidth = 1;
				c.gridx = 2;
				c.gridy = 6;
				painel.add(radioPrestadorNo,c);
				
				
				//linha 5 Bombeiros - água cadastur
				c.gridwidth = 1;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 7;
				painel.add(lbombeiro, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 7;
				painel.add(textBombeiro,c);
				
				c.fill = GridBagConstraints.NONE;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				//c.weightx = 0.2;
				c.gridx = 2;
				c.gridy = 7;
				painel.add(lagua,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				//c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridwidth = 2;
				c.gridx = 3;
				c.gridy = 7;
				painel.add(textAgua,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 7;
				painel.add(lcadastur,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 6;
				c.gridy = 7;
				painel.add(textCadastur,c);
				
				//Linha COPAM LENHA  E STATUS
				
				c.gridwidth = 1;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 8;
				painel.add(lcopam, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 8;
				painel.add(textCopam,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 2;
				c.gridy = 8;
				painel.add(lLenha,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 3;
				c.gridy = 8;
				c.gridwidth = 2;
				painel.add(comboLenha,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 8;
				painel.add(lStatus,c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 6;
				c.gridy = 8;
				painel.add(comboStatus,c);
				
				//Linha Fone e Email
				c.gridwidth = 1;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 9;
				painel.add(lFone, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 1;
				c.gridy = 9;
				c.gridwidth = 2;
				painel.add(textFone,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 3;
				c.gridy = 9;
				painel.add(lEmail,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 4;
				c.gridy = 9;
				c.gridwidth = 4;
				painel.add(textEmail,c);
				
				//Linha Observação:
				
				c.gridwidth = 1;
				c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 10;
				painel.add(lNotas, c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
				c.gridx = 1;
				c.gridy = 10;
				c.gridwidth = 6;
				painel.add(textNotas,c);
				
				//Linha separadora
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 11;
				c.gridwidth =7;
				painel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
				
				//linha 6*******fim***********
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 0;
				c.gridy = 12;
				c.gridwidth = 5;
				checkCampoDeferido.setSelected(false);
				painel.add(checkCampoDeferido,c);
				
				c.gridwidth = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(vTop,vLeft,vBottom,vRight);
				c.gridx = 5;
				c.gridy = 12;
				c.gridwidth = 2;
				painel.add(botaoSalvarAlteracoesCadastro,c);
				botaoSalvarAlteracoesCadastro.setEnabled(isEmpresaNova ? true : false);//TEste....
				
				botaoSalvarAlteracoesCadastro.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						botaoCriarRequerimento.setEnabled(true);
						areaRequest.setEnabled(true);
						Runner.LOGGER.setLevel(Level.WARNING);
						Runner.LOGGER.info("BOTAO SALVAR PRESSIONADO");
						if(isEmpresaNova == false){
							if(updateDataEmpresa()){
								controller.editarEmpresa(empresa);//fazer......................
								isFieldsAble(false);
								botaoSalvarAlteracoesCadastro.setEnabled(false);
								textCnpj.setEnabled(true);
							}
							else{
								Runner.LOGGER.setLevel(Level.WARNING);
								Runner.LOGGER.info("MENSAGEM: CAMPS DE PREENCHIMENTO OBRIGATÓRIO");
								JOptionPane.showMessageDialog(null, "* CAMPOS DE PREENCHIMENTO OBRIGATÓRIO");
							}
						}
						
						else if(isEmpresaNova == true){
							if(validarCnpj() == true){
								empresa = new Empresa();
								empresa.setCnpj(textCnpj.getText());
								if(updateDataEmpresa()){
									controller.inserirNovaEmpresa(empresa);//fazer no DAO....................
									isFieldsAble(false);
									botaoSalvarAlteracoesCadastro.setEnabled(false);
									textCnpj.setEnabled(true);
								}
								else{
									Runner.LOGGER.setLevel(Level.WARNING);
									Runner.LOGGER.info("MENSAGEM: CAMPS DE PREENCHIMENTO OBRIGATÓRIO");
									JOptionPane.showMessageDialog(null, "* CAMPOS DE PREENCHIMENTO OBRIGATÓRIO");
								}
							}
							else{
								//JOptionPane.showConfirmDialog(null, "Deseja cadastrar nova pessoa?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
								JOptionPane.showMessageDialog(null, "CPF Inválido!");
								textCnpj.setText("");
							}
						}
						
					}
				});
			}
			
			return painel;
		}
		
		private void setPainelDataPessoa(){// Metodo para setar as informações do banco na tela.
			System.out.println(empresa.getRazao());
			textCnpj.setText(empresa.getCnpj());
			textRazao.setText(empresa.getRazao());
			textFantasia.setText(empresa.getFantasia());
			textInscricao.setText(empresa.getInscMunicipal());
			textAddress.setText(empresa.getEndereco());
			textBairro.setText(empresa.getBairro());
			textCity.setText(empresa.getCidade());
			textAtividade.setText(empresa.getAtividade());
			
			textDataInicio = mascaraData();
			Date dt = empresa.getDataInicio();
			if(dt != null){
				String data_form = "dd/MM/yyyy";
				SimpleDateFormat simple =  new SimpleDateFormat(data_form);
				String dataFormada = simple.format(dt);
				//System.out.println("Data: "+dataFormada);
				textDataInicio.setText(dataFormada);
			}
			
			
			textNum.setText(empresa.getNumero());
			comboUf.setSelectedItem(empresa.getEstado());
			
			textBombeiro.setText(empresa.getBombeiro());
			textAgua.setText(empresa.getAgua());
			textCadastur.setText(empresa.getCadastur());
			textCopam.setText(empresa.getCopam());
			textFone.setText(empresa.getFone());
			textEmail.setText(empresa.getEmail());
			textNotas.setText(empresa.getNotas());
			
			comboStatus.setSelectedItem(empresa.getStatus());
			comboLenha.setSelectedItem(empresa.getStatus());
			
			if(empresa.getOnlyservices().equals("S")){
				System.out.println("Uai... SIM");
				//radioPrestadorYes.setEnabled(true);
				//radioPrestadorNo.setEnabled(true);
				radioPrestadorYes.setSelected(true);
				//radioPrestadorNo.setSelected(false);
				//radioPrestadorYes.setEnabled(false);
				//radioPrestadorNo.setEnabled(false);
			}
			else{
				System.out.println("Uai Nao");
				radioPrestadorYes.setSelected(false);
				radioPrestadorNo.setSelected(true);
			}
			
		}
		
		private boolean validaCamposObrigatorios(){
			if(textCnpj.getText().isEmpty() || textRazao.getText().isEmpty() || textAddress.getText().isEmpty() ||
					textBairro.getText().isEmpty() || textCity.getText().isEmpty()){
				return false;
			}
			return true;
		}
		
		private boolean updateDataEmpresa(){//Metodo para gravar as informações no banco.
			if(!validaCamposObrigatorios()){
				return false;
			}
			
			empresa.setRazao(textRazao.getText());
			empresa.setFantasia(textFantasia.getText());
			
			if(textInscricao.getText().length() == 0){
				empresa.setInscMunicipal(null);
			}
			else{
				empresa.setInscMunicipal(textInscricao.getText());
			}
			
			if(textAtividade.getText().length() == 0){
				empresa.setAtividade(null);
			}
			else{
				empresa.setAtividade(textAtividade.getText());
			}
			
			
			if(textDataInicio.getText().equals("  /  /    ")){
				empresa.setDataInicio(null);
				//System.out.println("Entrou");
			}
			else{
				
				try {
					empresa.setDataInicio(parseDate(textDataInicio.getText(), "dd/MM/yyyy"));
					//Date data = parseDate(textDataInicio.getText(), "yyyy/MM/dd");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			empresa.setEndereco(textAddress.getText());
			empresa.setNumero(textNum.getText());
			empresa.setBairro(textBairro.getText());
			empresa.setCidade(textCity.getText());
			empresa.setEstado(uf[comboUf.getSelectedIndex()]);
			
			//Novos campos:
			empresa.setBombeiro(textBombeiro.getText());
			empresa.setAgua(textAgua.getText());
			empresa.setCadastur(textCadastur.getText());
			empresa.setCopam(textCopam.getText());
			empresa.setFone(textFone.getText());
			empresa.setEmail(textEmail.getText());
			empresa.setNotas(textNotas.getText());
			
			empresa.setStatus(status[comboStatus.getSelectedIndex()]);
			empresa.setLenha(lenha[comboLenha.getSelectedIndex()]);
			
			if(radioPrestadorYes.isSelected())
				empresa.setOnlyservices("S");
			else
				empresa.setOnlyservices("N");
			
			
			return true;
		}
		
		private Date parseDate(String date, String format) throws ParseException
		{
		    SimpleDateFormat formatter = new SimpleDateFormat(format);
		    return formatter.parse(date);
		
		}
		
		private JTextArea getTextArea(){
			areaRequest.setAlignmentX(CENTER_ALIGNMENT);
			painelRequest.setBorder(BorderFactory.createTitledBorder("VENHO REQUERER..."));
			///painelRequest.setLayout(new BoxLayout(painelRequest, BoxLayout.Y_AXIS));
			areaRequest.setColumns(50);
			areaRequest.setLineWrap(true);
			areaRequest.setRows(8);
			painelRequest.add(areaRequest);
			
			return areaRequest;
		}
		
		private JPanel getPanelSubmit(){
			JPanel painelSubmit = new JPanel();
			
			checkRequerimento.setSelected(true);
			painelSubmit.add(checkRequerimento);
			
			painelSubmit.add(botaoCriarRequerimento);
			botaoCriarRequerimento.setEnabled(false);
			
			botaoCriarRequerimento.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Runner.LOGGER.setLevel(Level.WARNING);
					Runner.LOGGER.info("GERAR ARQUIVO REQUERIMENTO");
					dispose();//Fecha a Aplicação.
					controller.gerarWordCnpj(empresa, areaRequest.getText(), checkCND.isSelected(),checkRequerimento.isSelected(), checkCampoDeferido.isSelected());//**************TODO
					controller.init();
					
				}
			});
			
			JButton bt2 = new JButton("Cancelar");
			painelSubmit.add(bt2);
			
			bt2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					dispose();//fecha a aplicação.
					controller.init();
					
				}
			});
			
			checkCND.setSelected(false);
			painelSubmit.add(checkCND);
			
			return painelSubmit;
		}
		
		
		private JPanel getPainelRequest(){
			if (painelRequest == null){
				painelRequest = new JPanel();
				BorderLayout layout = new BorderLayout();
				painelRequest.setLayout(layout);
				
				painelRequest.add(getTextArea(), BorderLayout.NORTH);
				painelRequest.add(getPanelSubmit(), BorderLayout.SOUTH);
				//painelRequest.setBackground(new Color(100,0,0));
				//painelRequest.add(new JSeparator(SwingConstants.HORIZONTAL));
				
				
			}
			
			return painelRequest;
		}

	}

