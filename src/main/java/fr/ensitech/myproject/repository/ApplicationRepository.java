package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Application;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.RollbackException;
import java.util.List;

public class ApplicationRepository implements IApplicationRepository {

    @Override
    public void addApplication(Application application) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(application);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Application getApplicationById(Long id) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.find(Application.class, id);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateApplication(Application application) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.update(application);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void deleteApplication(Application application) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.remove(application);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<Application> getApplicationsByStudent(Long studentId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery("from Application a where a.student.id = :sId", Application.class)
                    .setParameter("sId", studentId)
                    .list();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<Application> getApplicationsByOffer(Long offerId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery("from Application a where a.offer.id = :oId", Application.class)
                    .setParameter("oId", offerId)
                    .list();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}