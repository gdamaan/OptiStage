package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.dto.ApplicationDto;
import javax.ws.rs.core.Response;

public interface IApplicationController {

    Response createApplication(ApplicationDto dto);

    Response getApplicationById(Long id);

    Response getApplicationsByStudentId(Long studentId);

    Response getApplicationsByOfferId(Long offerId);

    // Changer le statut (Accepter/Refuser)
    Response updateApplicationStatus(Long id, String status);

    Response deleteApplication(Long id);

}