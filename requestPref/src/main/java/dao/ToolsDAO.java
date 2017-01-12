package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import entities.Tools;
import util.HibernateUtil;

public class ToolsDAO {
EntityManager em = HibernateUtil.getEntityManager();
	public Tools getTools(int id){

	return em.find(Tools.class, id);
}

	public void updateTools(Tools tools){
		em.getTransaction().begin();
		em.merge(tools);
		em.getTransaction().commit();
	}
	
	


}
