package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Internship;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class InternshipRepository implements IInternshipRepository {

    @Override
    public void addInternship(Internship internship) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getSession()) {
            transaction = session.beginTransaction();
            session.save(internship);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Erreur lors de la création du stage : " + e.getMessage(), e);
        }
    }

    @Override
    public Internship getInternshipById(Long id) throws Exception {
        try (Session session = HibernateConnector.getSession()) {
            return session.get(Internship.class, id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du stage par ID : " + e.getMessage(), e);
        }
    }

    @Override
    public Internship getInternshipByApplicationId(Long applicationId) throws Exception {
        try (Session session = HibernateConnector.getSession()) {
            Query<Internship> query = session.createQuery("FROM Internship i WHERE i.application.id = :appId", Internship.class);
            query.setParameter("appId", applicationId);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du stage via la candidature : " + e.getMessage(), e);
        }
    }

    @Override
    public void updateInternship(Internship internship) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getSession()) {
            transaction = session.beginTransaction();
            session.update(internship);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Erreur lors de la mise à jour du stage : " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteInternship(Internship internship) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getSession()) {
            transaction = session.beginTransaction();
            session.delete(internship);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Erreur lors de la suppression du stage : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Internship> getAllInternships() throws Exception {
        try (Session session = HibernateConnector.getSession()) {
            return session.createQuery("FROM Internship", Internship.class).list();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de tous les stages : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Internship> getInternshipsByProfessor(Long professorId) throws Exception {
        try (Session session = HibernateConnector.getSession()) {
            Query<Internship> query = session.createQuery("FROM Internship i WHERE i.professor.id = :profId", Internship.class);
            query.setParameter("profId", professorId);
            return query.list();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des stages du professeur : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Internship> getInternshipsByStudent(Long studentId) throws Exception {
        try (Session session = HibernateConnector.getSession()) {
            Query<Internship> query = session.createQuery(
                    "FROM Internship i WHERE i.application.student.id = :studentId",
                    Internship.class
            );
            query.setParameter("studentId", studentId);
            return query.list();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des stages de l'étudiant : " + e.getMessage(), e);
        }
    }
}