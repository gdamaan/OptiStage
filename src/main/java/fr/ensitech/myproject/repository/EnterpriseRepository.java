package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Enterprise;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class EnterpriseRepository implements IEnterpriseRepository {

    @Override
    public void addEnterprise(Enterprise enterprise) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(enterprise);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Enterprise getEnterpriseById(Long id) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.find(Enterprise.class, id);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Enterprise getEnterpriseByUserId(Long userId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            // Requête HQL pour trouver l'entreprise liée à l'ID de l'utilisateur
            Query<Enterprise> query = session.createQuery("from Enterprise e where e.user.id = :userId", Enterprise.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateEnterprise(Enterprise enterprise) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.update(enterprise);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void deleteEnterprise(Enterprise enterprise) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.remove(enterprise);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<Enterprise> getAllEnterprises() throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery("from Enterprise", Enterprise.class).list();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}