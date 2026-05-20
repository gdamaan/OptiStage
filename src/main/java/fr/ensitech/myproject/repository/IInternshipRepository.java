package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Internship;

import java.util.List;

public interface IInternshipRepository {

    void addInternship(Internship internship) throws Exception;

    Internship getInternshipById(Long id) throws Exception;

    // Très utile pour retrouver le stage d'un étudiant à partir de la candidature d'origine
    Internship getInternshipByApplicationId(Long applicationId) throws Exception;

    void updateInternship(Internship internship) throws Exception;

    List<Internship> getInternshipsByStudent(Long studentId) throws Exception;

    void deleteInternship(Internship internship) throws Exception;

    List<Internship> getAllInternships() throws Exception;

    List<Internship> getInternshipsByProfessor(Long professorId) throws Exception;
}