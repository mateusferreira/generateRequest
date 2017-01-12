package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Empresa;
import util.HibernateUtil;

public class EmpresaDAO {
	EntityManager em = HibernateUtil.getEntityManager();
	
	public Empresa getEmpresa(String cnpj){
		System.out.println("Chegou EmpresaDAO");
		
		
		return em.find(Empresa.class, cnpj);
	}
	
	public void updateEmpresa(Empresa empresa){
		em.getTransaction().begin();
		em.merge(empresa);
		em.getTransaction().commit();
	}
	
	public void insertEmpresa(Empresa empresa){
		em.getTransaction().begin();
		em.persist(empresa);
		em.getTransaction().commit();
	}
	
	public ArrayList<Empresa> getListEmpresa(){
		TypedQuery<Empresa> query = em.createQuery("from Empresa p order by p.razao",Empresa.class);
		List<Empresa> list = query.getResultList();
		
		return new ArrayList<Empresa>(list);
	}

}

