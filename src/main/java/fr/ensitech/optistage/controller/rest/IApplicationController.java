package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.ApplicationDto;
import javax.ws.rs.core.Response;

public interface IApplicationController {

    /**
     * Soumet une nouvelle candidature étudiante pour une offre de stage spécifique.
     * @param dto : l'objet DTO contenant les informations de la candidature (lettre de motivation, ID de l'offre, ID de l'étudiant, etc.).
     * @return un objet {@link Response} confirmant la création (statut 201) ou signalant une erreur de validation des données (statut 400).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response createApplication(ApplicationDto dto);

    /**
     * Récupère les détails complets d'une candidature spécifique à partir de son identifiant unique.
     * @param id : l'identifiant unique de la candidature recherchée.
     * @return un objet {@link Response} contenant la candidature sous forme de DTO (statut 200), ou une erreur 404 si elle est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getApplicationById(Long id);

    /**
     * Extrait l'historique complet des candidatures soumises par un étudiant spécifique.
     * Idéal pour alimenter le tableau de bord personnel de l'étudiant (suivi des envois).
     * @param studentId : l'identifiant unique de l'étudiant concerné.
     * @return un objet {@link Response} contenant la liste de ses candidatures.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getApplicationsByStudentId(Long studentId);

    /**
     * Récupère l'ensemble des candidatures reçues pour une offre de stage précise.
     * Méthode sécurisée nécessitant de valider l'identité du demandeur pour s'assurer qu'il est bien le recruteur de l'offre.
     * @param offerId : l'identifiant de l'offre de stage ciblée.
     * @param token : le jeton d'authentification (JWT) permettant de vérifier les droits d'accès.
     * @return un objet {@link Response} contenant la liste des candidatures associées à cette offre.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getApplicationsByOfferId(Long offerId, String token);

    /**
     * Met à jour le statut d'une candidature suite à la décision d'un recruteur (ex: "ACCEPTEE", "REFUSEE", "EN_ATTENTE").
     * @param id : l'identifiant unique de la candidature à modifier.
     * @param status : la nouvelle chaîne de caractères représentant le statut de la candidature.
     * @param token : le jeton d'authentification pour confirmer l'habilitation du recruteur effectuant la modification.
     * @return un objet {@link Response} confirmant le changement d'état.
     * @throws Exception en cas d'erreur de traitement ou si les droits d'accès sont insuffisants.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response updateApplicationStatus(Long id, String status, String token) throws Exception;

    /**
     * Supprime définitivement une candidature du système.
     * Utilisé notamment si l'étudiant souhaite retirer son dossier avant le traitement par l'entreprise.
     * @param id : l'identifiant de la candidature à révoquer.
     * @return un objet {@link Response} confirmant la suppression (statut 204 No Content).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response deleteApplication(Long id);

}