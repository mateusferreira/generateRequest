package poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import entities.Empresa;
import entities.Pessoa;
import entities.Tools;
import requestPref.requestPref.Runner;
import view.ViewStart;

public class MakeCND {
	
	//private String filePath = "D:/CNDs/cnd_base_copy.doc";
	private POIFSFileSystem fs = null; 
	private String textoNum = null;
	
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
	
	private String fitAddress(String address){
		while(address.length() >=78){
       	 address = (String)JOptionPane.showInputDialog(null, "*********************** PERMITIDO NO MÁXIMO 77 CARACTERES **********************",
					"TAMANHO EXCEDIDO: "+address.length() +" > 77 CARACTERES",
					JOptionPane.PLAIN_MESSAGE, null, null, address);
       }
       
       Runner.LOGGER.info("Campo Endereço com "+address.length()+" caracteres");
       
       return address;
	}
	
	public void geraWordEmpresaCnd(Empresa empresa, String finalidade, Tools tools){
		try {            
            fs = new POIFSFileSystem(new FileInputStream(tools.getPathFile()+"/base_GERAR_CND.doc"));            
            HWPFDocument doc = new HWPFDocument(fs);
         
            Range range = doc.getOverallRange();
            
            Date dt = empresa.getDataInicio();
            String dataFormada = "";
			if(dt != null){
				String data_form = "dd/MM/yyyy";
				SimpleDateFormat simple =  new SimpleDateFormat(data_form);
				dataFormada = simple.format(dt);
			}
			else{
				dataFormada = "";
				System.out.println("Data de início não informada!");
				Runner.LOGGER.setLevel(Level.INFO);
				Runner.LOGGER.info("Empresa com data de início não informada");
			}
			
			if(empresa.getNumero().equals("S/N") || empresa.getNumero().equals("S/Nº") || empresa.getNumero().equals("SN") || empresa.getNumero().equals("S/Nº"))
				textoNum = " ";
			else
				textoNum = " Nº ";
			
			
			String campoEndereco = empresa.getEndereco().toUpperCase() +textoNum +empresa.getNumero()+", BAIRRO "+empresa.getBairro().toUpperCase()+", "+empresa.getCidade().toUpperCase()+" - "+empresa.getEstado();
            range.replaceText("$P1","");
            range.replaceText("$P2","");
            range.replaceText("$P3","");
            range.replaceText("$P4", empresa.getRazao().toUpperCase());
            
            campoEndereco = fitAddress(campoEndereco);
           /* while(campoEndereco.length() >=78){
            	 campoEndereco = (String)JOptionPane.showInputDialog(null, "*********************** PERMITIDO NO MÁXIMO 77 CARACTERES **********************",
						"TAMANHO EXCEDIDO: "+campoEndereco.length() +" > 77 CARACTERES",
						JOptionPane.PLAIN_MESSAGE, null, null, campoEndereco);
            }
            */
            range.replaceText("$P5", campoEndereco);
        
            
            if(empresa.getAtividade() == null)
            	range.replaceText("$P6","");
            else
            	range.replaceText("$P6", empresa.getAtividade().toUpperCase());
            
            range.replaceText("$P7", dataFormada);
            
            if(empresa.getInscMunicipal() == null)
            	range.replaceText("$P8","");
            else
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
            
            if(pessoa.getNumero().equals("S/N") || pessoa.getNumero().equals("S/Nº") || pessoa.getNumero().equals("SN") || pessoa.getNumero().equals("S/Nº"))
				textoNum = " ";
            //else if(pessoa.getNumero().equals(""))
			else
				textoNum = " Nº ";
            
            String campoEndereco = pessoa.getEndereco().toUpperCase()+ textoNum +pessoa.getNumero()+", BAIRRO "+pessoa.getBairro().toUpperCase()+", "+pessoa.getCidade().toUpperCase()+" - "+pessoa.getEstado();

            range.replaceText("$P1", pessoa.getNome().toUpperCase());
            range.replaceText("$P2", pessoa.getCpf());
            
            campoEndereco = fitAddress(campoEndereco);
            
            range.replaceText("$P3", campoEndereco);
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
    		
            range.replaceText("$PY","GONÇALVES, "+t.toUpperCase());
            
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
