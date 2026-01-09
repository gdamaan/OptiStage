package fr.ensitech.myproject.repository;

import fr.ensitech.myproject.entity.Application;
import java.util.List;

public interface IApplicationRepository {
    void addApplication(Application application) throws Exception;
    Application getApplicationById(Long id) throws Exception;
    void updateApplication(Application application) throws Exception;
    void deleteApplication(Application application) throws Exception;
    List<Application> getApplicationsByStudent(Long studentId) throws Exception;
    List<Application> getApplicationsByOffer(Long offerId) throws Exception;
}