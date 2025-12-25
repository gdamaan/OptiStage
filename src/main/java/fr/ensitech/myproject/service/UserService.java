package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.PasswordHistory;
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.*;
import fr.ensitech.myproject.utils.PasswordHasher;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class UserService implements IUserService {

    private final IUserRepository userRepository = new UserRepository();
    private final IPasswordHistoryRepository historyRepository = new PasswordHistoryRepository();

    @Override
    public boolean subscribe(User user) throws Exception {
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return false;
        }

        String clearPassword = user.getPassword();
        String hashedPassword = PasswordHasher.hashPassword(clearPassword);
        user.setPassword(hashedPassword);

        String hashedResponse = PasswordHasher.hashSecretResponse(user.getResponse());
        user.setResponse(hashedResponse);

        user.setIsActive(false);
        user.setLastPasswordUpdate(new Date());

        userRepository.addUser(user);

        PasswordHistory history = new PasswordHistory();
        history.setOldPasswordHash(hashedPassword);
        history.setUser(user);
        history.setChangeDate(new Date());

        historyRepository.addHistory(history);

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
