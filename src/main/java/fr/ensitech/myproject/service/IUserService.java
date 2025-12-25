package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * Inscrit un nouveau utilisateur.
     *
     * @param user : l'utilisateur à inscrire.
     * @return {@code true} si l'utilisateur a bien été inscrit, {@code false} si l'email de l'utilisateur à inscrire est déjà utilisé.
     * @throws Exception : Si erreur lors de la création de l'utilisateur.
     */
    boolean subscribe(User user) throws Exception;

    void unsubscribe(String email) throws Exception;
    void activate(Long userId) throws Exception;
    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id) throws Exception;
    void updateProfile(User user) throws Exception;
    void updatePassword(Long userId, String newPassword) throws Exception;
    List<User> getAllUsers() throws Exception;
    String getQuestion(String email, String oldPassword) throws Exception;
}
