package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Enterprise;
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.entity.dto.EnterpriseDto;
import fr.ensitech.myproject.service.EnterpriseService;
import fr.ensitech.myproject.service.IEnterpriseService;
import fr.ensitech.myproject.service.UserService;
import fr.ensitech.myproject.service.IUserService;
import fr.ensitech.myproject.utils.Dto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("enterprises")
@Produces(MediaType.APPLICATION_JSON)
public class EnterpriseController implements IEnterpriseController {

    private final IEnterpriseService enterpriseService = new EnterpriseService();
    private final IUserService userService = new UserService();

    // URI => http://localhost:9991/ws/rest/enterprises/create
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEnterprise(EnterpriseDto dto) {
        // 1. Validation des boucliers (Champs obligatoires)
        if (dto == null
                || dto.getName() == null || dto.getName().isBlank()
                || dto.getSiret() == null || dto.getSiret().isBlank()
                || dto.getManagerId() == null || dto.getManagerId() <= 0) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Données invalides : Nom, SIRET et ID du Manager sont obligatoires.")
                    .build();
        }

        try {
            // 2. Récupération du Recruteur (Manager)
            User manager = userService.getUserById(dto.getManagerId());
            if (manager == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Aucun utilisateur trouvé avec l'ID " + dto.getManagerId())
                        .build();
            }

            // 3. Transformation DTO -> Entity (Assemblage de l'armure)
            Enterprise enterprise = new Enterprise();
            enterprise.setName(dto.getName());
            enterprise.setSiret(dto.getSiret());
            enterprise.setSector(dto.getSector());
            enterprise.setDescription(dto.getDescription());
            enterprise.setWebsite(dto.getWebsite());

            // Liaison avec le manager
            enterprise.setUser(manager);

            // 4. Appel du Service (Logique métier)
            enterpriseService.createEnterprise(enterprise);

            // 5. Réponse (Succès 201 Created)
            return Response.status(Response.Status.CREATED)
                    .entity(Dto.enterpriseToDto(enterprise))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            // Le service renvoie une exception si le SIRET existe déjà ou si le user a déjà une entreprise
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // URI => http://localhost:9991/ws/rest/enterprises/all
    @GET
    @Path("/all")
    public Response getAllEnterprises() {
        try {
            List<Enterprise> enterprises = enterpriseService.getAllEnterprises();
            List<EnterpriseDto> dtos = new ArrayList<>();

            enterprises.forEach(e -> dtos.add(Dto.enterpriseToDto(e)));

            return Response.status(Response.Status.OK).entity(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response updateEnterprise(EnterpriseDto enterpriseDto) throws Exception {
        return null;
    }

    @Override
    public Response deleteEnterprise(Long id) throws Exception {

        return null;
    }

    @Override
    public Response getEnterprisesBySector(String sector) throws Exception {
        return null;
    }

    @Override
    public Response getEnterpriseByManagerId(Long managerId) throws Exception {
        return null;
    }

    // URI => http://localhost:9991/ws/rest/enterprises/1
    @GET
    @Path("/{id}")
    public Response getEnterpriseById(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID invalide").build();
        }
        try {
            Enterprise enterprise = enterpriseService.getEnterpriseById(id);
            if (enterprise == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entreprise introuvable").build();
            }
            return Response.status(Response.Status.OK).entity(Dto.enterpriseToDto(enterprise)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}