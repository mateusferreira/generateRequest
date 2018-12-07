package poi;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import entities.Pessoa;
import entities.Tools;

public class MakeWordDocument {
	
	private String capitalizeFirstWord(String word){
		word = word.toLowerCase();
		char []teste = word.toCharArray();
		//teste[0] = Character.toUpperCase(teste[0]);
		
		int x = 0;
		boolean flag = false;
		while(teste[x] == ' ') x++;
		teste[x] = Character.toUpperCase(teste[x]);
		x++;
		//System.out.println(x);
		for (; x < word.length(); x++){
			
			if (flag == true && teste[x] != ' '){
				flag = false;
				
				if(teste[x] == 'e' && teste[x+1] == ' ')continue;
				else if(teste[x] == 'd' && teste[x+1] == 'e' && teste[x+2] == ' ') continue;
				else if(teste[x] == 'd' && teste[x+1] == 'a' && teste[x+2] == ' ') continue;
				else teste[x] = Character.toUpperCase(teste[x]);
			}
			
			if(teste[x] == ' ')
				flag = true;
	
		}
		return String.valueOf(teste);
	}
	
	

	private static void setRun (XWPFRun run , String fontFamily , int fontSize, boolean bold) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setBold(bold);
    }
	
	public static void setTableAlignment(XWPFTable table, STJc.Enum justification) {
	    CTTblPr tblPr = table.getCTTbl().getTblPr();
	    CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : (CTJc) tblPr.addNewJc());
	    jc.setVal(justification);
	}
	
	public void geraReqCpf(Pessoa pessoa, String assunto, Tools tools, boolean areaDeferido, Pessoa representa){
		int tF = 12;
		int tabular = 1100;
		String letra = "Times New Roman";
		
		XWPFDocument document = new XWPFDocument();
		
		XWPFParagraph cabecalho = document.createParagraph();
		XWPFRun run = cabecalho.createRun();
		
		//System.out.println("TESTE TOOLS: "+tools.getPathFile());
		run.setText(tools.getPronome());
		run.addBreak();
		run.setText(tools.getNome());
		run.addBreak();
		run.setText(tools.getTratamento()+". "+tools.getCargo());
		run.addBreak();
		run.setText(tools.getCidadeEstado());
		run.setFontSize(12);
		run.setFontFamily("Times New Roman");
		run.setBold(true);
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		
		XWPFParagraph body = document.createParagraph();
		XWPFRun roda2 = body.createRun();
		XWPFRun roda = body.createRun();
		XWPFRun roda3 = body.createRun();

		roda2.setText(pessoa.getNome().toUpperCase());
		roda2.setBold(true);
		roda2.setFontSize(tF);
		roda2.setFontFamily(letra);
		roda.setText(", "+pessoa.getNacionalidade().toLowerCase());
		
		if(pessoa.getEstadoCivil() != null){
			roda.setText(", "+pessoa.getEstadoCivil().toLowerCase());
		}
		
		if (pessoa.getSexo() == 'M'){
			if(pessoa.getRg() != null){
				roda.setText(", portador do RG ");
				roda.setText(pessoa.getRg());
			}
			roda.setText(", inscrito no CPF nº ");
		}
		else{
			if(pessoa.getRg() != null){
				roda.setText(", portadora do RG ");
				roda.setText(pessoa.getRg());
			}
			roda.setText(", inscrita no CPF nº ");
		}
		roda.setText(pessoa.getCpf());
		///**********************
		//Arrumar issso..... 
		
		roda.setText(", residente na ");
		roda.setText(capitalizeFirstWord(pessoa.getEndereco()));
		roda.setText(", ");
		roda.setText("nº "+pessoa.getNumero()+", bairro "+capitalizeFirstWord(pessoa.getBairro())+", ");	
		roda.setText(capitalizeFirstWord(pessoa.getCidade())+" - "+pessoa.getEstado());
		
		if(representa != null){
			if(pessoa.getSexo() == 'F')
				roda.setText(", aqui representada por ");
			else
				roda.setText(", aqui representado por ");
			roda.setText(representa.getNome().toUpperCase());
		}
		
		roda.setText(", vem mui respeitosamente requerer de V. Sª.,");
		
		
		roda.setFontSize(tF);
		roda.setFontFamily(letra);
		roda.setBold(false);
		
		body.setAlignment(ParagraphAlignment.BOTH);
		body.setIndentationFirstLine(tabular);
		
		//String assunto = "baixa do ISSQN (Imposto sobre Serviço de qualquer natureza), na atividade na atividade de Motorista";
		roda3.setText(assunto);
		roda3.setText(".");
		
		roda3.setBold(true);
		roda3.setFontSize(tF);
		roda3.setFontFamily(letra);
		
		XWPFParagraph termos = document.createParagraph();
		XWPFRun runTermos = termos.createRun();
		runTermos.addBreak();
		runTermos.setText("Nestes Termos,");
		runTermos.addBreak();
		runTermos.addBreak();
		runTermos.setText("Pede Deferimento.");
		runTermos.addBreak();
		runTermos.addBreak();
		runTermos.setText("Gonçalves, ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy'.'");
		Date data = new Date();
		String t;
		t = sdf.format(data);
		runTermos.setText(t.toLowerCase());
		runTermos.setFontSize(tF);
		runTermos.setFontFamily(letra);
		runTermos.setBold(false);
		termos.setAlignment(ParagraphAlignment.LEFT);
		termos.setIndentationLeft(tabular);
		runTermos.addBreak();
		runTermos.addBreak();
		
		SimpleDateFormat formataData = new SimpleDateFormat("yyMMdd");
		
		String nameData = formataData.format(data);
		//System.out.println(nameData+" "+pessoa.getNome());
		
		XWPFParagraph sign = document.createParagraph();
		XWPFRun runSign = sign.createRun();
		runSign.setText("_____________________________");
		runSign.addBreak();
		
		if(representa != null){//Se representante.....
			runSign.setText(capitalizeFirstWord(representa.getNome()));
			runSign.addBreak();
			runSign.setText(representa.getCpf());
		}
		else
			runSign.setText(capitalizeFirstWord(pessoa.getNome()));
		
		runSign.addBreak();
		runSign.addBreak();
		runSign.addBreak();
		runSign.setFontFamily(letra);
		runSign.setFontSize(tF);
		runSign.setBold(false);
		sign.setAlignment(ParagraphAlignment.CENTER);
		
		if(areaDeferido){
			XWPFTable table = document.createTable();
		
			setTableAlignment(table,STJc.RIGHT);
		
			XWPFTableRow tableRowOne = table.getRow(0);
			
			XWPFParagraph caixa = tableRowOne.getCell(0).addParagraph();
			caixa.setAlignment(ParagraphAlignment.LEFT);
			
			XWPFRun runCaixa = caixa.createRun();
			setRun(runCaixa,"Bookman Old Style",10,false);
			runCaixa.setText("(  )Deferido");
			runCaixa.addBreak();
			runCaixa.setText("(  )Indeferido       __/__/__");
			runCaixa.addBreak();
			runCaixa.setText("Despacho:__________________");
			runCaixa.addBreak();
			runCaixa.setText("____________________________");
			
			XWPFParagraph boxSign = tableRowOne.getCell(0).addParagraph();
			boxSign.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun runBoxSign = boxSign.createRun();
			boxSign.setAlignment(ParagraphAlignment.CENTER);
			setRun(runBoxSign,letra,10,true);
			
			runBoxSign.setText("________________________");
			runBoxSign.addBreak();
			runBoxSign.setText(tools.getNome());
			runBoxSign.addBreak();
			runBoxSign.setText(tools.getCargo());
		}
		
		try{
			//String pathFile = "D:/Usuários/Mateus_2/Documents/SECRETARIA/Secretaria 2017/REQUERIMENTOS 2017/P.FISICA/";
			String pathFile = tools.getPathFile()+"/P.FISICA/";
			String nameFile = nameData +" "+pessoa.getNome()+".doc";
		
			System.out.println("NOME DO ARQUIVO: "+nameFile);
			FileOutputStream output = new FileOutputStream(pathFile + nameFile);	
			document.write(output);
			
			Desktop desktop = Desktop.getDesktop();
			File f =new File(pathFile + nameFile);
			
			if(f.exists())
				desktop.open(f);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
