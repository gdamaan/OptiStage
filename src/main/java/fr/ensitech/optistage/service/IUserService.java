package fr.ensitech.optistage.service;

import fr.ensitech.optistage.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * Inscrit un nouvel utilisateur dans le système avec l'état non Actif et devra l'activer via un mail.
     * @param user : l'utilisateur à inscrire.
     * @return {@code true} si l'utilisateur a été créé avec succès, {@code false} sinon.
     * @throws Exception si une erreur a lieu lors de l'inscription.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#unsubscribe(String email)
     */
    boolean subscribe(User user) throws Exception;

    /**
     * Désinscrit un utilisateur du système en le mettant dans un état non Actif.
     * @param email : l'email de l'utilisateur à désinscrire.
     * @throws Exception si une erreur a lieu lors de la désinscription.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#updateProfile(User)
     */
    void unsubscribe(String email) throws Exception;

    /**
     * Active un utilisateur du système en le mettant dans un état Actif. L'utilisateur doit être préalablement inscrit et doit activer son compte via un mail d'activation.
     * @param userId : l'identifiant de l'utilisateur à activer.
     * @throws Exception si une erreur a lieu lors de l'activation, notamment si l'utilisateur à activer n'existe pas ou si les paramètres sont incorrects.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#subscribe(User)
     */
    void activate(Long userId) throws Exception;

    /**
     * Récupère un utilisateur à partir de son email. L'utilisateur doit être préalablement inscrit.
     * @param email : l'email de l'utilisateur à récupérer.
     * @return l'utilisateur correspondant à l'email fourni.
     * @throws Exception si une erreur a lieu lors de la récupération de l'utilisateur, notamment si l'utilisateur n'existe pas ou si les paramètres sont incorrects.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#getUserById(Long id)
     */
    User getUserByEmail(String email) throws Exception;

    /**
     * Récupère un utilisateur à partir de son identifiant unique. L'utilisateur doit être préalablement inscrit.
     * @param id : l'identifiant de l'utilisateur à récupérer.
     * @return l'utilisateur correspondant à l'identifiant fourni.
     * @throws Exception si une erreur a lieu lors de la récupération, notamment si aucun utilisateur ne possède cet identifiant.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#getUserByEmail(String email)
     */
    User getUserById(Long id) throws Exception;

    /**
     * Met à jour le profil d'un utilisateur. Seules les informations de base (prénom, nom, date de naissance) sont mises à jour.
     * @param user : l'utilisateur avec les nouvelles informations à mettre à jour. L'email doit être renseigné pour identifier l'utilisateur à mettre à jour.
     * @throws Exception si une erreur a lieu lors de la mise à jour du profil, notamment si l'utilisateur à mettre à jour n'existe pas ou si les paramètres sont incorrects.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#updatePassword(Long, String)
     */
    void updateProfile(User user) throws Exception;

    /**
     * Modifie le mot de passe d'un utilisateur après validation. Le nouveau mot de passe sera sécurisé avant d'être persisté.
     * @param userId : l'identifiant de l'utilisateur concerné par le changement.
     * @param newPassword : le nouveau mot de passe en clair à attribuer.
     * @throws Exception si une erreur survient pendant le processus de mise à jour ou si l'identifiant est invalide.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.UserService#updateProfile(User)
     */
    void updatePassword(Long userId, String newPassword) throws Exception;

    /**
     * Extrait l'ensemble des utilisateurs enregistrés dans la base de données.
     * @return une {@link List} contenant tous les utilisateurs du système.
     * @throws Exception si une erreur technique survient lors de la récupération des données.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<User> getAllUsers() throws Exception;

    /**
     * Récupère la question secrète associée à un utilisateur en vue d'une réinitialisation, à condition que l'ancien mot de passe soit fourni pour validation.
     * @param email : l'email de l'utilisateur dont on cherche la question.
     * @param oldPassword : l'ancien mot de passe pour des raisons évidentes de vérification de sécurité.
     * @return la question de sécurité sous forme de chaîne de caractères.
     * @throws Exception si l'authentification initiale échoue ou si l'utilisateur reste introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    String getQuestion(String email, String oldPassword) throws Exception;
}