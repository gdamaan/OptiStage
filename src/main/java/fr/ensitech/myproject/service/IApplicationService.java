package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Application;
import java.util.List;

/**
 * Interface de service gérant le cycle de vie et les règles métiers
 * applicables aux dossiers de candidature (Applications).
 * * @author Amaan GD
 * @since 2026-05-20
 */
public interface IApplicationService {

    /**
     * Crée et initialise une nouvelle candidature pour un étudiant sur une offre de stage spécifique,
     * après validation des critères d'unicité.
     * @param application : la candidature contenant l'étudiant et l'offre concernée.
     * @throws Exception si l'étudiant a déjà postulé à cette même offre ou si la persistance échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#getApplicationById(Long)
     */
    void createApplication(Application application) throws Exception;

    /**
     * Récupère une candidature spécifique à partir de son identifiant unique.
     * @param id : l'identifiant de la candidature à rechercher.
     * @return la candidature correspondante, ou {@code null} si elle n'existe pas.
     * @throws Exception si une erreur technique survient lors de la récupération.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#updateApplicationStatus(Long, String, String)
     */
    Application getApplicationById(Long id) throws Exception;

    /**
     * Met à jour le statut d'une candidature et applique la règle d'automatisation Stark
     * (création automatique de stage et refus des autres candidats si le statut passe à "ACCEPTE").
     * @param applicationId : l'identifiant de la candidature concernée.
     * @param newStatus : le nouveau statut à appliquer (ex: ACCEPTE, REFUSE).
     * @param requesterEmail : l'email du tuteur à l'origine de la demande pour vérification des droits d'accès.
     * @throws Exception si l'accès est refusé, si la candidature est introuvable ou si l'initialisation du stage échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#deleteApplication(Long)
     */
    void updateApplicationStatus(Long applicationId, String newStatus, String requesterEmail) throws Exception;

    /**
     * Supprime définitivement une candidature de la base de données.
     * @param id : l'identifiant de la candidature à détruire.
     * @throws Exception si la candidature n'existe pas ou si la suppression échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#getApplicationsByStudent(Long)
     */
    void deleteApplication(Long id) throws Exception;

    /**
     * Extrait toutes les candidatures soumises par un étudiant spécifique.
     * @param studentId : l'identifiant de l'étudiant concerné.
     * @return une {@link List} contenant l'historique des candidatures de l'étudiant.
     * @throws Exception si une erreur de lecture en base de données survient.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#getApplicationsByOffer(Long)
     */
    List<Application> getApplicationsByStudent(Long studentId) throws Exception;

    /**
     * Extrait l'intégralité des candidatures reçues pour une offre de stage particulière.
     * @param offerId : l'identifiant de l'offre de stage à inspecter.
     * @return une {@link List} des candidatures associées à cette offre.
     * @throws Exception si le traitement de la requête échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.IApplicationService#createApplication(Application)
     */
    List<Application> getApplicationsByOffer(Long offerId) throws Exception;
}