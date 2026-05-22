package unit.fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Question;
import fr.ensitech.myproject.entity.Role;
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.HibernateConnector;
import fr.ensitech.myproject.repository.UserRepository;
import fr.ensitech.myproject.service.UserService;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserServiceIntegrationTest {

    private Session session;
    private Transaction tx;

    private UserService userService;
    private UserRepository userRepository;

    private User user;
    private Role role;

    @Before
    public void setUp() {
        // Forcer HibernateConnector à utiliser la config H2 de test
        System.setProperty("hibernate.config.file", "hibernate-test.cfg.xml");

        session = HibernateConnector.getTestInstance().openSession();
        tx = session.beginTransaction();

        userRepository = new UserRepository();
        userService = new UserService();

        // 1. Préparation du Rôle
        role = session.createQuery("from Role where name = 'Étudiant'", Role.class).uniqueResult();
        if (role == null) {
            role = new Role();
            role.setName("Étudiant");
            session.save(role);
        }

        // 2. Préparation de la Question (Obligatoire pour l'entité User)
        Question question = session.createQuery("from Question", Question.class).setMaxResults(1).uniqueResult();
        if (question == null) {
            question = new Question();
            question.setQuestion("Quel est le nom de votre premier animal de compagnie ?");
            session.save(question);
        }

        // 3. On valide l'insertion pour que les sessions de vos Repositories puissent les voir
        tx.commit();
        tx = session.beginTransaction(); // On rouvre la transaction pour la suite du test

        // 4. Construction de l'utilisateur de test
        user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@gmail.com")
                .password("JohnDoe1234!")
                .response("SecretResponse")
                .role(role)
                .question(question) // On n'oublie pas la question !
                .build();
    }

    @After
    public void tearDown() {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }

        // =================================================================
        // Nettoyage post-test
        // Les Repositories ayant commit de leur côté, un simple rollback
        // de la session de test ne suffit pas. Il faut purger manuellement.
        // =================================================================
        tx = session.beginTransaction();
        session.createQuery("delete from PasswordHistory").executeUpdate();
        session.createQuery("delete from User").executeUpdate();
        tx.commit();

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @SneakyThrows
    @Test
    public void shouldPersistUserInDatabase() {

        //GIVEN

        //WHEN
        boolean isSubscribed = userService.subscribe(user);

        //THEN
        assertTrue(isSubscribed);

        User userFromDb = userRepository.getUserByEmail(user.getEmail());
        assertNotNull(userFromDb);
        assertEquals(user.getFirstname(), userFromDb.getFirstname());
        assertEquals(user.getLastname(), userFromDb.getLastname());
        assertEquals(user.getEmail(), userFromDb.getEmail());
    }

    @SneakyThrows
    @Test
    public void shouldFailPersistUserIfEmailAlreadyExists() {

        //GIVEN
        //WHEN
        boolean firstTry = userService.subscribe(user);
        session.flush();

        boolean secondTry = userService.subscribe(user);
        session.flush();

        //THEN
        assertTrue(firstTry);
        assertFalse(secondTry); // Doit renvoyer false car l'email est déjà pris
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailPersistUserWithMissingRole() {

        //GIVEN
        user.setRole(null);

        //WHEN
        userService.subscribe(user);
        session.flush();

        //THEN (Exception levée)
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailPersistUserWithMissingRoleName() {

        //GIVEN
        role.setName(null);

        //WHEN
        userService.subscribe(user);
        session.flush();

        //THEN (Exception levée)
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void shouldFailPersistUserWithNonExistingRoleInDatabase() {

        //GIVEN
        role.setName("ROLE_INCONNU");

        //WHEN
        userService.subscribe(user);
        session.flush();

        //THEN (Exception levée)
    }

}