package poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import entities.Pessoa;

public class MakeCND {
	
	private static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream("D:\\teste.doc");
            doc.write(out);
        }
        finally{
            out.close();
        }
    }
	
	public void geraWordPessoaCnd(Pessoa pessoa, String finalidade){
		
		String filePath = "D:\\cnd_base_copy.doc";
        POIFSFileSystem fs = null;      
        try {            
            fs = new POIFSFileSystem(new FileInputStream(filePath));            
            HWPFDocument doc = new HWPFDocument(fs);
         
            Range range = doc.getOverallRange();

          
            
            range.replaceText("$P1", pessoa.getNome().toUpperCase());
            range.replaceText("$P2", pessoa.getCpf());
            range.replaceText("$P3",pessoa.getBairro().toUpperCase()+" "+pessoa.getCidade().toUpperCase()+" - "+pessoa.getEstado());
            range.replaceText("$P4","");
            range.replaceText("$P5","");
            range.replaceText("$P6","");
            range.replaceText("$P7","");
            range.replaceText("$P8","");
            range.replaceText("$PZ","");
            range.replaceText("$P9","");/*
            range.replaceText("$P1","");
            range.replaceText("$P2","");
            range.replaceText("$P3","");
            range.replaceText("$P4","CAMPEÃO COMÉRCIO DE MATERIAIS LTDA");
            range.replaceText("$P5","RUA JOAQUIM FERREIRA DE SOUZA, N°125 CENTRO");
            range.replaceText("$P6","COMERCIO VAREJISTA DE MATERIAIS DE CONSTRUÇÃO");
            range.replaceText("$P7","15/01/2000");
            range.replaceText("$P8","2314");
            range.replaceText("$PZ","");
            range.replaceText("$P9","13.594.756/0001-58");*/
            
            range.replaceText("$PX", finalidade.toUpperCase());
            range.replaceText("$PY","Gonçalves, 24 de janeiro de 2017.");
            
            saveWord(filePath, doc);
            
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
		
	}

}
