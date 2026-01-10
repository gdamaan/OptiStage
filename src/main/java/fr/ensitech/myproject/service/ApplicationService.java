package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Application;
import fr.ensitech.myproject.repository.ApplicationRepository;
import fr.ensitech.myproject.repository.IApplicationRepository;

import java.util.Date;
import java.util.List;

public class ApplicationService implements IApplicationService {

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
        application.setStatus("En cours de traitement."); // Statut initial

        this.applicationRepository.addApplication(application);
    }

    @Override
    public Application getApplicationById(Long id) throws Exception {
        return this.applicationRepository.getApplicationById(id);
    }

    @Override
    public void updateApplicationStatus(Long applicationId, String newStatus) throws Exception {
        Application application = this.applicationRepository.getApplicationById(applicationId);
        if (application != null) {
            application.setStatus(newStatus);
            this.applicationRepository.updateApplication(application);
        } else {
            throw new Exception("Candidature introuvable pour la mise à jour du statut.");
        }
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