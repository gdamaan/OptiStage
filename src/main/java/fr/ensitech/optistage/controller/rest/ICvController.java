package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.entity.dto.CvUploadDto;
import javax.ws.rs.core.Response;

public interface ICvController {

    // On ne passe plus le studentId, on envoie juste le DTO contenant le Base64
    Response uploadCv(CvUploadDto cvDto);

    // On récupère le CV via l'ID généré par la base NoSQL (String)
    Response getCv(String cvId);
}