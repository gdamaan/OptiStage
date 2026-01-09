package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Administrator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.RollbackException;

public class AdministratorRepository implements IAdministratorRepository {

    @Override
    public void addAdmin(Administrator admin) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(admin);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Administrator getAdminByLogin(String login) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            Query<Administrator> query = session.createQuery("from Administrator where login = :login", Administrator.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Administrator getAdminById(Long id) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.find(Administrator.class, id);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateAdmin(Administrator admin) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.update(admin);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void deleteAdmin(Administrator admin) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.remove(admin); // On retire l'objet du bunker
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}