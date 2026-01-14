package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.dto.InternshipOfferDto;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IInternshipOfferController {

    Response createOffer(InternshipOfferDto offerDto);
    Response getOfferById(Long id);
    Response getAllOffers();
    Response updateOffer(InternshipOfferDto offerDto);
    Response deleteOffer(Long id);
    /** Affiche les offres d'une entreprise spécifique (Tableau de bord Recruteur) */
    Response getOffersByEnterpriseId(Long enterpriseId);
    /** Recherche des offres (Moteur de recherche Étudiant) */
    Response getOffersByTitle(String title);
}