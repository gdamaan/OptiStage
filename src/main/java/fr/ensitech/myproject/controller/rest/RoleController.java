package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.repository.RoleRepository;
import fr.ensitech.myproject.utils.Dto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/roles")
public class RoleController implements IRoleController {
    private final RoleRepository roleRepository = new RoleRepository();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAllRoles() {
        try {
            var dtos = roleRepository.getAllRoles().stream() // Utilise votre repo existant
                    .map(Dto::roleToDto)
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}