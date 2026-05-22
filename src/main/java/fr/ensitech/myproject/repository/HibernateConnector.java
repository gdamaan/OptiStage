package fr.ensitech.myproject.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Design pattern "Singleton"
public final class HibernateConnector {

	private static HibernateConnector instance;
	private final SessionFactory sessionFactory;

	// Constructeur privé blindé avec votre ancienne gestion d'erreur
	private HibernateConnector(String configFile) throws HibernateException {
		try {
			sessionFactory = new Configuration().configure(configFile).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Échec critique lors de l'initialisation du pool de connexions : " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	// Récupérer l'instance classique
	public static HibernateConnector getInstance() throws HibernateException {
		if (instance == null) {
			instance = new HibernateConnector("hibernate.cfg.xml");
		}
		return instance;
	}

	// Singleton pour les tests (H2)
	public static HibernateConnector getTestInstance() {
		if (instance == null) {
			instance = new HibernateConnector("hibernate-test.cfg.xml");
		}
		return instance;
	}

	// Ouvrir une nouvelle session à chaque appel (Méthode de la nouvelle version)
	public Session openSession() {
		return sessionFactory.openSession();
	}

	// =====================================================================
	// PONT DE RÉTROCOMPATIBILITÉ (Votre ancienne méthode indispensable)
	// =====================================================================
	public static Session getSession() {
		// Appelle automatiquement le Singleton pour fournir la session
		// Cela évite de devoir réécrire tous vos fichiers Repository
		return getInstance().openSession();
	}

	// Fermeture propre du pool de connexions
	public void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}