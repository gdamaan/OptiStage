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
    @Path("/{studentId}")
    @Secured // <-- VERROUILLAGE ACTIVÉ
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response uploadCv(@PathParam("studentId") int studentId, CvUploadDto cvDto) {
        try {
            cvService.saveCv(studentId, cvDto.getFileName(), cvDto.getBase64Content());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Le CV a été stocké avec succès la base de données.");
            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erreur lors de l'upload : " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{studentId}")
    @Secured // <-- VERROUILLAGE ACTIVÉ
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getCv(@PathParam("studentId") int studentId) {
        try {
            Document cvDoc = cvService.getCv(studentId);

            if (cvDoc == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Aucun CV trouvé pour cet étudiant.")
                        .build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("studentId", cvDoc.getInteger("studentId"));
            response.put("fileName", cvDoc.getString("fileName"));
            response.put("content", cvDoc.getString("content"));

            return Response.ok(response).build();

        } catch (Exception e) {
            return Response.serverError().entity("Erreur lors de la récupération : " + e.getMessage()).build();
        }
    }
}