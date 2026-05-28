package fr.ensitech.optistage.service;

import fr.ensitech.optistage.entity.Administrator;

/**
 * Interface de service définissant les privilèges et les opérations de haut niveau
 * réservés aux comptes d'administration d'OptiStage.
 * @author Amaan GD
 * @since 2026-05-20
 */
public interface IAdministratorService {

    /**
     * Enregistre un nouvel administrateur au sein du système central.
     * @param admin : l'entité administrateur contenant les identifiants et informations à persister.
     * @throws Exception si les contraintes de sécurité d'unicité ne sont pas respectées ou si la base échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.IAdministratorService#login(String, String)
     */
    void createAdmin(Administrator admin) throws Exception;

    /**
     * Authentifie un administrateur sur la plateforme via ses identifiants de connexion sécurisés.
     * @param login : l'identifiant ou l'adresse email de l'administrateur.
     * @param password : le mot de passe en clair à valider contre l'empreinte cryptographique.
     * @return l'entité {@link Administrator} authentifiée si l'accès est accordé.
     * @throws Exception si l'authentification échoue ou si le compte est inexistant.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.IAdministratorService#getAdminById(Long)
     */
    Administrator login(String login, String password) throws Exception;

    /**
     * Récupère la fiche complète d'un administrateur par son identifiant unique.
     * @param id : l'identifiant unique de l'administrateur ciblé.
     * @return l'administrateur correspondant, ou {@code null} s'il n'existe pas.
     * @throws Exception si une erreur de traitement ou d'accès aux données survient.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.IAdministratorService#updateAdmin(Administrator)
     */
    Administrator getAdminById(Long id) throws Exception;

    /**
     * Met à jour les paramètres de configuration ou le profil d'un administrateur existant.
     * @param admin : l'objet administrateur mis à jour avec ses nouvelles valeurs.
     * @throws Exception si l'entité fournie est corrompue ou introuvable en base.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.IAdministratorService#deleteAdmin(Long)
     */
    void updateAdmin(Administrator admin) throws Exception;

    /**
     * Révoque les droits et supprime définitivement un compte administrateur du système central.
     * @param id : l'identifiant unique du compte d'administration à détruire.
     * @throws Exception si l'identifiant est incorrect ou si l'administrateur n'existe pas.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.IAdministratorService#createAdmin(Administrator)
     */
    void deleteAdmin(Long id) throws Exception;
}