package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Internship;
import fr.ensitech.myproject.repository.IInternshipRepository;
import fr.ensitech.myproject.repository.InternshipRepository;

import java.util.List;

public class InternshipService implements IInternshipService {

    private final IInternshipRepository internshipRepository;

    public InternshipService() {
        this.internshipRepository = new InternshipRepository();
    }

    @Override
    public void createInternship(Internship internship) throws Exception {
        if (internship == null || internship.getApplication() == null) {
            throw new Exception("Impossible de créer un stage sans dossier de candidature valide.");
        }

        // Sécurité réglementaire : On initialise toujours le statut de la convention
        internship.setConventionStatus("EN_COURS");

        this.internshipRepository.addInternship(internship);
    }

    @Override
    public Internship getInternshipById(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Identifiant de stage invalide.");
        }
        return this.internshipRepository.getInternshipById(id);
    }

    @Override
    public Internship getInternshipByApplicationId(Long applicationId) throws Exception {
        if (applicationId == null || applicationId <= 0) {
            throw new Exception("Identifiant de candidature invalide.");
        }
        return this.internshipRepository.getInternshipByApplicationId(applicationId);
    }

    @Override
    public void updateInternship(Internship internship) throws Exception {
        if (internship == null || internship.getId() == null) {
            throw new Exception("Impossible de mettre à jour un stage inexistant.");
        }
        this.internshipRepository.updateInternship(internship);
    }

    @Override
    public void deleteInternship(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Identifiant de stage invalide pour la suppression.");
        }
        Internship internship = this.internshipRepository.getInternshipById(id);
        if (internship == null) {
            throw new Exception("Stage introuvable, suppression annulée.");
        }
        this.internshipRepository.deleteInternship(internship);
    }

    @Override
    public List<Internship> getAllInternships() throws Exception {
        return this.internshipRepository.getAllInternships();
    }

    @Override
    public List<Internship> getInternshipsByProfessor(Long professorId) throws Exception {
        if (professorId == null || professorId <= 0) {
            throw new Exception("Identifiant de professeur référent invalide.");
        }
        return this.internshipRepository.getInternshipsByProfessor(professorId);
    }

    @Override
    public List<Internship> getInternshipsByStudent(Long studentId) throws Exception {
        if (studentId == null || studentId <= 0) {
            throw new Exception("Identifiant d'étudiant invalide.");
        }
        return this.internshipRepository.getInternshipsByStudent(studentId);
    }
}