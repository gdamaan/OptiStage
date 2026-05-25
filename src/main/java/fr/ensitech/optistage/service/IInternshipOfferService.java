package fr.ensitech.optistage.service;

import fr.ensitech.optistage.entity.InternshipOffer;
import java.util.List;

public interface IInternshipOfferService {

    /**
     * Crée et publie une nouvelle offre de stage dans le système, la rendant ainsi
     * visible et accessible aux candidatures des étudiants.
     * @param offer : l'offre de stage contenant toutes les informations requises à enregistrer.
     * @throws Exception si une erreur survient lors de la persistance ou si les données sont invalides.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.InternshipOfferService#deleteOffer(Long)
     */
    void createOffer(InternshipOffer offer) throws Exception;

    /**
     * Récupère une offre de stage spécifique à partir de son identifiant unique.
     * @param id : l'identifiant de l'offre à rechercher.
     * @return l'offre de stage correspondant à l'identifiant fourni.
     * @throws Exception si l'identifiant est invalide ou si l'offre reste introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.InternshipOfferService#getAllOffers()
     */
    InternshipOffer getOfferById(Long id) throws Exception;

    /**
     * Met à jour les informations détaillées d'une offre de stage existante.
     * @param offer : l'objet offre contenant les données modifiées à appliquer.
     * @throws Exception si l'offre n'existe pas ou si la mise à jour échoue en base de données.
     * @author Amaan GD
     * @since 2026-05-20
     * @see fr.ensitech.optistage.service.InternshipOfferService#createOffer(InternshipOffer)
     */
    void updateOffer(InternshipOffer offer) throws Exception;

    /**
     * Supprime définitivement une offre de stage du système à partir de son identifiant.
     * @param id : l'identifiant de l'offre à détruire.
     * @throws Exception si l'identifiant est incorrect ou si l'offre est introuvable.
     * @author Amaan GD
     * @since 2026-05-20
     */
    void deleteOffer(Long id) throws Exception;

    /**
     * Extrait la totalité des offres de stage enregistrées et actives dans l'application.
     * @return une {@link List} contenant l'intégralité des offres disponibles.
     * @throws Exception si une erreur technique survient lors de la récupération des données.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<InternshipOffer> getAllOffers() throws Exception;

    /**
     * Récupère l'ensemble des offres de stage publiées par une entreprise spécifique.
     * @param enterpriseId : l'identifiant unique de l'entreprise émettrice.
     * @return une {@link List} des offres de stage rattachées à cette entreprise.
     * @throws Exception si l'identifiant de l'entreprise est invalide.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<InternshipOffer> getOffersByEnterprise(Long enterpriseId) throws Exception;

    /**
     * Effectue une recherche textuelle pour filtrer les offres de stage par leur titre.
     * @param title : la chaîne de caractères ou le mot-clé à rechercher dans les titres des offres.
     * @return une {@link List} d'offres dont le titre correspond au critère de recherche.
     * @throws Exception si la recherche échoue ou si les paramètres sont invalides.
     * @author Amaan GD
     * @since 2026-05-20
     */
    List<InternshipOffer> getOffersByTitle(String title) throws Exception;
}