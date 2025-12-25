package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;
import java.util.List;

public class UserRepository implements IUserRepository {

    @Override
    public void addUser(User user) throws Exception {

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();

        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            //Query<User> query = session.createNativeQuery("select * from user where email = :email", User.class);
            //Query<User> query = session.createQuery("from User u where u.email = :email", User.class);
            Query<User> query = session.createNamedQuery("User::findByEmail", User.class);
            query.setParameter("email", email);

            //return query.getSingleResult();
            return query.uniqueResult();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public User getUserById(Long id) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            //return session.get(User.class, id);
            return session.find(User.class, id);

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateUser(User user) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();

        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUser(User user) throws Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            session.remove(user);
            tx.commit();

        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();

            //Requête HQL (ou JPQL)
            //return session.createQuery("select u from User u", User.class).list();

            //Requête SQL native
            return session.createNativeQuery("select * from user", User.class).list();

            //Requête prédéfinie
            //return session.createNamedQuery("User::findAll", User.class).list();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void setActivate(String email, boolean active) throws Exception {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is null or blank");
        }

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            Query<User> query = session.createNamedQuery("User::findByEmail", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            if (user == null) {
                throw new NullPointerException("User not found");
            }
            user.setIsActive(active);
            session.update(user);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void setProfile(User user) throws Exception {
        if (user == null
                || user.getId() == null || user.getId() <= 0
                || user.getFirstname() == null || user.getFirstname().isBlank()
                || user.getLastname() == null || user.getLastname().isBlank()) {

            throw new IllegalArgumentException("Invalid user");
        }
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            User _user = session.get(User.class, user.getId());
            _user.setFirstname(user.getFirstname());
            _user.setLastname(user.getLastname());
            _user.setBirthdate(user.getBirthdate());
            session.update(_user);
            tx.commit();
        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void setPassword(Long userId,String newPassword) throws Exception {

        if (userId == null || userId <= 0
                || newPassword == null || newPassword.isBlank()) {

            throw new IllegalArgumentException("Invalid user id");
        }

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateConnector.getSession();
            tx = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setPassword(newPassword);

            // 1ère façon de faire
            //session.update(user);
            //tx.commit();

            // 2ème façon de faire
            Query query = session.createQuery("update User u set u.password = ?1 where u.id = ?2");
            query.setParameter(1, newPassword);
            query.setParameter(2, userId);
            query.executeUpdate();
            tx.commit();

        } catch (RollbackException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


}
