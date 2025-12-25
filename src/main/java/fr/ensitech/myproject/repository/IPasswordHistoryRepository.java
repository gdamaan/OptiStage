package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.PasswordHistory;
import java.util.List;

public interface IPasswordHistoryRepository {

    /**
     * Ajoute un nouvel enregistrement d'historique de mot de passe (hash scrypt).
     * @param history L'objet PasswordHistory à persister.
     */
    void addHistory(PasswordHistory history) throws Exception;

    /**
     * Récupère la liste des hachages des 5 derniers mots de passe de l'utilisateur.
     * Utilisé pour la vérification de non-réutilisation.
     * @param userId L'ID de l'utilisateur.
     * @return Une liste de chaînes (les hashs scrypt).
     */
    List<String> getLatestHashes(Long userId) throws Exception;

    /**
     * Récupère le nombre total d'anciens mots de passe pour un utilisateur.
     * @param userId L'ID de l'utilisateur.
     * @return Le compte.
     */
    Long getHistoryCount(Long userId) throws Exception;

    /**
     * Supprime le plus ancien enregistrement d'historique pour l'utilisateur.
     * Appelé lorsque le compte dépasse 5.
     * @param userId L'ID de l'utilisateur.
     */
    void deleteOldestHistory(Long userId) throws Exception;
}