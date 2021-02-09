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
import view.DialogOptionExcel;
import view.DialogSelectOptionCopyPast;
import view.EmpresaTableView;
import view.MakeBusinessRequest;
import view.MakeRequest;
import view.PessoaTableView;
import view.RelationListEmpresaTableView;
import view.ViewStart;

public class Controller {
	
	private String version = "VER.: 20200701";
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
	private RelationListEmpresaTableView relationList;
	private MakeBusinessRequest empresa;
	private Pessoa jstring;
	
	private Tools keepTools;
	private DialogChangeMajor changeMajor = new DialogChangeMajor();
	private DialogSelectOptionCopyPast changeCopyPast = new DialogSelectOptionCopyPast();
	private DialogOptionExcel optionExcel = new DialogOptionExcel();
	
	public void init(){
		//request = new MakeRequest(this);
		//request.init();
		viewStart = new ViewStart(this,"Gerenciador de Requerimentos "+version);
		viewStart.init();
		
	}
	
	public void goShowPersonTable(boolean cpfIsSelected, boolean representa){
		viewStart.setVisible(false);
		if(cpfIsSelected == true){//Vai pra tela de criação de requerimento Pessoa Física
			pessoaTableView = new PessoaTableView(this, "Tabela", representa);
			pessoaTableView.init(dao.getListPessoas());
			System.out.println("Aqui: "+getJString());
			
		}
		
		else{
			empresaTableView = new EmpresaTableView(this, "Tabela");
			empresaTableView.init(daoEmpresa.getListEmpresa());
		}
	
	}
	
	public void goShowRelationTable(){//Aqui será a tela de visualização das empresas.
		viewStart.setVisible(false);
		relationList = new RelationListEmpresaTableView(this, "Tabela");
		relationList.init(daoEmpresa.getListEmpresa());
		
	}
	
	public void goMakeRequest(Pessoa p){
		//System.out.println(p.getNome());
		//System.out.println("teste....");
		jstring = null;
		pessoaTableView.setVisible(false);
		request = new MakeRequest(this, "Cria Requerimento "+version,p);
		request.init();
		
	}
	
	public void goMakeRequest(Empresa empresa, Boolean relation){
		//System.out.println("CHEGOU AQUI EMPRESA........" +empresa.getRazao());
		//System.out.println("teste....");
		if(relation == false)
			empresaTableView.setVisible(false);
		else
			relationList.setVisible(false);
		
		businessRequest = new MakeBusinessRequest(this, "Cria Requerimento "+version,empresa);
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
	
	public void gerarWordCpf(Pessoa pessoa, String requere, boolean geraCND, boolean isGeraRequerimento, boolean flag, Pessoa representa){
				
		if(isGeraRequerimento == true)//Se for falso não vai criar o requerimento
			poi.geraReqCpf(pessoa, requere, keepTools, flag, representa);
		
		if(geraCND == true){
			String resp = JOptionPane.showInputDialog(null, "FINALIDADE DA CND");
			
			if(resp != null){
				poiCnd.geraWordPessoaCnd(pessoa, resp, keepTools);
				//System.out.println("Implementar CND");
			}
			
		}
	}
	
	public void gerarWordCnpj(Empresa empresa, String requere, boolean geraCND,boolean isGeraRequerimento, boolean flag){
		//TODO:
		if(isGeraRequerimento == true)
			poiBusiness.geraReqCnpj(empresa, requere, keepTools, flag);
		
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
	
	public void configCopyPast(Tools tools){
		updateTools (changeCopyPast.display(tools));
	}
	
	public void configExcel(Tools tools){
		//updateTools (optionExcel.display(tools)); *****implementar DAO
		updateTools (optionExcel.display(tools));
		//System.out.println("VOU IMPLEMENTAR AINDA");
	}
	
	public void setJString (Pessoa texto){
		
		jstring = texto;
		request.myTest(true);
	}
	
	public Pessoa getJString(){
		return jstring;
	}

}
