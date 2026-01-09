package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.InternshipOffer;
import fr.ensitech.myproject.repository.IInternshipOfferRepository;
import fr.ensitech.myproject.repository.InternshipOfferRepository;

import java.util.List;

public class InternshipOfferService implements IInternshipOfferService {

    private final IInternshipOfferRepository offerRepository;

    public InternshipOfferService() {
        this.offerRepository = new InternshipOfferRepository();
    }

    @Override
    public void createOffer(InternshipOffer offer) throws Exception {

        if (offer.getStartDate() != null && offer.getEndDate() != null) {
            if (offer.getEndDate().before(offer.getStartDate())) {
                throw new Exception("Erreur  : la date de fin ne peut pas être avant la date de début.");
            }
        }

        if (offer.getEnterprise() == null) {
            throw new Exception("Une offre doit obligatoirement être rattachée à une entreprise.");
        }

        this.offerRepository.addOffer(offer);
    }

    @Override
    public InternshipOffer getOfferById(Long id) throws Exception {
        return this.offerRepository.getOfferById(id);
    }

    @Override
    public void updateOffer(InternshipOffer offer) throws Exception {
        this.offerRepository.updateOffer(offer);
    }

    @Override
    public void deleteOffer(Long id) throws Exception {
        InternshipOffer offer = this.offerRepository.getOfferById(id);
        if (offer != null) {
            this.offerRepository.deleteOffer(offer);
        } else {
            throw new Exception("Impossible de supprimer : offre introuvable (ID: " + id + ")");
        }
    }

    @Override
    public List<InternshipOffer> getAllOffers() throws Exception {
        return this.offerRepository.getAllOffers();
    }

    @Override
    public List<InternshipOffer> getOffersByEnterprise(Long enterpriseId) throws Exception {
        return this.offerRepository.getOffersByEnterprise(enterpriseId);
    }

    @Override
    public List<InternshipOffer> getOffersByTitle(String title) throws Exception {
        return this.offerRepository.getOffersByTitle(title);
    }
}