package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.InternshipOffer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.RollbackException;
import java.util.List;

public class InternshipOfferRepository implements IInternshipOfferRepository {

    @Override
    public void addOffer(InternshipOffer offer) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(offer);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public InternshipOffer getOfferById(Long id) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.find(InternshipOffer.class, id);
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateOffer(InternshipOffer offer) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.update(offer);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void deleteOffer(InternshipOffer offer) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.remove(offer);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<InternshipOffer> getAllOffers() throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery("from InternshipOffer", InternshipOffer.class).list();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<InternshipOffer> getOffersByEnterprise(Long enterpriseId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery("from InternshipOffer o where o.enterprise.id = :entId", InternshipOffer.class)
                    .setParameter("entId", enterpriseId)
                    .list();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}