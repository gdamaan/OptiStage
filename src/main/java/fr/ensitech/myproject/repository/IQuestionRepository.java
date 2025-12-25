package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Question;

public interface IQuestionRepository {
    void addQuestion(Question question);
    Question getQuestionById(int id);
    void updateQuestion(Question question);
    void deleteQuestion(int id);
}
