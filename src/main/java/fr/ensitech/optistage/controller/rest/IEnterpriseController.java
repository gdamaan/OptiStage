package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.EnterpriseDto;

import javax.ws.rs.core.Response;

public interface IEnterpriseController {

    // ==========================================
    // FONDAMENTAUX (CRUD)
    // ==========================================

    /**
     * Crée et enregistre une nouvelle entreprise partenaire dans le système.
     * @param enterpriseDto : l'objet DTO contenant les informations de l'entreprise à créer.
     * @return un objet {@link Response} confirmant la création (statut 201) ou signalant une erreur de validation (statut 400).
     * @throws Exception si une erreur technique survient lors de l'insertion en base de données.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response createEnterprise(EnterpriseDto enterpriseDto) throws Exception;

    /**
     * Récupère les détails d'une entreprise spécifique à partir de son identifiant unique.
     * @param id : l'identifiant unique de l'entreprise recherchée.
     * @return un objet {@link Response} contenant l'entreprise sous forme de DTO (statut 200), ou une erreur 404 si elle est introuvable.
     * @throws Exception si une erreur technique survient lors de la récupération.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getEnterpriseById(Long id) throws Exception;

    /**
     * Extrait l'ensemble des entreprises partenaires enregistrées dans la plateforme.
     * @return un objet {@link Response} contenant la liste exhaustive de toutes les entreprises.
     * @throws Exception si une erreur survient lors de l'accès aux données.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getAllEnterprises() throws Exception;

    /**
     * Met à jour les informations d'une entreprise existante (nom, adresse, contact, etc.).
     * @param enterpriseDto : l'objet DTO contenant les nouvelles données de l'entreprise (l'ID doit être inclus pour l'identification).
     * @return un objet {@link Response} confirmant la mise à jour des données (statut 200/202).
     * @throws Exception en cas d'erreur lors du processus de modification ou si l'entité est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response updateEnterprise(EnterpriseDto enterpriseDto) throws Exception;

    /**
     * Supprime définitivement une entreprise du système.
     * @param id : l'identifiant unique de l'entreprise à révoquer.
     * @return un objet {@link Response} confirmant la suppression (statut 204 No Content).
     * @throws Exception si l'ID est invalide ou si des contraintes d'intégrité (ex: offres ou stages liés) bloquent l'opération.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response deleteEnterprise(Long id) throws Exception;


    // ==========================================
    // FILTRES ET RECHERCHES SPÉCIFIQUES
    // ==========================================

    /**
     * Recherche et liste les entreprises filtrées par leur secteur d'activité.
     * @param sector : le secteur d'activité ciblé (ex: "Développement Web", "Cybersécurité").
     * @return un objet {@link Response} contenant la liste des entreprises opérant dans le secteur demandé.
     * @throws Exception en cas d'erreur lors de l'exécution de la requête de filtrage.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getEnterprisesBySector(String sector) throws Exception;

    /**
     * Récupère l'entreprise spécifiquement associée à un compte manager (tuteur ou recruteur de l'entreprise).
     * Idéal pour initialiser le tableau de bord côté partenaire.
     * @param managerId : l'identifiant unique de l'utilisateur (manager) rattaché à l'entreprise.
     * @return un objet {@link Response} contenant les informations de l'entreprise liée à ce compte.
     * @throws Exception si aucune entreprise ne correspond à cet utilisateur ou en cas d'erreur système.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getEnterpriseByManagerId(Long managerId) throws Exception;
}