package fr.ensitech.myproject.main;

import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.IUserRepository;
import fr.ensitech.myproject.repository.UserRepository;

import java.util.Date;

public class MainUpdate {

    public static void main(String[] args) {

        IUserRepository userRepository = new UserRepository();

        try {
            User user = userRepository.getUserByEmail("albert@gmail.com");
            System.out.println("User avant mise à jour : " + user);
            user.setLastname("FRANCOIS");
            userRepository.updateUser(user);
            User updatedUser = userRepository.getUserByEmail("albert@gmail.com");
            System.out.println("User après mise à jour : " + updatedUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
