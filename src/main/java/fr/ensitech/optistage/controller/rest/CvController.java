package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.annotation.Secured;
import fr.ensitech.optistage.entity.dto.CvUploadDto;
import fr.ensitech.optistage.service.ICvService;
import fr.ensitech.optistage.service.CvService;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/cv")
public class CvController implements ICvController {

    private final ICvService cvService;

    public CvController() {
        this.cvService = new CvService();
    }

    @POST
    @Path("/upload")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response uploadCv(CvUploadDto cvDto) {
        try {
            // ATTENTION MONSIEUR : Votre CvService.saveCv doit maintenant renvoyer le String de l'ID généré par la BDD NoSQL
            String generatedCvId = cvService.saveCv(cvDto.getFileName(), cvDto.getBase64Content());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Le CV a été stocké avec succès dans la base NoSQL.");
            // On renvoie cet ID au Front-end pour qu'il le glisse dans la candidature
            response.put("cvId", generatedCvId);

            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erreur lors de l'upload : " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{cvId}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getCv(@PathParam("cvId") String cvId) {
        try {
            // ATTENTION MONSIEUR : Votre CvService.getCv doit maintenant prendre un String en paramètre
            Document cvDoc = cvService.getCv(cvId);

            if (cvDoc == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Aucun CV trouvé pour cet identifiant.")
                        .build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("fileName", cvDoc.getString("fileName"));
            response.put("content", cvDoc.getString("content"));

            return Response.ok(response).build();

        } catch (Exception e) {
            return Response.serverError().entity("Erreur lors de la récupération : " + e.getMessage()).build();
        }
    }
}