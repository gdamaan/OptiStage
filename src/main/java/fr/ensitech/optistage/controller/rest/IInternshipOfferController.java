package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.InternshipOfferDto;
import javax.ws.rs.core.Response;

public interface IInternshipOfferController {

    /**
     * Crée et publie une nouvelle offre de stage dans le système.
     * @param offerDto : l'objet DTO contenant les informations de la nouvelle offre de stage.
     * @return un objet {@link Response} confirmant la création de l'offre (statut 201) ou signalant une erreur de paramétrage (statut 400).
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response createOffer(InternshipOfferDto offerDto);

    /**
     * Récupère les détails d'une offre de stage spécifique à partir de son identifiant unique.
     * @param id : l'identifiant de l'offre de stage à consulter.
     * @return un objet {@link Response} contenant l'offre sous forme de DTO (statut 200), ou une erreur 404 si l'offre n'existe pas.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getOfferById(Long id);

    /**
     * Extrait l'ensemble des offres de stage actives enregistrées dans la base de données.
     * @return un objet {@link Response} contenant la liste de toutes les offres de stage disponibles.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getAllOffers();

    /**
     * Met à jour les informations d'une offre de stage préalablement publiée.
     * @param offerDto : l'objet DTO contenant les nouvelles données de l'offre à modifier (l'ID doit être inclus).
     * @return un objet {@link Response} confirmant la mise à jour de l'offre.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response updateOffer(InternshipOfferDto offerDto);

    /**
     * Supprime définitivement une offre de stage du système.
     * @param id : l'identifiant unique de l'offre à supprimer.
     * @return un objet {@link Response} confirmant la suppression (statut 204 No Content) ou signalant une erreur si l'ID est invalide.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response deleteOffer(Long id);

    /**
     * Récupère l'ensemble des offres de stage publiées par une entreprise spécifique.
     * Idéal pour alimenter le tableau de bord d'un recruteur.
     * @param enterpriseId : l'identifiant unique de l'entreprise recruteuse.
     * @return un objet {@link Response} contenant la liste des offres associées à cette entreprise.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getOffersByEnterpriseId(Long enterpriseId);

    /**
     * Recherche des offres de stage dont le titre correspond ou contient une chaîne de caractères spécifique.
     * Utilisé comme moteur de recherche principal pour les étudiants.
     * @param title : les mots-clés ou le titre de l'offre recherchée.
     * @return un objet {@link Response} contenant la liste des offres correspondant aux critères de recherche.
     * @author Amaan GD
     * @since 2026-05-20
     */
    Response getOffersByTitle(String title);
}