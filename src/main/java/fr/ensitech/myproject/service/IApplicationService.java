package fr.ensitech.myproject.service;

import fr.ensitech.myproject.entity.Application;
import java.util.List;

public interface IApplicationService {
    void createApplication(Application application) throws Exception;
    Application getApplicationById(Long id) throws Exception;
    void updateApplicationStatus(Long applicationId, String newStatus) throws Exception;
    void deleteApplication(Long id) throws Exception;
    List<Application> getApplicationsByStudent(Long studentId) throws Exception;
    List<Application> getApplicationsByOffer(Long offerId) throws Exception;
}