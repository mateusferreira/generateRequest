package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Pessoa;
import util.HibernateUtil;

public class PessoaDAO {
	EntityManager em = HibernateUtil.getEntityManager();
	
	public Pessoa getPessoa(String cpf){
		System.out.println("Chegou PessoaDAO");
		
		
		return em.find(Pessoa.class, cpf);
	}
	
	public void updatePessoa(Pessoa pessoa){
		em.getTransaction().begin();
		em.merge(pessoa);
		em.getTransaction().commit();
	}
	
	public void insertPessoa(Pessoa pessoa){
		em.getTransaction().begin();
		em.persist(pessoa);
		em.getTransaction().commit();
	}
	
	public ArrayList<Pessoa> getListPessoas(){
		TypedQuery<Pessoa> query = em.createQuery("from Pessoa p order by p.nome",Pessoa.class);
		List<Pessoa> list = query.getResultList();
		
		return new ArrayList<Pessoa>(list);
	}

}
