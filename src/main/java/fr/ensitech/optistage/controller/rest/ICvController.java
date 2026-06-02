package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.CvUploadDto;
import javax.ws.rs.core.Response;

public interface ICvController {

    /**
     * Route pour uploader un CV.
     */
    Response uploadCv(int studentId, CvUploadDto cvDto);

    /**
     * Route pour télécharger/voir le CV.
     */
    Response getCv(int studentId);
}