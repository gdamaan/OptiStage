package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Question;
import fr.ensitech.myproject.repository.QuestionRepository;
import javax.ws.rs.ext.Provider;
import java.util.List;

public class QuestionController implements IQuestionController {
    private final QuestionRepository questionRepository = new QuestionRepository();

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}