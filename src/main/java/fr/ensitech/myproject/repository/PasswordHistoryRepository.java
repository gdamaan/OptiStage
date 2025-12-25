package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.PasswordHistory;
import fr.ensitech.myproject.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import java.util.List;

public class PasswordHistoryRepository implements IPasswordHistoryRepository {

    private static final int HISTORY_LIMIT = 5;

    @Override
    public void addHistory(PasswordHistory history) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(history);
            tx.commit();
        } catch (RollbackException | ConstraintViolationException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new Exception("Erreur lors de l'ajout de l'historique: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<String> getLatestHashes(Long userId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            // Récupère les 5 derniers hashs, triés par date de changement
            return session.createQuery(
                            "SELECT ph.oldPasswordHash FROM PasswordHistory ph " +
                                    "WHERE ph.user.id = :userId " +
                                    "ORDER BY ph.changeDate DESC", String.class)
                    .setParameter("userId", userId)
                    .setMaxResults(HISTORY_LIMIT)
                    .getResultList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Long getHistoryCount(Long userId) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            return session.createQuery(
                            "SELECT count(ph.id) FROM PasswordHistory ph WHERE ph.user.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteOldestHistory(Long userId) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();

            //  Trouver l'ID de l'enregistrement le plus ancien pour cet utilisateur
            Long oldestId = session.createQuery(
                            "SELECT ph.id FROM PasswordHistory ph " +
                                    "WHERE ph.user.id = :userId " +
                                    "ORDER BY ph.changeDate ASC", Long.class)
                    .setParameter("userId", userId)
                    .setMaxResults(1)
                    .getSingleResult();

            //  Supprimer l'enregistrement
            PasswordHistory oldest = session.get(PasswordHistory.class, oldestId);
            if (oldest != null) {
                session.delete(oldest);
            }
            tx.commit();
        } catch (RollbackException | ConstraintViolationException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new Exception("Erreur lors de la suppression de l'historique: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}