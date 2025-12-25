package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.User;

import java.util.List;


public interface IUserRepository {

    void addUser(User user) throws Exception;
    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(User user) throws Exception;
    List<User> getAllUsers() throws Exception;
    void setActivate(String email, boolean active) throws Exception;
    void setProfile(User user) throws Exception;
    void setPassword(Long userId,String newPassword) throws Exception;

}
