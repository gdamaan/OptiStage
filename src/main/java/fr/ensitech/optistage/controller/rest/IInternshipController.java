package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.Internship;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

public interface IInternshipController {

    /**
     * Récupère les détails complets d'un stage spécifique à partir de son identifiant unique.
     * @param id : l'identifiant unique du stage recherché.
     * @return un objet {@link Response} contenant le stage trouvé (statut 200) ou une erreur 404 si aucun stage ne correspond.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getInternshipById(Long id);

    /**
     * Récupère le stage généré à partir d'un dossier de candidature d'origine spécifique.
     * @param applicationId : l'identifiant unique de la candidature validée.
     * @return un objet {@link Response} contenant le stage correspondant.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getInternshipByApplicationId(Long applicationId);

    /**
     * Espace Étudiant : Extrait l'ensemble des stages associés à l'étudiant actuellement connecté à la session.
     * @param crc : le contexte de la requête HTTP permettant d'intercepter et d'extraire les informations de l'appelant.
     * @return un objet {@link Response} contenant la liste des stages de l'étudiant authentifié.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getMyInternships(ContainerRequestContext crc);

    /**
     * Espace Enseignant : Récupère la liste des stages des étudiants placés sous la responsabilité directe d'un professeur référent.
     * @param professorId : l'identifiant unique de l'enseignant encadrant.
     * @return un objet {@link Response} contenant les dossiers de suivi des stages supervisés.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getInternshipsByProfessorId(Long professorId);

    /**
     * Espace Admin : Fournit une vue globale, centralisée et exhaustive sur l'intégralité des stages de l'école.
     * @return un objet {@link Response} contenant la liste complète de tous les stages enregistrés dans le système.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getAllInternships();

    /**
     * Met à jour les données d'un stage en cours ou finalisé (notations, validation de convention, retours et feedbacks).
     * @param id : l'identifiant unique du stage à modifier.
     * @param internshipUpdate : l'entité contenant les nouvelles valeurs à appliquer pour la mise à jour.
     * @return un objet {@link Response} confirmant le succès de l'opération de mise à jour.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response updateInternshipData(Long id, Internship internshipUpdate);

    /**
     * Supprime définitivement un stage du système en cas d'anomalie majeure ou d'annulation officielle de la convention.
     * @param id : l'identifiant unique du stage à révoquer.
     * @return un objet {@link Response} confirmant la suppression (statut 204 No Content).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response deleteInternship(Long id);
}