package fr.ensitech.myproject.main;

import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.IUserRepository;
import fr.ensitech.myproject.repository.UserRepository;

public class MainRemove {

    public static void main(String[] args) {

        IUserRepository userRepository = new UserRepository();

        try {
            User user = userRepository.getUserByEmail("albert@gmail.com");
            userRepository.deleteUser(user);
            user = userRepository.getUserByEmail("albert@gmail.com");
            System.out.println("User avec email albert@gmail.com : " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
