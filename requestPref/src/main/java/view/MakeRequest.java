	package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import control.Controller;
import entities.Pessoa;
import model.LimitTextField;
import requestPref.requestPref.Runner;


public class MakeRequest extends JFrame {
	
	//private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String lineSeparator = System.getProperty("line.separator");
	//private Runner run;
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	
	private PessoaTableView pV;
	
	private boolean valor = false;//teste para setar o valor do representante na janela.
	
	private int vTop = 6;//10
	private int vLeft = 1;//5
	private int vRight = 1;//5
	private int vBottom = 6;//10
	private int multBorda = 20;
	
	private boolean isPessoaNova;// ao clicar no botão salvar alterações, esta variavel decidirá se irá cadastrar um novo ou se vai editar as informações.
	
	private Controller controller;
	
	//Paineis....
	private JPanel painelGeral;
	private JPanel painelLogo;
	private JPanel painelCentral;
	private JPanel painel;
	private JPanel painelCentroSup;
	private JPanel painelRequest;
	private JPanel painelEmNomeDe;
	
	//Label....
	private JLabel lcpf = new JLabel("CPF:");
	private JLabel lNome = new JLabel("Nome:");
	private JLabel lSexo = new JLabel("Sexo:");
	private JLabel lNacionalidade = new JLabel("Nacionalidade:");
	private JLabel lEstadoCivil = new JLabel("Est.Civil:");
	private JLabel lRg = new JLabel("RG:");
	private JLabel lAddress	= new JLabel("Endereço:");
	private JLabel lNumber = new JLabel("nº");
	private JLabel lBairro = new JLabel("Bairro:");
	private JLabel lCity = new JLabel("Cidade:");
	private JLabel lUf = new JLabel("Estado:");
	
	//TextField
	//private JTextField textCpf = new JTextField(20);
	//JFormattedTextField
	private JFormattedTextField textCpf = null;
	
	
	//TextField
	private JTextField textNome = new JTextField(30);
	private JTextField textNacionalidade = new JTextField(10);
	private JTextField textRG = new JTextField(10);
	private JTextField textAddress = new JTextField(10);
	private JTextField textNum = new JTextField(5);
	private JTextField textBairro = new JTextField();
	private JTextField textReprCPF = new JTextField(8);
	//textBairro.setDocument(new LimitedDocument(10));
	
	private JTextField textCity = new JTextField(10);
	
	private JTextField textRepresentante = new JTextField(30);
	
	private JTextArea areaRequest = new JTextArea();
	
	//ComboBox
	private String[] sexo = {"Masculino", "Feminino"};
	JComboBox comboSexo = new JComboBox(sexo);
	
	private String[] estadoCivil = {"Casado(a)","Solteiro(a)","Divorciado(a)","Viúvo(a)",""};
	JComboBox comboEstadoCivil = new JComboBox(estadoCivil);
	
	private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
							"PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
	
	JComboBox comboUf = new JComboBox(uf);
	//JButton
	private JButton editarInformacoesCadastro = new JButton("Editar");
	private JButton botaoDeletarPessoa = new JButton("Excluir");
	private JButton botaoSalvarAlteracoesCadastro = new JButton("Salvar Alterações");
	private JButton botaoCriarRequerimento = new JButton("Criar");
	
	private JCheckBox checkRequerimento = new JCheckBox("Gerar Req.");
	private JCheckBox checkCND = new JCheckBox("Gerar CND");
	
	private JCheckBox checkCampoDeferido = new JCheckBox("IMPRIMIR LOCAL DEFERIMENTO");
	
	private Pessoa pessoa;
	private Pessoa representante = null;
	
	
	public MakeRequest(Controller controller, String titulo, Pessoa p){
		super(titulo);
		this.controller = controller;
		pessoa = p;
		Runner.LOGGER.setLevel(Level.INFO);
		Runner.LOGGER.info("Make Request");
	}
	
	public void init(){
		super.setSize(660, 580);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setContentPane(getPainelGeral());
		//super.setJMenuBar(getMenu());
		super.setVisible(true);
		//super.setResizable(false);//Desabilitar o Maximizar
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/PMG.png")));
		
	}
	
	private void limitarTextFields(){
		textBairro.setDocument(new LimitTextField(35));
		textNome.setDocument(new LimitTextField(40));
		textRG.setDocument(new LimitTextField(20));
		textAddress.setDocument(new LimitTextField(50));
		textNum.setDocument(new LimitTextField(14));
		textNacionalidade.setDocument(new LimitTextField(15));
		textCity.setDocument(new LimitTextField(20));
	}
	
	
	private JPanel getPainelGeral(){
		limitarTextFields();
		
		if(painelGeral == null){
			painelGeral = new JPanel();
			BorderLayout layout = new BorderLayout();
			painelGeral.setLayout(layout);
			
			painelGeral.add(getPainelLogo(), BorderLayout.NORTH);
			painelGeral.add(getPainelRequest(), BorderLayout.SOUTH);
			painelGeral.add(getPainel(), BorderLayout.CENTER);
			
			
			
			/*if(pessoa != null){	
				setPainelDataPessoa();
			}*/
			
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
	
	public void recebePessoa(Pessoa pessoa){
		this.pessoa = pessoa;
	}
	
	private JPanel getPainelCentralSuperior(){
		if(painelCentroSup == null){
			painelCentroSup = new JPanel();
			painelCentroSup.setBackground(new Color(100,200,200));
			painelCentroSup.add(lcpf);
			painelCentroSup.add(mascaraCpf());
		
						
			if(pessoa == null){
				//System.out.println("CPF não encontrado!");
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja cadastrar nova pessoa?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
				//System.out.println(opcao);//0 - SIM - 1 - NÃO
				botaoDeletarPessoa.setEnabled(false);
				if(opcao == 0){
					//textCpf.setEnabled(false);//trava o CPF;
					botaoSalvarAlteracoesCadastro.setEnabled(true);
					isPessoaNova = true;//habilito para escolher no clicar do botão Salvar Alterações.
					isFieldsAble(true);//Habilito o Cadastro
					
					
					//********SUGESTÕES PARA PREENCHIMENTO
					textNacionalidade.setText("BRASILEIRO");
					textCity.setText("GONÇALVES");
					comboUf.setSelectedIndex(12);
				}
				else{
					dispose();//Fecha a aplicação...
				}
			}
			
			else{
				//System.out.println("NOME: "+pessoa.getNome()+" ENDEREÇO: "+ pessoa.getEndereco());
				
				botaoDeletarPessoa.setEnabled(true);
				setPainelDataPessoa();
				editarInformacoesCadastro.setEnabled(true);
				botaoCriarRequerimento.setEnabled(true);
				isPessoaNova = false;
				isFieldsAble(false);
			}
					

			
			painelCentroSup.add(editarInformacoesCadastro);
			editarInformacoesCadastro.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					botaoCriarRequerimento.setEnabled(false);
					areaRequest.setEnabled(false);
					botaoSalvarAlteracoesCadastro.setEnabled(true);
					textCpf.setEnabled(false);//Travar o CPF para não modificar....
					isFieldsAble(true);
				}
			});
			
			painelCentroSup.add(botaoDeletarPessoa);
			
			botaoDeletarPessoa.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cadastro?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
					//System.out.println(opcao);//0 - SIM - 1 - NÃO
					
					if(opcao == 0){
						//System.out.println("Implementar SIM");
						controller.excluirPessoaBanco(pessoa);
						dispose();
						controller.init();
					}
					else{
						System.out.println("Não fazer nada");
					}
				}
			});
			
			if(pessoa == null)//TODO ... VERIFICAR SE NÃO POSSO COLOCAR NO TESTE DA LINHA 219
				editarInformacoesCadastro.setEnabled(false);
			
		}
		
		return painelCentroSup;
	}
	
	private void isFieldsAble (boolean able){
		
		textCpf.setEditable(able);
		textNome.setEditable(able);
		textRG.setEditable(able);
		textNacionalidade.setEditable(able);
		textAddress.setEditable(able);
		textBairro.setEditable(able);
		textCity.setEditable(able);
		textNum.setEditable(able);
		comboSexo.setEnabled(able);
		comboEstadoCivil.setEnabled(able);
		comboUf.setEnabled(able);
		
	}
	
	//retirar
	private boolean validarCpf(JFormattedTextField inCPF){
		String cpf = inCPF.getText();
		
		int digitoVerificadorUm;
		int digitoVerificadorDois;
		
		int x = Character.getNumericValue(cpf.charAt(0));
		boolean numEquals = true;
		for(int i = 1; i<14; i++){
			if(i==3 || i==7 || i==11)continue;//PULO OS PONTOS E O TRAÇO.(XXX.XXX.XXX-XX)
			
			if(x != Character.getNumericValue(cpf.charAt(i))){
				numEquals = false;
				break;
			}
		}
		
		if(numEquals == true){
			System.out.println("Numeros iguais");
			return false;
		}
		
		digitoVerificadorUm = Character.getNumericValue(cpf.charAt(0)) + 2 * Character.getNumericValue(cpf.charAt(1)) + 3 * Character.getNumericValue(cpf.charAt(2));
		digitoVerificadorUm += 4 * Character.getNumericValue(cpf.charAt(4)) +  5 * Character.getNumericValue(cpf.charAt(5)) + 6 * Character.getNumericValue(cpf.charAt(6));
		digitoVerificadorUm += 7 * Character.getNumericValue(cpf.charAt(8)) + 8 * Character.getNumericValue(cpf.charAt(9)) + 9 * Character.getNumericValue(cpf.charAt(10));
		
		digitoVerificadorUm = digitoVerificadorUm % 11;
		digitoVerificadorUm = digitoVerificadorUm % 10;
		
		digitoVerificadorDois = Character.getNumericValue(cpf.charAt(1)) + 2 * Character.getNumericValue(cpf.charAt(2)) + 3 * Character.getNumericValue(cpf.charAt(4));
		digitoVerificadorDois += 4 * Character.getNumericValue(cpf.charAt(5)) + 5 * Character.getNumericValue(cpf.charAt(6)) + 6 * Character.getNumericValue(cpf.charAt(8));
		digitoVerificadorDois += 7 * Character.getNumericValue(cpf.charAt(9)) + 8 * Character.getNumericValue(cpf.charAt(10)) + 9 * digitoVerificadorUm;
		digitoVerificadorDois = digitoVerificadorDois % 11;
		digitoVerificadorDois = digitoVerificadorDois % 10;
		
		//System.out.println("Ver.1: "+digitoVerificadorUm+ " Ver.2: "+digitoVerificadorDois);
		//System.out.println("Penultiomo: "+Character.getNumericValue(cpf.charAt(12))+ " Ultimo:" +Character.getNumericValue(cpf.charAt(13)));
		
		if(digitoVerificadorUm == Character.getNumericValue(cpf.charAt(12)) && digitoVerificadorDois == Character.getNumericValue(cpf.charAt(13)))
			return true;
		
		else
			return false;
	}
	//ver porque não consigo usar essa função passando argumento. Por isso tenho q usar uma cópia dela lá em baixo.
	private JFormattedTextField mascaraCpf(){
	
		if(textCpf == null){
			try {
				MaskFormatter ms = new MaskFormatter("###.###.###-##");
				//ms.setPlaceholderCharacter('_');
				textCpf = new JFormattedTextField(ms);
			} catch (ParseException e) {
				Runner.LOGGER.setLevel(Level.WARNING);
				Runner.LOGGER.info("Erro mascara CPF!");
				e.printStackTrace();
			}
			textCpf.setPreferredSize(new Dimension(100,20));	
			
		}
		return textCpf;
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
			painel.add(lNome,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			//c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 3;
			painel.add(textNome,c);
			
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 4;
			c.gridy = 0;
			painel.add(lRg,c);
			
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
			c.gridx = 5;
			c.gridy = 0;
			c.gridwidth = 2;
			painel.add(textRG,c);
			c.gridwidth = 1;
			//Linha 2: Nacionalidade - Sexo - EstadoCivil
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
			c.anchor = GridBagConstraints.WEST;
			c.gridx = 0;
			c.gridy = 1;
			painel.add(lNacionalidade,c);
			
			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 1;
			c.gridy = 1;
			painel.add(textNacionalidade,c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			//c.weightx = 0.2;
			c.gridx = 2;
			c.gridy = 1;
			painel.add(lSexo,c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor =GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			
			c.gridx = 3;
			c.gridy = 1;
			painel.add(comboSexo,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridwidth = 2;
			c.gridx = 4;
			c.gridy = 1;
			painel.add(lEstadoCivil,c);
			
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
			c.gridx = 6;
			c.gridy = 1;
			painel.add(comboEstadoCivil,c);
			
			//LINHA 2 Endereço - Numero
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
			c.gridx = 0;
			c.gridy = 2;
			painel.add(lAddress,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 4;
			painel.add(textAddress,c);
			
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 5;
			c.gridy = 2;
			painel.add(lNumber,c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
			c.gridx = 6;
			c.gridy = 2;
			painel.add(textNum,c);
			
			//Linha 3 Bairro - Cidade - Estado
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft * multBorda,vBottom,vRight);
			c.gridx = 0;
			c.gridy = 3;
			painel.add(lBairro,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 1;
			c.gridy = 3;
			painel.add(textBairro,c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			//c.weightx = 0.2;
			c.gridx = 2;
			c.gridy = 3;
			painel.add(lCity,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			//c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridwidth = 2;
			c.gridx = 3;
			c.gridy = 3;
			painel.add(textCity,c);
			
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 5;
			c.gridy = 3;
			painel.add(lUf,c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight * multBorda);
			c.gridx = 6;
			c.gridy = 3;
			painel.add(comboUf,c);
			
			//linha 4
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 0;
			c.gridy = 4;
			c.gridwidth =7;
			painel.add(new JSeparator(SwingConstants.HORIZONTAL), c);
			c.gridwidth = 1;
			
			//linha 5
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 5;
			checkCampoDeferido.setSelected(false);// Selecionar ou não o campo Deferido
			painel.add(checkCampoDeferido,c);
			
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(vTop,vLeft,vBottom,vRight);
			c.gridx = 5;
			c.gridy = 5;
			c.gridwidth = 2;
			painel.add(botaoSalvarAlteracoesCadastro,c);
			botaoSalvarAlteracoesCadastro.setEnabled(isPessoaNova ? true : false);//TEste....
			
			botaoSalvarAlteracoesCadastro.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					botaoCriarRequerimento.setEnabled(true);
					areaRequest.setEnabled(true);
					if(isPessoaNova == false){
						if(updateDataPessoa()){
							controller.editarPessoa(pessoa);
							isFieldsAble(false);
							botaoSalvarAlteracoesCadastro.setEnabled(false);
							textCpf.setEnabled(true);
						}
						else{
							Runner.LOGGER.setLevel(Level.WARNING);
							//LOGGER.setLevel(Level.WARNING);
							Runner.LOGGER.info("PREENCHER CAMPOS OBRIGATÓRIOS");
							//LOGGER.info("PREENCHER CAMPOS OBRIGATÓRIOS");
							JOptionPane.showMessageDialog(null, "* CAMPOS DE PREENCHIMENTO OBRIGATÓRIO");
						}
					}
					
					else if(isPessoaNova == true){
						if(validarCpf(textCpf) == true){
							pessoa = new Pessoa();
							pessoa.setCpf(textCpf.getText());
							if(updateDataPessoa()){
								try {
									controller.inserirNovaPessoa(pessoa);
									isFieldsAble(false);
									botaoSalvarAlteracoesCadastro.setEnabled(false);
									textCpf.setEnabled(true);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									Runner.LOGGER.setLevel(Level.WARNING);
									Runner.LOGGER.info("CPF JÁ CADASTRADO: "+textCpf.getText());
									textCpf.setText("");
									JOptionPane.showMessageDialog(null, "ERRO!!! CPF JÁ CADASTRADO!!");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "* CAMPOS DE PREENCHIMENTO OBRIGATÓRIO");
							}
						}
						else{
							//JOptionPane.showConfirmDialog(null, "Deseja cadastrar nova pessoa?", "ATENÇÃO!",JOptionPane.YES_NO_OPTION);
							Runner.LOGGER.setLevel(Level.WARNING);
							Runner.LOGGER.info("CPF INVÁLIDO: "+textCpf.getText());
							JOptionPane.showMessageDialog(null, "CPF Inválido!");
							textCpf.setText("");
						}
					}
					
				}
			});
		}
		
		return painel;
	}
	
	private void setPainelDataPessoa(){
		textCpf.setText(pessoa.getCpf());
		textNome.setText(pessoa.getNome());
		textRG.setText(pessoa.getRg());
		textAddress.setText(pessoa.getEndereco());
		textBairro.setText(pessoa.getBairro());
		textCity.setText(pessoa.getCidade());
		textNacionalidade.setText(pessoa.getNacionalidade());
		textNum.setText(pessoa.getNumero());
		if(pessoa.getSexo() == 'F')
			comboSexo.setSelectedIndex(1);
		else
			comboSexo.setSelectedIndex(0);
		
		
		//System.out.println("NOME: "+pessoa.getNome()+" VALOR: "+pessoa.getEstadoCivil());
		if(pessoa.getEstadoCivil() == null)
			comboEstadoCivil.setSelectedIndex(4);
		else if(pessoa.getEstadoCivil().equals("CASADO") || pessoa.getEstadoCivil().equals("CASADA"))
			comboEstadoCivil.setSelectedIndex(0);
		else if(pessoa.getEstadoCivil().equals("SOLTEIRO") || pessoa.getEstadoCivil().equals("SOLTEIRA"))
			comboEstadoCivil.setSelectedIndex(1);
		else if(pessoa.getEstadoCivil().equals("DIVORCIADO") || pessoa.getEstadoCivil().equals("DIVORCIADA"))
			comboEstadoCivil.setSelectedIndex(2);
		else if(pessoa.getEstadoCivil().equals("VIÚVO") || pessoa.getEstadoCivil().equals("VIÚVA"))
			comboEstadoCivil.setSelectedIndex(3);

		
		comboUf.setSelectedItem(pessoa.getEstado());
		
		imprimeLogDataPessoa();
		
	}
	
	private boolean validaCamposObrigatorios(){
		if(textCpf.getText().isEmpty() || textNome.getText().isEmpty() || textAddress.getText().isEmpty() ||
				textBairro.getText().isEmpty() || textCity.getText().isEmpty()){
			return false;
		}
		return true;
	}
	
	private void imprimeLogDataPessoa(){
		Runner.LOGGER.setLevel(Level.INFO);
		Runner.LOGGER.info("*******DADOS CADASTRAIS********"+lineSeparator+
				"CPF: "+textCpf.getText()+lineSeparator+ "NOME: "+textNome.getText()+lineSeparator+"RG: "+textRG.getText()+lineSeparator+"NACIONALIDADE: "
		+textNacionalidade.getText()+lineSeparator+"ENDEREÇO: "+textAddress.getText());
		//LOGGER.info("RG Nº :"+textRG.getText());
		//LOGGER.info("NACIONALIDADE: "+textNacionalidade.getText());
		
	}
	
	private boolean updateDataPessoa(){
		imprimeLogDataPessoa();
		if(!validaCamposObrigatorios()){
			return false;
		}
		
		pessoa.setNome(textNome.getText().toUpperCase());
		
		System.out.println("RG: "+textRG.getText()+"RG SIZE: "+textRG.getText().length());
		if(textRG.getText().length() == 0){
			//System.out.println("Entrou rg vazio");
			pessoa.setRg(null);
		}
		else{
			pessoa.setRg(textRG.getText());
		}
		
		pessoa.setNacionalidade(textNacionalidade.getText().toUpperCase());
		if(comboSexo.getSelectedIndex() == 0)
			pessoa.setSexo('M');
		else
			pessoa.setSexo('F');
		
		switch (comboEstadoCivil.getSelectedIndex()){//Pensar em alguma coisa para melhorar a escolha.
		case 0:
			if(pessoa.getSexo() == 'M')
				pessoa.setEstadoCivil("CASADO");
			else
				pessoa.setEstadoCivil("CASADA");
			break;
		case 1:
			if(pessoa.getSexo() == 'M')
				pessoa.setEstadoCivil("SOLTEIRO");
			else
				pessoa.setEstadoCivil("SOLTEIRA");
			break;
		case 2:
			if(pessoa.getSexo() == 'M')
				pessoa.setEstadoCivil("DIVORCIADO");
			else
				pessoa.setEstadoCivil("DIVORCIADA");
			break;
		case 3:
			if(pessoa.getSexo() == 'M')
				pessoa.setEstadoCivil("VIÚVO");
			else
				pessoa.setEstadoCivil("VIÚVA");
			break;
		
		default:
			pessoa.setEstadoCivil(null);
		}
		
		pessoa.setEndereco(textAddress.getText().toUpperCase());
		pessoa.setNumero(textNum.getText());
		pessoa.setBairro(textBairro.getText().toUpperCase());
		pessoa.setCidade(textCity.getText().toUpperCase());
		pessoa.setEstado(uf[comboUf.getSelectedIndex()]);
		
		return true;
	}
	
	
	private JPanel getPanelEmNomeDe(){
		painelEmNomeDe = new JPanel();
		
		 JLabel representado = new JLabel("Representado por:");
		 JButton procurar = new JButton("...");
		 
		 JLabel reprCPF = new JLabel("CPF:");
		 
		 
		 painelEmNomeDe.add(representado);
		 painelEmNomeDe.add(procurar);
		 
		 
		 procurar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controller.goShowPersonTable(true, true);
					Runner.LOGGER.info("Procurar ... pressionado");
					//while(controller.getJString() == null);
					//textRepresentante.setText(controller.getJString());
					
				}
			}); 
		 
		 textRepresentante.setEnabled(false);
		 textReprCPF.setEnabled(false);
		 painelEmNomeDe.add(textRepresentante);
		 painelEmNomeDe.add(reprCPF);
		 painelEmNomeDe.add(textReprCPF);
		 
		return painelEmNomeDe;
		
	}
	///*******
	
	public void myTest(boolean val1){
		valor = val1;
		
		if(valor == true){
			representante = controller.getJString();
			
			if(representante.getCpf().equals(pessoa.getCpf())){
				System.out.println("Como assim, representar a própria pessoa???");
				representante = null;
				JOptionPane.showMessageDialog(MakeRequest.this, "Não se pode representar ela mesma");
			}
			else{
			textRepresentante.setText(representante.getNome());
			textReprCPF.setText(representante.getCpf());
			}
		}
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
				Runner.LOGGER.setLevel(Level.INFO);
				Runner.LOGGER.info("BOTÃO  OK PRESSIONADO");
				dispose();//Fecha a Aplicação.
				controller.gerarWordCpf(pessoa, areaRequest.getText(), checkCND.isSelected(), checkRequerimento.isSelected(), checkCampoDeferido.isSelected(),representante);
				controller.init();
				
			}
		});
		
		JButton bt2 = new JButton("Cancelar");
		painelSubmit.add(bt2);
		
		bt2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Runner.LOGGER.setLevel(Level.INFO);
				Runner.LOGGER.info("BOTÃO  CANCELAR PRESSIONADO");
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
			painelRequest.add(getPanelEmNomeDe(), BorderLayout.CENTER);
			painelRequest.add(getPanelSubmit(), BorderLayout.SOUTH);
			//painelRequest.setBackground(new Color(100,0,0));
			//painelRequest.add(new JSeparator(SwingConstants.HORIZONTAL));
			
			
		}
		
		return painelRequest;
	}

}
