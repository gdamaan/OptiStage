package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Internship;

import java.util.List;

public interface IInternshipService {

    /**
     * Crée et enregistre un nouveau stage dans le système après avoir appliqué
     * les règles de sécurité et initialisé le statut de la convention.
     * @param internship : le stage contenant le dossier de candidature validé à enregistrer.
     * @throws Exception si le dossier de candidature est manquant ou si une erreur survient en base de données.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.InternshipService#getInternshipByApplicationId(Long)
     */
    void createInternship(Internship internship) throws Exception;

    /**
     * Récupère un stage à partir de son identifiant unique.
     * @param id : l'identifiant du stage à rechercher.
     * @return le stage correspondant à l'identifiant fourni.
     * @throws Exception si l'identifiant est invalide ou si le stage reste introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.InternshipService#getAllInternships()
     */
    Internship getInternshipById(Long id) throws Exception;

    /**
     * Recherche le stage associé à un dossier de candidature spécifique.
     * @param applicationId : l'identifiant de la candidature liée au stage.
     * @return le stage correspondant à la candidature, ou {@code null} si aucun stage n'a encore été généré.
     * @throws Exception si l'identifiant de la candidature est incorrect ou invalide.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.InternshipService#createInternship(Internship)
     */
    Internship getInternshipByApplicationId(Long applicationId) throws Exception;

    /**
     * Met à jour l'ensemble des informations et le suivi d'un stage existant.
     * @param internship : l'objet stage contenant les nouvelles données à persister.
     * @throws Exception si le stage ou son identifiant n'existe pas dans le système.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.InternshipService#createInternship(Internship)
     */
    void updateInternship(Internship internship) throws Exception;

    /**
     * Supprime définitivement un stage du système à partir de son identifiant.
     * @param id : l'identifiant du stage à détruire.
     * @throws Exception si l'identifiant est invalide ou si le stage est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    void deleteInternship(Long id) throws Exception;

    /**
     * Extrait la totalité des stages enregistrés dans l'application.
     * @return une {@link List} contenant l'intégralité des stages.
     * @throws Exception si une erreur technique survient lors de la récupération des données.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<Internship> getAllInternships() throws Exception;

    /**
     * Récupère la liste des stages affectés à un professeur référent spécifique.
     * @param professorId : l'identifiant unique du professeur encadrant.
     * @return une {@link List} des stages supervisés par ce professeur.
     * @throws Exception si l'identifiant du professeur est invalide.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<Internship> getInternshipsByProfessor(Long professorId) throws Exception;

    /**
     * Récupère les stages associés aux candidatures d'un étudiant particulier.
     * @param studentId : l'identifiant unique de l'étudiant concerné.
     * @return une {@link List} des stages de cet étudiant.
     * @throws Exception si l'identifiant de l'étudiant est invalide.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<Internship> getInternshipsByStudent(Long studentId) throws Exception;
}