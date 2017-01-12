package util;

import javax.persistence.*;


public class HibernateUtil 
{
	private static final EntityManagerFactory emFactory = buildSessionFactory();

	private static EntityManagerFactory buildSessionFactory() 
	{
		try 
		{
			return Persistence.createEntityManagerFactory("Persistence_Unit");
		
			// Create
															// Factory
			//NOME IGUAL AO XML
		} 
		catch (Throwable e) 
		{
			// Make sure you log the exception , as it might be swallowed
			System.err.println(" Error creating EntityManagerFactory ." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static EntityManager getEntityManager() 
	{
		return emFactory.createEntityManager();
	}
}
