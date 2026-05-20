package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Internship;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

public interface IInternshipController {

    // 1. Recherche par ID direct
    Response getInternshipById(Long id);

    // 2. Recherche par dossier de candidature d'origine
    Response getInternshipByApplicationId(Long applicationId);

    // 3. Espace Étudiant : Récupérer ses propres stages
    Response getMyInternships(ContainerRequestContext crc);

    // 4. Espace Professeur : Récupérer les étudiants sous sa responsabilité
    Response getInternshipsByProfessorId(Long professorId);

    // 5. Espace Admin : Vue globale sur tous les stages de l'école
    Response getAllInternships();

    // 6. Mise à jour (Notes, Convention, Feedback)
    Response updateInternshipData(Long id, Internship internshipUpdate);

    // 7. Suppression (Anomalie ou annulation de stage)
    Response deleteInternship(Long id);
}