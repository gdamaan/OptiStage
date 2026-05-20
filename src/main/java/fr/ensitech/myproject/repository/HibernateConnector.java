package fr.ensitech.myproject.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {

    // L'usine est instanciée une seule et unique fois au démarrage (Singleton)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Échec critique lors de l'initialisation du pool de connexions : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        // Ouvre simplement une session depuis l'usine existante, SANS créer de nouvelles connexions
        return sessionFactory.openSession();
    }
}