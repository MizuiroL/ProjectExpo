package service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Event;
import model.Exhibit;
import model.Exhibitor;
import model.Expo;
import model.ExpoManager;
import model.FixedExhibitArea;
import model.Ticket;
import model.Visitor;

public interface ExpoEntityManagerFactory {
	public default EntityManager getManager() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Expo.class);
		configuration.addAnnotatedClass(Exhibit.class);
		configuration.addAnnotatedClass(Exhibitor.class);
		configuration.addAnnotatedClass(FixedExhibitArea.class);
		configuration.addAnnotatedClass(ExpoManager.class);
		configuration.addAnnotatedClass(Ticket.class);
		configuration.addAnnotatedClass(Visitor.class);
		configuration.addAnnotatedClass(Event.class);
		
		/* Create session factory */
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		
		/* Initialize session object */
		Session session = sessionFactory.openSession();
		
		EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
		return entityManager;
	}
}
