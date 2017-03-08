package control;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.EmpresaDAO;
import dao.PessoaDAO;
import dao.ToolsDAO;
import entities.Empresa;
import entities.Pessoa;
import entities.Tools;
import model.PessoaTableModel;
import poi.MakeCND;
import poi.MakeWordBusinessDocument;
import poi.MakeWordDocument;
import view.DialogChangeMajor;
import view.EmpresaTableView;
import view.MakeBusinessRequest;
import view.MakeRequest;
import view.PessoaTableView;
import view.ViewStart;

public class Controller {
	
	private MakeRequest request;
	private MakeBusinessRequest businessRequest;
	private ViewStart viewStart;
	private PessoaDAO dao = new PessoaDAO();
	private EmpresaDAO daoEmpresa = new EmpresaDAO();
	private ToolsDAO toolsdao = new ToolsDAO();
	private MakeWordDocument poi = new MakeWordDocument();
	private MakeCND poiCnd = new MakeCND();
	private MakeWordBusinessDocument poiBusiness = new MakeWordBusinessDocument();
	private PessoaTableView pessoaTableView;
	private EmpresaTableView empresaTableView;
	private MakeBusinessRequest empresa;
	
	private Tools keepTools;
	private DialogChangeMajor changeMajor = new DialogChangeMajor();
	
	public void init(){
		//request = new MakeRequest(this);
		//request.init();
		viewStart = new ViewStart(this,"Gerenciador de Requerimentos");
		viewStart.init();
		
	}
	
	public void goShowPersonTable(boolean cpfIsSelected){
		viewStart.setVisible(false);
		if(cpfIsSelected == true){//Vai pra tela de criação de requerimento Pessoa Física
			pessoaTableView = new PessoaTableView(this, "Tabela");
			pessoaTableView.init(dao.getListPessoas());
		}
		
		else{
			empresaTableView = new EmpresaTableView(this, "Tabela");
			empresaTableView.init(daoEmpresa.getListEmpresa());
		}
	}
	
	public void goMakeRequest(Pessoa p){
		//System.out.println(p.getNome());
		//System.out.println("teste....");
		pessoaTableView.setVisible(false);
		request = new MakeRequest(this, "Cria Requerimento",p);
		request.init();
		
	}
	
	public void goMakeRequest(Empresa empresa){
		//System.out.println(p.getNome());
		//System.out.println("teste....");
		empresaTableView.setVisible(false);
		businessRequest = new MakeBusinessRequest(this, "Cria Requerimento",empresa);
		businessRequest.init();
		
	}
	
	
	public Pessoa buscarPessoaNoBanco(String cpf){
		//System.out.println("CPF: "+cpf);
		return dao.getPessoa(cpf);
		
	}
	
	public void excluirPessoaBanco(Pessoa pessoa){
		dao.excluirPessoa(pessoa);
	}
	
	public void excluirEmpresaBanco(Empresa empresa){
		daoEmpresa.excluirEmpresa(empresa);
	}
	
	public void editarPessoa(Pessoa pessoa){
		dao.updatePessoa(pessoa);
	}
	
	public void editarEmpresa(Empresa empresa){
		daoEmpresa.updateEmpresa(empresa);
	}
	
	public void inserirNovaPessoa(Pessoa pessoa){
		dao.insertPessoa(pessoa);
	}
	
	public void inserirNovaEmpresa(Empresa empresa){
		daoEmpresa.insertEmpresa(empresa);
	}
	
	public void gerarWordCpf(Pessoa pessoa, String requere, boolean geraCND, boolean isGeraRequerimento){
		//request.setVisible(false);
		
		//System.out.println("DADOS: "+pessoa.getNome()+" - "+requere);
		if(isGeraRequerimento == true)//Se for falso não vai criar o requerimento
			poi.geraReqCpf(pessoa, requere, keepTools);
		
		if(geraCND == true){
			String resp = JOptionPane.showInputDialog(null, "FINALIDADE DA CND");
			
			if(resp != null){
				poiCnd.geraWordPessoaCnd(pessoa, resp, keepTools);
				//System.out.println("Implementar CND");
			}
			
		}
	}
	
	public void gerarWordCnpj(Empresa empresa, String requere, boolean geraCND,boolean isGeraRequerimento){
		//TODO:
		if(isGeraRequerimento == true)
			poiBusiness.geraReqCnpj(empresa, requere, keepTools);
		
		if(geraCND == true){
			String resp = JOptionPane.showInputDialog(null, "FINALIDADE DA CND");
			
			if(resp != null){
				poiCnd.geraWordEmpresaCnd(empresa, resp, keepTools);
				//System.out.println("Implementar CND");
			}
		}
	}
	
	public void updateTools(Tools tools){
		toolsdao.updateTools(tools);
	}
	
	public Tools getTools(int id){
		
		return toolsdao.getTools(id);
		
	}
	
	public void guardaTools(Tools tools){
		keepTools = tools;
	}
	
	public void trocarPrefeito(Tools tools){
		updateTools(changeMajor.display(tools));
	}

}
