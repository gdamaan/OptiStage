package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RoleRepository {
    public Role getRoleByName(String roleName) throws Exception {
        Session session = null;
        try {
            session = HibernateConnector.getSession();
            Query<Role> query = session.createQuery("from Role where name = :name", Role.class);
            query.setParameter("name", roleName);
            return query.uniqueResult();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}