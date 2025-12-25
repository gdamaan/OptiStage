package fr.ensitech.myproject.main;

import fr.ensitech.myproject.entity.Question;
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.repository.IQuestionRepository;
import fr.ensitech.myproject.repository.IUserRepository;
import fr.ensitech.myproject.repository.QuestionRepository;
import fr.ensitech.myproject.repository.UserRepository;

import java.util.Date;

public class MainAddUser {

    public static void main(String[] args) {

        IQuestionRepository questionRepository = new QuestionRepository();
        Question question = questionRepository.getQuestionById(1);

        User user = new User();
        user.setFirstname("Simone");
        user.setLastname("DEBEAUVOIR");
        user.setEmail("simone@existentialiste.fr");
        user.setPassword("Existentialisme2.0");
        user.setBirthdate(new Date());
        user.setIsActive(false);
        user.setQuestion(question);
        user.setResponse("La condition humaine.");


        IUserRepository userRepository = new UserRepository();
        try {
            userRepository.addUser(user);
            System.out.println("User added successfully : " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
