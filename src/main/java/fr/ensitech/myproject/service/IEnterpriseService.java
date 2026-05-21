package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Enterprise;
import java.util.List;

public interface IEnterpriseService {

    /**
     * Enregistre une nouvelle entreprise dans le système et l'associe à son
     * tuteur / gestionnaire professionnel.
     * @param enterprise : l'entité entreprise contenant toutes les informations obligatoires (SIRET, nom, etc.) à persister.
     * @throws Exception si le SIRET est invalide, si l'entreprise existe déjà ou si une erreur survient en base de données.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.EnterpriseService#deleteEnterprise(Long)
     */
    void createEnterprise(Enterprise enterprise) throws Exception;

    /**
     * Récupère les informations d'une entreprise à partir de son identifiant unique.
     * @param id : l'identifiant de l'entreprise à rechercher.
     * @return l'entreprise correspondante, ou {@code null} si aucune entité ne possède cet identifiant.
     * @throws Exception si l'identifiant fourni est invalide ou incorrect.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.EnterpriseService#getAllEnterprises()
     */
    Enterprise getEnterpriseById(Long id) throws Exception;

    /**
     * Recherche une entreprise en fonction de l'identifiant unique de l'utilisateur (recruteur/tuteur) qui la gère.
     * @param userId : l'identifiant du tuteur professionnel lié à l'entreprise.
     * @return l'entreprise managée par cet utilisateur.
     * @throws Exception si l'utilisateur n'existe pas ou n'est rattaché à aucune structure.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.EnterpriseService#getEnterpriseById(Long)
     */
    Enterprise getEnterpriseByUserId(Long userId) throws Exception;

    /**
     * Met à jour les données structurelles d'une entreprise existante (secteur, description, site web, etc.).
     * @param enterprise : l'objet entreprise contenant les modifications à appliquer.
     * @throws Exception si l'entreprise n'est pas répertoriée dans le système ou si la validation des données échoue.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.myproject.service.EnterpriseService#createEnterprise(Enterprise)
     */
    void updateEnterprise(Enterprise enterprise) throws Exception;

    /**
     * Supprime définitivement une entreprise du système ainsi que les dépendances associées si la politique de la base de données le permet.
     * @param id : l'identifiant de la structure à supprimer.
     * @throws Exception si l'identifiant est incorrect ou si l'entreprise est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    void deleteEnterprise(Long id) throws Exception;

    /**
     * Extrait la liste complète de toutes les entreprises partenaires enregistrées dans l'application.
     * @return une {@link List} contenant l'ensemble des entreprises.
     * @throws Exception si une erreur technique de lecture réseau ou de base de données survient.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<Enterprise> getAllEnterprises() throws Exception;
}