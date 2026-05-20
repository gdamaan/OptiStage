package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Application;
import fr.ensitech.myproject.entity.Internship; // N'oubliez pas cet import
import fr.ensitech.myproject.repository.ApplicationRepository;
import fr.ensitech.myproject.repository.IApplicationRepository;

import java.util.Date;
import java.util.List;

public class ApplicationService implements IApplicationService {

    private final IInternshipService internshipService = new InternshipService();
    private final IApplicationRepository applicationRepository;

    public ApplicationService() {
        this.applicationRepository = new ApplicationRepository();
    }

    @Override
    public void createApplication(Application application) throws Exception {
        List<Application> existingApplications = applicationRepository.getApplicationsByStudent(application.getStudent().getId());

        for (Application app : existingApplications) {
            if (app.getOffer().getId().equals(application.getOffer().getId())) {
                throw new Exception("Vous avez déja postulé à cette offre.");
            }
        }
        application.setApplyDate(new Date());
        application.setStatus("EN_ATTENTE"); // Statut initial

        this.applicationRepository.addApplication(application);
    }

    @Override
    public Application getApplicationById(Long id) throws Exception {
        return this.applicationRepository.getApplicationById(id);
    }

    @Override
    public void updateApplicationStatus(Long applicationId, String newStatus, String requesterEmail) throws Exception {
        Application application = this.applicationRepository.getApplicationById(applicationId);

        if (application == null) {
            throw new Exception("Candidature introuvable pour la mise à jour du statut.");
        }

        // On récupère l'email du tuteur qui a créé l'offre associée à cette candidature
        String managerEmail = application.getOffer().getEnterprise().getUser().getEmail();

        // On le compare avec l'email de la personne qui fait la requête
        if (!managerEmail.equals(requesterEmail)) {
            throw new Exception("Accès refusé : Vous n'êtes pas le propriétaire de cette offre.");
        }

        // === L'AUTOMATISATION STARK : LOGIQUE HIGHLANDER ET CRÉATION DE STAGE ===
        if ("ACCEPTE".equals(newStatus)) {
            // On récupère toutes les candidatures pour cette offre
            List<Application> allAppsForThisOffer = this.applicationRepository.getApplicationsByOffer(application.getOffer().getId());

            for (Application app : allAppsForThisOffer) {
                if (app.getId().equals(applicationId)) {
                    // L'heureux élu
                    app.setStatus("ACCEPTE");

                    // On vérifie d'abord si un stage n'a pas déjà été créé pour éviter les doublons
                    if (this.internshipService.getInternshipByApplicationId(app.getId()) == null) {
                        Internship newInternship = new Internship();
                        newInternship.setApplication(app);
                        // On l'enregistre en base de données
                        this.internshipService.createInternship(newInternship);
                    }
                } else {
                    // Les autres candidats sont automatiquement refusés
                    app.setStatus("REFUSE");
                }
                // On met à jour chaque candidature en base
                this.applicationRepository.updateApplication(app);
            }
        } else {
            // Si c'est juste un refus (ou autre statut), on ne touche qu'à cette candidature spécifique
            application.setStatus(newStatus);
            this.applicationRepository.updateApplication(application);
        }
        // =========================================================================
    }

    @Override
    public void deleteApplication(Long id) throws Exception {
        Application application = this.applicationRepository.getApplicationById(id);
        if (application != null) {
            this.applicationRepository.deleteApplication(application);
        }
    }

    @Override
    public List<Application> getApplicationsByStudent(Long studentId) throws Exception {
        return this.applicationRepository.getApplicationsByStudent(studentId);
    }

    @Override
    public List<Application> getApplicationsByOffer(Long offerId) throws Exception {
        return this.applicationRepository.getApplicationsByOffer(offerId);
    }
}