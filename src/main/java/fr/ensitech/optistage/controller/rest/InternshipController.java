package fr.ensitech.optistage.controller.rest;

import fr.ensitech.optistage.annotation.Secured;
import fr.ensitech.optistage.entity.Internship;
import fr.ensitech.optistage.entity.User;
import fr.ensitech.optistage.entity.dto.InternshipDto;
import fr.ensitech.optistage.service.IInternshipService;
import fr.ensitech.optistage.service.IUserService;
import fr.ensitech.optistage.service.InternshipService;
import fr.ensitech.optistage.service.UserService;
import fr.ensitech.optistage.utils.Dto;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("internships")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InternshipController implements IInternshipController {

    private final IInternshipService internshipService = new InternshipService();
    private final IUserService userService = new UserService();

    // 1. RÉCUPÉRER UN STAGE PAR SON ID
    @GET
    @Path("/{id}")
    @Secured
    @Override
    public Response getInternshipById(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de stage invalide").build();
        }
        try {
            Internship internship = internshipService.getInternshipById(id);
            if (internship == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Stage introuvable").build();
            }
            return Response.ok(Dto.internshipToDto(internship)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 2. RÉCUPÉRER UN STAGE VIA L'ID DE SA CANDIDATURE
    @GET
    @Path("/application/{appId}")
    @Secured
    @Override
    public Response getInternshipByApplicationId(@PathParam("appId") Long applicationId) {
        if (applicationId == null || applicationId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de candidature invalide").build();
        }
        try {
            Internship internship = internshipService.getInternshipByApplicationId(applicationId);
            if (internship == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Aucun stage actif pour cette candidature").build();
            }
            return Response.ok(Dto.internshipToDto(internship)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 3. VUE ÉTUDIANT : RÉCUPÉRER SES PROPRES STAGES
    @GET
    @Path("/student")
    @Secured
    @Override
    public Response getMyInternships(@Context ContainerRequestContext crc) {
        try {
            String email = (String) crc.getProperty("userEmail");
            User student = userService.getUserByEmail(email);

            List<Internship> internships = internshipService.getInternshipsByStudent(student.getId());
            List<InternshipDto> dtos = internships.stream()
                    .map(Dto::internshipToDto)
                    .collect(Collectors.toList());

            return Response.ok(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 4. VUE PROFESSEUR : RÉCUPÉRER LES STAGES SOUS SA RESPONSABILITÉ
    @GET
    @Path("/professor/{profId}")
    @Secured
    @Override
    public Response getInternshipsByProfessorId(@PathParam("profId") Long professorId) {
        if (professorId == null || professorId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID professeur invalide").build();
        }
        try {
            List<Internship> internships = internshipService.getInternshipsByProfessor(professorId);
            List<InternshipDto> dtos = internships.stream()
                    .map(Dto::internshipToDto)
                    .collect(Collectors.toList());

            return Response.ok(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 5. VUE ADMIN : LISTER TOUS LES STAGES DE L'ÉCOLE
    @GET
    @Path("/all")
    @Secured
    @Override
    public Response getAllInternships() {
        try {
            List<Internship> internships = internshipService.getAllInternships();
            List<InternshipDto> dtos = internships.stream()
                    .map(Dto::internshipToDto)
                    .collect(Collectors.toList());

            return Response.ok(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 6. METTRE À JOUR LES PARAMÈTRES DU STAGE (Convention, Notes, Feedback)
    @PUT
    @Path("/update/{id}")
    @Secured
    @Override
    public Response updateInternshipData(@PathParam("id") Long id, Internship internshipUpdate) {
        if (id == null || internshipUpdate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Données de mise à jour manquantes").build();
        }
        try {
            Internship existingInternship = internshipService.getInternshipById(id);
            if (existingInternship == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Stage introuvable").build();
            }

            // Application sélective des modifications
            if (internshipUpdate.getConventionStatus() != null) {
                existingInternship.setConventionStatus(internshipUpdate.getConventionStatus());
            }
            if (internshipUpdate.getNote_finale() != null) {
                existingInternship.setNote_finale(internshipUpdate.getNote_finale());
            }
            if (internshipUpdate.getRapport_tuteur() != null) {
                existingInternship.setRapport_tuteur(internshipUpdate.getRapport_tuteur());
            }
            if (internshipUpdate.getProfessor() != null) {
                existingInternship.setProfessor(internshipUpdate.getProfessor());
            }

            internshipService.updateInternship(existingInternship);
            return Response.ok(Dto.internshipToDto(existingInternship)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // 7. SUPPRIMER UN STAGE
    @DELETE
    @Path("/delete/{id}")
    @Secured
    @Override
    public Response deleteInternship(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID invalide").build();
        }
        try {
            internshipService.deleteInternship(id);
            return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}