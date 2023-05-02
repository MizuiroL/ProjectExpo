package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceEntityManagerFactory implements ExpoEntityManagerFactory {

	@Override
	public EntityManager getManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Expo");
		factory.getCache().evictAll();
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

}
