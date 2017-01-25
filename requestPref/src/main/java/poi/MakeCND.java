package poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import entities.Empresa;
import entities.Pessoa;
import entities.Tools;

public class MakeCND {
	
	//private String filePath = "D:/CNDs/cnd_base_copy.doc";
	private POIFSFileSystem fs = null; 
	
	private static void saveWord(HWPFDocument doc, Date data, String nomePessoa, Tools tools) throws FileNotFoundException, IOException{
		String pathFile = tools.getPathFile()+"/CNDs/";//TODO.....
        FileOutputStream out = null;
        
        SimpleDateFormat formataData = new SimpleDateFormat("yyMMdd");
		
		String nameData = formataData.format(data);
		String nameFile = nameData +" "+nomePessoa+".doc";
        try{
            out = new FileOutputStream(pathFile + nameFile);
            doc.write(out);
            Desktop desktop = Desktop.getDesktop();
			File f =new File(pathFile + nameFile);
			
			if(f.exists())
				desktop.open(f);
        }
        finally{
            out.close();
        }
    }
	
	public void geraWordEmpresaCnd(Empresa empresa, String finalidade, Tools tools){
		try {            
            fs = new POIFSFileSystem(new FileInputStream(tools.getPathFile()+"/base_GERAR_CND.doc"));            
            HWPFDocument doc = new HWPFDocument(fs);
         
            Range range = doc.getOverallRange();

            range.replaceText("$P1","");
            range.replaceText("$P2","");
            range.replaceText("$P3","");
            range.replaceText("$P4", empresa.getRazao().toUpperCase());
            range.replaceText("$P5",empresa.getEndereco().toUpperCase()+", "+empresa.getBairro().toUpperCase()+" "+empresa.getCidade().toUpperCase()+" - "+empresa.getEstado());
            range.replaceText("$P6", empresa.getAtividade().toUpperCase());
            range.replaceText("$P7","");
            range.replaceText("$P8",empresa.getInscMunicipal());
            range.replaceText("$PZ","");
            range.replaceText("$P9", empresa.getCnpj());
           
            range.replaceText("$PX", finalidade.toUpperCase());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy'.'");
    		Date data = new Date();
    		String t;
    		t = sdf.format(data);
    		
            range.replaceText("$PY","Gonçalves, "+t);
            
            saveWord(doc, data, empresa.getRazao(),tools);
            
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
		
	}
	
	public void geraWordPessoaCnd(Pessoa pessoa, String finalidade, Tools tools){
		     
        try {            
        	fs = new POIFSFileSystem(new FileInputStream(tools.getPathFile()+"/base_GERAR_CND.doc"));           
            HWPFDocument doc = new HWPFDocument(fs);
         
            Range range = doc.getOverallRange();

            range.replaceText("$P1", pessoa.getNome().toUpperCase());
            range.replaceText("$P2", pessoa.getCpf());
            range.replaceText("$P3",pessoa.getEndereco().toUpperCase()+", "+pessoa.getBairro().toUpperCase()+" - "+pessoa.getCidade().toUpperCase()+" - "+pessoa.getEstado());
            range.replaceText("$P4","");
            range.replaceText("$P5","");
            range.replaceText("$P6","");
            range.replaceText("$P7","");
            range.replaceText("$P8","");
            range.replaceText("$PZ","");
            range.replaceText("$P9","");
          
            range.replaceText("$PX", finalidade.toUpperCase());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy'.'");
    		Date data = new Date();
    		String t;
    		t = sdf.format(data);
    		
            range.replaceText("$PY","Gonçalves, "+t);
            
            saveWord(doc, data, pessoa.getNome(),tools);
            
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
		
	}

}
