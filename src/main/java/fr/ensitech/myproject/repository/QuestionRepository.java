package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class QuestionRepository implements IQuestionRepository {

    @Override
    public void addQuestion(Question question) {
        Session session = HibernateConnector.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(question);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Question getQuestionById(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            Question question = session.get(Question.class, id);
            return question;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void updateQuestion(Question question) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnector.getSession();
            transaction = session.beginTransaction();
            session.update(question);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteQuestion(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnector.getSession();
            transaction = session.beginTransaction();
            Question question = session.get(Question.class, id);
            if (question != null) {
                session.delete(question);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
