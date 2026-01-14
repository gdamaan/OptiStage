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
import java.util.stream.Collectors;

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
        if (dto == null || dto.getManagerId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Manager ID obligatoire").build();
        }

        try {
            User manager = userService.getUserById(dto.getManagerId());
            if (manager == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Recruteur introuvable").build();
            }
            Enterprise enterprise = Dto.fromDto(dto, manager);
            enterpriseService.createEnterprise(enterprise);

            return Response.status(Response.Status.CREATED).entity(Dto.enterpriseToDto(enterprise)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // URI => http://localhost:9991/ws/rest/enterprises/update
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response updateEnterprise(EnterpriseDto dto) {
        if (dto == null || dto.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de l'entreprise obligatoire pour modification").build();
        }
        try {
            Enterprise existing = enterpriseService.getEnterpriseById(dto.getId());
            if (existing == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entreprise introuvable").build();
            }


            Enterprise toUpdate = Dto.fromDto(dto, existing.getUser());

            enterpriseService.updateEnterprise(toUpdate);

            return Response.status(Response.Status.OK).entity(Dto.enterpriseToDto(toUpdate)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // URI => http://localhost:9991/ws/rest/enterprises/delete/5
    @DELETE
    @Path("/delete/{id}")
    @Override
    public Response deleteEnterprise(@PathParam("id") Long id) {
        try {
            enterpriseService.deleteEnterprise(id);
            return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content (Standard pour un delete)
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    // URI => http://localhost:9991/ws/rest/enterprises/manager/1
    @GET
    @Path("/manager/{id}")
    @Override
    public Response getEnterpriseByManagerId(@PathParam("id") Long managerId) {
        try {
            Enterprise enterprise = enterpriseService.getEnterpriseByUserId(managerId);
            if (enterprise == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Aucune entreprise pour ce manager").build();
            }
            return Response.status(Response.Status.OK).entity(Dto.enterpriseToDto(enterprise)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // TODO Faire la méthode dans le SERVICE
    @GET
    @Path("/sector/{sector}")
    @Override
    public Response getEnterprisesBySector(@PathParam("sector") String sector) {
        return null;
    }


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

    @GET
    @Path("/{id}")
    public Response getEnterpriseById(@PathParam("id") Long id) {
        if (id == null || id <= 0) return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            Enterprise enterprise = enterpriseService.getEnterpriseById(id);
            if (enterprise == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.status(Response.Status.OK).entity(Dto.enterpriseToDto(enterprise)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}