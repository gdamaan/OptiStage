package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Internship;

import java.util.List;

public interface IInternshipService {

    void createInternship(Internship internship) throws Exception;

    Internship getInternshipById(Long id) throws Exception;

    Internship getInternshipByApplicationId(Long applicationId) throws Exception;

    void updateInternship(Internship internship) throws Exception;

    void deleteInternship(Long id) throws Exception;

    List<Internship> getAllInternships() throws Exception;

    List<Internship> getInternshipsByProfessor(Long professorId) throws Exception;

    List<Internship> getInternshipsByStudent(Long studentId) throws Exception;
}