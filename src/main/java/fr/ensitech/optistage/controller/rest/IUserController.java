package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.User;
import fr.ensitech.optistage.entity.dto.LoginRequest;

import javax.ws.rs.core.Response;

public interface IUserController {

    /**
     * Point de terminaison de test pour vérifier la disponibilité de l'API utilisateur.
     * @return une chaîne de caractères de bienvenue.
     * @author Amaan GD
     * @since 2026-05-20
     */
    String getInfos();

    /**
     * Récupère l'ensemble des utilisateurs enregistrés dans le système.
     * Cette méthode est sécurisée et requiert un jeton d'authentification (JWT).
     * @return un objet {@link Response} contenant la liste des utilisateurs sous forme de DTO (statut 200) ou une erreur interne (statut 500).
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.controller.rest.UserController#getUsers()
     */
    Response getUsers();

    /**
     * Récupère les informations d'un utilisateur spécifique à partir de son identifiant.
     * @param id : l'identifiant unique de l'utilisateur recherché.
     * @return un objet {@link Response} contenant l'utilisateur trouvé sous forme de DTO (statut 200), ou une erreur 404 s'il est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getUserById(Long id);

    /**
     * Inscrit un nouvel utilisateur dans le système via une requête HTTP POST.
     * Le compte créé sera inactif par défaut jusqu'à sa validation.
     * @param user : l'objet User contenant les données saisies lors de l'inscription.
     * @return un objet {@link Response} confirmant la création (statut 201) ou signalant une erreur de requête (statut 400).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response createUser(User user);

    /**
     * Met à jour les informations du profil d'un utilisateur.
     * Cette méthode est sécurisée et nécessite que l'utilisateur soit connecté.
     * @param user : l'objet User contenant les nouvelles informations de profil.
     * @return un objet {@link Response} confirmant la mise à jour (statut 202) ou signalant une anomalie.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response updateProfile(User user);

    /**
     * Active le compte d'un utilisateur préalablement inscrit.
     * @param id : l'identifiant unique de l'utilisateur à activer.
     * @return un objet {@link Response} confirmant l'activation (statut 202).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response activate(Long id);

    /**
     * Désinscrit un utilisateur en basculant son compte vers un état inactif.
     * @param email : l'adresse email de l'utilisateur à désactiver.
     * @return un objet {@link Response} confirmant la désinscription (statut 202).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response unsubscribe(String email);

    /**
     * Authentifie un utilisateur, vérifie sa validité humaine (CAPTCHA), et délivre un jeton JWT.
     * @param loginReq : un objet DTO encapsulant l'email, le mot de passe en clair et le token CAPTCHA.
     * @return un objet {@link Response} contenant les données utilisateur et un cookie HTTP sécurisé avec le JWT (statut 200), ou un refus d'accès (statut 401/403).
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.controller.rest.UserController#login(LoginRequest)
     */
    Response login(LoginRequest loginReq);

    /**
     * Déconnecte l'utilisateur en invalidant sa session active.
     * @param email : l'adresse email de l'utilisateur à déconnecter.
     * @return un objet {@link Response} confirmant la déconnexion.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response logout(String email);

    /**
     * Récupère la question secrète d'un utilisateur en vue d'une réinitialisation de mot de passe.
     * @param email : l'email de l'utilisateur cible.
     * @param oldPassword : un ancien mot de passe connu requis pour valider l'identité.
     * @return un objet {@link Response} contenant la question secrète (statut 200).
     * @throws Exception en cas de dysfonctionnement lors du traitement.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getQuestion(String email, String oldPassword) throws Exception;

    /**
     * Vérifie la réponse à la question secrète et, si elle est correcte, attribue un nouveau mot de passe à l'utilisateur.
     * @param email : l'email de l'utilisateur concerné.
     * @param newpwd : le nouveau mot de passe en clair à attribuer.
     * @param secretResponse : la réponse en clair à la question secrète soumise par l'utilisateur.
     * @return un objet {@link Response} confirmant le changement (statut 200) ou rejetant la réponse (statut 401).
     * @throws Exception en cas d'erreur de traitement ou de hachage.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response checkResponse(String email, String newpwd, String secretResponse) throws Exception;

}