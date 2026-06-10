package fr.ensitech.optistage.service;

import fr.ensitech.optistage.entity.PasswordHistory;
import fr.ensitech.optistage.entity.Role;
import fr.ensitech.optistage.entity.User;
import fr.ensitech.optistage.repository.*;
import fr.ensitech.optistage.utils.PasswordHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class UserService implements IUserService {

    private static final Logger logger = (Logger) LogManager.getLogger(UserService.class);

    private  IUserRepository userRepository = new UserRepository();
    private  IPasswordHistoryRepository historyRepository = new PasswordHistoryRepository();
    private  RoleRepository roleRepository = new RoleRepository();

    @Override
    public boolean subscribe(User user) throws Exception {
        logger.info("Tentative d'inscription du user avec mail : {}", user.getEmail());
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return false;
        }

        if (user.getRole() == null || user.getRole().getName() == null) {
            throw new Exception("Le rôle de l'utilisateur n'est pas spécifié.");
        }

        Role role = roleRepository.getRoleByName(user.getRole().getName());
        if (role == null) {
            throw new Exception("Le rôle spécifié n'existe pas dans la base de données : " + user.getRole().getName());
        }
        user.setRole(role);

        String clearPassword = user.getPassword();
        String hashedPassword = PasswordHasher.hashPassword(clearPassword);
        user.setPassword(hashedPassword);

        String hashedResponse = PasswordHasher.hashSecretResponse(user.getResponse());
        user.setResponse(hashedResponse);

        user.setIsActive(false);
        user.setLastPasswordUpdate(new Date());

        if (user.getEnterprise() != null) {
            user.getEnterprise().setUser(user);
        }

        // La sauvegarde en cascade (CascadeType.ALL) fera le reste sans erreur.
        userRepository.addUser(user);
        logger.info("User {} is subscribed with role {}", user.getEmail(), role.getName());

        PasswordHistory history = new PasswordHistory();
        history.setOldPasswordHash(hashedPassword);
        history.setUser(user);
        history.setChangeDate(new Date());

        historyRepository.addHistory(history);
        // --- DÉBUT DE LA LOGIQUE ASYNCHRONE ---
        java.util.concurrent.CompletableFuture.runAsync(() -> {
            try {
                // 1. Génération du token avec la méthode exacte de votre JwtUtil
                String validationToken = fr.ensitech.optistage.utils.JwtUtil.generateToken(user.getEmail(), role.getName());

                // 2. On redirige maintenant vers la future page React
                String validationLink = "http://localhost:5173/validate?token=" + validationToken;
                // 3. Contenu strictement professionnel pour le jury
                String subject = "OptiStage - Activation de votre compte";
                String content = "Bonjour " + user.getFirstname() + ",\n\n"
                        + "Votre compte a été créé avec succès.\n"
                        + "Afin de finaliser votre inscription et d'activer votre accès, veuillez cliquer sur le lien ci-dessous :\n\n"
                        + validationLink + "\n\n"
                        + "Si le lien n'est pas cliquable, veuillez le copier puis le coller dans la barre d'adresse de votre navigateur.\n\n"
                        + "Cordialement,\n"
                        + "L'équipe OptiStage.";

                // 4. Expédition
                EmailService.sendEmail(user.getEmail(), subject, content);

                logger.info("Email de validation expédié avec succès à {}", user.getEmail());
            } catch (Exception e) {
                logger.error("Échec lors de l'envoi de l'email à {}", user.getEmail(), e);
            }
        });
        return true;
    }

    @Override
    public void unsubscribe(String email) throws Exception {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is null or blank");
        }
        userRepository.setActivate(email, false);
    }

    @Override
    public void activate(Long userId) throws Exception {
        if (userId == null ||  userId <= 0) {
            throw new IllegalArgumentException("userId must be no null and > 0");
        }
        User user = userRepository.getUserById(userId);
        userRepository.setActivate(user.getEmail(), true);
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        logger.debug("getUserByEmail email : {}", email);
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is null or blank");
        }
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid id.");
        }
        return userRepository.getUserById(id);
    }

    @Override
    public void updateProfile(User user) throws Exception {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()
                || user.getFirstname() == null || user.getFirstname().isBlank()
                || user.getLastname() == null || user.getLastname().isBlank()
                || user.getBirthdate() == null) {

            throw new IllegalArgumentException("user parameters are incorrect");
        }
        User _user = userRepository.getUserByEmail(user.getEmail());
        if (_user == null) {
            throw new Exception("user to update not found");
        }
        _user.setFirstname(user.getFirstname());
        _user.setLastname(user.getLastname());
        _user.setBirthdate(user.getBirthdate());
        userRepository.updateUser(_user);
    }

    @Override
    public void updatePassword(Long userId,String newPassword) throws Exception {
        if (userId == null || newPassword == null || newPassword.isBlank()) {

            throw new IllegalArgumentException("parameters are incorrect");
        }
        String hashedPassword = PasswordHasher.hashPassword(newPassword);
        if (hashedPassword == null) {
            throw new Exception("Error during password hashing.");
        }
        userRepository.setPassword(userId, hashedPassword);
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userRepository.getAllUsers();
    }

    @Override
    public String getQuestion(String email, String oldPassword) throws Exception {
        if (email == null || email.isBlank()
                || oldPassword == null || oldPassword.isBlank()) {

            throw new IllegalArgumentException("parameters are incorrect");
        }

        Session session = null;
        try {
            session = HibernateConnector.getSession();
            User user = session.get(User.class, this.getUserByEmail(email).getId());
            if (!user.getPassword().equals(oldPassword)) {
                throw new Exception("Old password is incorrect");
            }
            return user.getQuestion().getQuestion();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
