package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Application;
import fr.ensitech.myproject.entity.InternshipOffer;
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.entity.dto.ApplicationDto;
import fr.ensitech.myproject.service.*;
import fr.ensitech.myproject.utils.Dto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationController implements IApplicationController {

    private final IApplicationService appService = new ApplicationService();
    private final IUserService userService = new UserService();
    private final IInternshipOfferService offerService = new InternshipOfferService();

    // 1. CRÉATION
    @POST
    @Path("/create")
    @Override
    public Response createApplication(ApplicationDto dto) {
        // Validation des entrées
        if (dto == null || dto.getStudentId() == null || dto.getOfferId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Données manquantes : ID étudiant et ID offre requis.")
                    .build();
        }

        try {
            // Récupération des entités liées
            User student = userService.getUserById(dto.getStudentId());
            InternshipOffer offer = offerService.getOfferById(dto.getOfferId());

            if (student == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Étudiant introuvable").build();
            }
            if (offer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Offre introuvable").build();
            }

            // Assemblage et sauvegarde
            Application app = Dto.fromDto(dto, student, offer);
            appService.createApplication(app);

            // Retour propre
            return Response.status(Response.Status.CREATED).entity(Dto.applicationToDto(app)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 2. RÉCUPÉRATION PAR ID
    @GET
    @Path("/{id}")
    @Override
    public Response getApplicationById(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID invalide").build();
        }
        try {
            Application app = appService.getApplicationById(id);
            if (app == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Candidature introuvable").build();
            }
            return Response.ok(Dto.applicationToDto(app)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 3. LISTE DES CANDIDATURES D'UN ÉTUDIANT
    @GET
    @Path("/student/{id}")
    @Override
    public Response getApplicationsByStudentId(@PathParam("id") Long studentId) {
        try {
            List<Application> apps = appService.getApplicationsByStudent(studentId);

            List<ApplicationDto> dtos = apps.stream()
                    .map(Dto::applicationToDto)
                    .collect(Collectors.toList());

            return Response.ok(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 4. LISTE DES CANDIDATURES POUR UNE OFFRE (Vue Recruteur)
    @GET
    @Path("/offer/{id}")
    @Override
    public Response getApplicationsByOfferId(@PathParam("id") Long offerId) {
        try {
            List<Application> allApps = appService.getApplicationsByOffer(offerId);

            List<ApplicationDto> offerApps = allApps.stream()
                    .map(Dto::applicationToDto)
                    .collect(Collectors.toList());

            return Response.ok(offerApps).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 5. MISE À JOUR DU STATUT (Accepté/Refusé)
    @PUT
    @Path("/{id}/status")
    @Override
    public Response updateApplicationStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        if (id == null || status == null || status.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID et Statut obligatoires").build();
        }
        try {
            Application app = appService.getApplicationById(id);
            if (app == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Candidature introuvable").build();
            }
            // Mise à jour manuelle du champ
            app.setStatus(status);
            appService.updateApplicationStatus(app.getId(), status);

            return Response.ok(Dto.applicationToDto(app)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 6. SUPPRESSION
    @DELETE
    @Path("/delete/{id}")
    @Override
    public Response deleteApplication(@PathParam("id") Long id) {
        if (id == null) return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            Application app = appService.getApplicationById(id);
            if (app == null) return Response.status(Response.Status.NOT_FOUND).build();

            appService.deleteApplication(id);
            return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}