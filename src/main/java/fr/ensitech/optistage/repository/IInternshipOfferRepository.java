package fr.ensitech.optistage.repository;

import fr.ensitech.optistage.entity.InternshipOffer;
import java.util.List;

public interface IInternshipOfferRepository {
    void addOffer(InternshipOffer offer) throws Exception;
    InternshipOffer getOfferById(Long id) throws Exception;
    void updateOffer(InternshipOffer offer) throws Exception;
    void deleteOffer(InternshipOffer offer) throws Exception;
    List<InternshipOffer> getAllOffers() throws Exception;
    List<InternshipOffer> getOffersByEnterprise(Long enterpriseId) throws Exception;
    List<InternshipOffer> getOffersByTitle(String title) throws Exception;
}