package fr.ensitech.myproject.main;

import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.IUserRepository;
import fr.ensitech.myproject.repository.UserRepository;

import java.util.List;

public class MainGet {

    public static void main(String[] args) {

        IUserRepository userRepository = new UserRepository();

        try {
            User user = userRepository.getUserById(1L);
            System.out.println("User by Id : " + user);

            user = userRepository.getUserById(100L);
            System.out.println("User by Id : " + user);

            user = userRepository.getUserByEmail("albert@gmail.com");
            System.out.println("User by email : " + user);

            user = userRepository.getUserByEmail("coucou");
            System.out.println("User by email : " + user);

            List<User> users = userRepository.getAllUsers();
            System.out.println("All Users (avec boucle foreach ) :" );
            for (User u : users) {
                System.out.println(u);
            }

            users = userRepository.getAllUsers();
            System.out.println("All Users (avec foreach des expressions Lambda ) :" );
            users.forEach(u -> System.out.println(u));
            //users.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
