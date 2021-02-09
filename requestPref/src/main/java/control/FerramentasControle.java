package control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import entities.Empresa;

public class FerramentasControle {
	
	//TENTAR FUTURAMENTE CRIAR ENUM AO INVÉS DE STRING.
	private String[] modTexto = {"FALTA DOC", "AGUARDANDO", "DISPENSADO", "VENCIDO", "PROTOCOLO", "PREFEITURA", "COPASA", "OK", "N/A"};
	private String sistemaCores;
	private int codStatusEmpresa = 0; //Se for 1 = criando | 2 = isento | 3 = baixado | 4 = regular;
	public FerramentasControle(){
		
	}
	
	public String setDataNomeArquivo(){
		
		Date data = new Date();
		SimpleDateFormat formataData = new SimpleDateFormat("yyMMdd");
		
		String nameData = formataData.format(data);
		
		return nameData;
	}
	
	public String setDataPorExtenso(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy'.'");
		Date data = new Date();
		String t;
		t = sdf.format(data);
		
		return t;
	}
	
	public boolean verificaSeEData(String texto){//Metodo para verificar se o texto contem o formato de data XX/XX/XXXX
		char temp;
		int  i;
		if(texto.equals(""))// Se a string for vazia retorna falso.
			return false;
		
		else{
			
			for(i = 0; i < 10; i++){ // vou testando caracter por caracter para verificar se todos  são números.
				temp = texto.charAt(i);//aqui eu pelo o caracter específico.
				
				if(i == 2 || i == 5){ // Aqui são as barras de separação da data. (xx/xx/xxxx).Código ANSCII para a barra é 47.
					if(temp == 47) continue;
					else{
						//JOptionPane.showMessageDialog(null, "Formato de Data: XX/XX/XXXX");
						return false;
					}
				}
				
				else if(temp < 48 || temp > 57)// O código ANSI para o nº 0 é 48, 1 é 49 e assim por diante até o nº 9 - 57.
					return false; // se não o caracter não for um número não me interessa tratar, por isso retorna falso. Se o primeiro for número o restante também é. Não vou tratar os outros.
			}	
		}
		
		return true;
	}
	
	
	public boolean verificaData(String texto, boolean msg){
			
			
			if(verificaSeEData(texto) == false)
				return false;
			
			else{
			//***TRABALHANDO COM DATAS:
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			String t = sdf.format(today);
			Date hoje = null;
			Date sistema = null;
			try {
				hoje = sdf.parse(t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String temporario = texto.substring(6, 10);
			t = temporario + "-";
			temporario = texto.substring(3, 5);
			t = t+ temporario + "-";
			t = t + texto.substring(0,2);
			
			try {
				sistema = sdf.parse(t);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(hoje.compareTo(sistema) > 0){// Se hoje for depois de data já passada. Ou seja se VENCEU.... OU SEJA RETORNA FALSO.
				if(msg)
					JOptionPane.showMessageDialog(null, "DATA JÁ VENCIDA. USAR A PALAVRA 'VENCIDA' AO INVÉS DE DATA");
				return false;
			}
			
			else if(hoje.compareTo(sistema) < 0)//Se hoje for antes de data futura. Ou seja ainda está no Prazo.
				return true;
			
			else if(hoje.compareTo(sistema) == 0)//Vence hoje. Mas hoje não é vencido ainda. Então tá OK.
				return true;
			
			//System.out.println("Data: " +t);//imprime o código ANSI do caracter.
		}
		
		return false;
	}
	
	public boolean verificaStatusEmpresa(Empresa p){//Retorna TRUE se tem pendencia:
		
		if(p.getStatus().equals("CRIANDO") || p.getInscMunicipal() == null){
			sistemaCores = "CRIANDO";
			codStatusEmpresa = 1;
			return false;
		}
			
		else if (p.getInscMunicipal().equals("ISENTO")){ 
			sistemaCores = "ISENTO";
			codStatusEmpresa = 2;
			return false;
		}
		else if (p.getStatus().equals("BAIXADO")){
			sistemaCores = p.getStatus();
			codStatusEmpresa = 3;
			return false;
		}
		
		else if(verificaPendencia(p.getBombeiro()) == false ||verificaPendencia(p.getAgua()) == false 
				|| verificaPendencia(p.getCadastur())== false || verificaPendencia(p.getLenha()) == false || verificaPendencia(p.getCopam()) == false){
			codStatusEmpresa = 0;
			return true;
		}
		
		else{
			codStatusEmpresa = 4;
			return false;
		}
	}
	
	
	public boolean verificaPendencia(String texto){//FILTRAR AS PALAVRAS CHAVES
		
		
		if(verificaSeEData(texto) == true){//......SE o texto for uma data válida....
			
			if(verificaData(texto, false) == true){ // se a data estiver no prazo.
				sistemaCores = "WHITE";
				return true; // retorno positivo.
			}
			else{
				sistemaCores = "PENDENTE";
				return false; // a data já está vencida;
			}
		}
		
		else{//.... SENÃO for data, é texto. Trataremos...
			
			
			//modTexto = {"FALTA DOC", "AGUARDANDO", "DISPENSADO", "VENCIDO", "PROTOCOLO", "PREFEITURA", "COPASA", "OK", "N/A"};
			
			if(texto.equals(modTexto[0]) || texto.equals(modTexto[3])){//Os 2 textos que geram pendência. Texto comum para todos.
				sistemaCores = "PENDENTE";
				return false;
			}
			
			else if(texto.equals(modTexto[2]) || texto.equals(modTexto[1]) || texto.equals(modTexto[4]) || texto.equals(modTexto[5]) || texto.equals(modTexto[6]) ||
					texto.equals(modTexto[7]) || texto.equals(modTexto[8])){
				sistemaCores = "REGULAR";
				return true;
			}
			
			else{//Se entrou aqui tem algo de errado no meu filtro, está deixando passar alguma coisa.
				//System.out.println("ERRO: NÃO DEVERIA TER ENTRADO AQUI.: file FerramentasControle - verificaPendenciaBombeiro(): (" + texto +")");
				sistemaCores = "PENDENTE";
				return false;
			}
		}
		
	}
	
	//********************VERIFICA SE DADOS DE ENTRADA ESTÃO DENTRO DO PADRÃO****************
	//******************* USADO NO CADASTRO OU ATUALIZÃO DE EMPRESA************************
	public boolean testaDadosEntrada(String texto[]){//FILTRAR AS PALAVRAS CHAVES
			
			int i;
			
			int var[] = {0,0,0,0,1}; //1 - erro na data  \ 2- erro no texto.
			//BOMBEIRO - AGUA - CADASTUR - COPAM - VARIAVEL DE CONTROLE SE FOR 1 - VERDADEIRO \ 0 FALSO;
			
			for(i = 0; i<4; i++){
				
				if(verificaSeEData(texto[i]) == true){//......SE o texto for uma data válida....
					
					if(verificaData(texto[i], false) == false){// se a data for vencida.
						var[i] = 1; // seta a variavel como data
						var[4] = 0; //seta o controle para falso;
					}
				}
				
				else{//.... SENÃO for data, é texto. Trataremos...
					
					
					//modTexto = {"FALTA DOC", "AGUARDANDO", "DISPENSADO", "VENCIDO", "PROTOCOLO", "PREFEITURA", "COPASA", "OK", "N/A"};
					
					if(texto[i].equals(modTexto[0]) || texto[i].equals(modTexto[3]) || texto[i].equals(modTexto[2]) || texto[i].equals(modTexto[1]) ||
							texto[i].equals(modTexto[4]) || texto[i].equals(modTexto[5]) || texto[i].equals(modTexto[6]) || texto[i].equals(modTexto[7]) || texto[i].equals(modTexto[8])){
						continue;
					}
					
					else{//Se entrou aqui tem algo de errado no meu filtro, está deixando passar alguma coisa.
						//System.out.println("ERRO: NÃO DEVERIA TER ENTRADO AQUI.: file FerramentasControle - verificaPendenciaBombeiro(): (" + texto +")");
						var[i] = 2; // seta a variavel como texto;
						var[4] = 0; //seta o controle para falso;
						//System.out.println("ERRO: "+texto[i]);
					}
				}
			}
			//**************FAZER AGORA O TRATAMENTO PARA OBTER AS MENSAGENS DE ERRO.	
			if(var[4] == 1) // não foi encontrado erro algum.
				return true;
			
			else if(var[0] == 1 || var[1] == 1 || var[2] == 1 || var[3] == 1)//Erro no formato da data
				JOptionPane.showMessageDialog(null, "DATA VENCIDA. USAR A PALAVRA 'VENCIDO' AO INVÉS");

			else if(var[0] == 2 || var[1] == 2 || var[2] == 2 || var[3] == 2)
				JOptionPane.showMessageDialog(null, "***PALAVRA INVÁLIDA***\n"
						+ "USAR AS SEGUINTES PALVARAS:\n "
						+ "BOMBEIRO: DISPENSADO, FALTA DOC, AGUARDANDO, VENCIDO, PROTOCOLO, N/A\n"
						+"ÁGUA: COPASA, PREFEITURA, FALTA DOC, VENCIDO, N/A\n"
						+"CADASTUR: FALTA DOC, VENCIDO, N/A\n"
						+"COPAM: FALTA DOC, DISPENSADO, N/A");
			else
				JOptionPane.showMessageDialog(null, "ERRO DESCONHECIDO");
			
			return false;
		}
	
	//*************************************************************************************
	//TESTANDO O STATUS DAS EMPRESAS:
	public boolean testaEmpresaPendente(Empresa p){
		

		 if(verificaStatusEmpresa(p) == true){
			//System.out.println("ENTROIUY.............");
			return true;
		}
	
		
		else{
			//System.out.println("REGULAR..." + p.getRazao());
			return false;
		}
	}
	
	public String getSistemaCores(){
		return sistemaCores;
	}
	
	public int getCodStatusEmpresa(){
		return codStatusEmpresa;
	}


}
