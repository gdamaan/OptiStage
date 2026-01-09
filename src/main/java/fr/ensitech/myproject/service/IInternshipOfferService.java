package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.InternshipOffer;
import java.util.List;

public interface IInternshipOfferService {
    void createOffer(InternshipOffer offer) throws Exception;
    InternshipOffer getOfferById(Long id) throws Exception;

    void updateOffer(InternshipOffer offer) throws Exception;
    void deleteOffer(Long id) throws Exception;
    List<InternshipOffer> getAllOffers() throws Exception;
    List<InternshipOffer> getOffersByEnterprise(Long enterpriseId) throws Exception;
    List<InternshipOffer> getOffersByTitle(String title) throws Exception;
}