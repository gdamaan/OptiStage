package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Enterprise;
import fr.ensitech.myproject.entity.InternshipOffer;
import fr.ensitech.myproject.entity.dto.InternshipOfferDto;
import fr.ensitech.myproject.service.EnterpriseService;
import fr.ensitech.myproject.service.IEnterpriseService;
import fr.ensitech.myproject.service.InternshipOfferService;
import fr.ensitech.myproject.service.IInternshipOfferService;
import fr.ensitech.myproject.utils.Dto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("offers")
@Produces(MediaType.APPLICATION_JSON)
public class InternshipOfferController implements IInternshipOfferController {

    private final IInternshipOfferService offerService = new InternshipOfferService();
    private final IEnterpriseService enterpriseService = new EnterpriseService();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOffer(InternshipOfferDto dto) {
        if (dto == null || dto.getEnterpriseId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Enterprise ID obligatoire").build();
        }

        try {
            Enterprise enterprise = enterpriseService.getEnterpriseById(dto.getEnterpriseId());
            if (enterprise == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entreprise introuvable").build();
            }
            InternshipOffer offer = Dto.fromDto(dto, enterprise);
            offerService.createOffer(offer);

            return Response.status(Response.Status.CREATED).entity(Dto.offerToDto(offer)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Override
    public Response getOfferById(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID invalide").build();
        }
        try {
            InternshipOffer offer = offerService.getOfferById(id);
            if (offer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Offre introuvable").build();
            }
            return Response.status(Response.Status.OK).entity(Dto.offerToDto(offer)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public Response getAllOffers() {
        try {
            List<InternshipOffer> offers = offerService.getAllOffers();
            List<InternshipOfferDto> dtos = offers.stream()
                    .map(Dto::offerToDto)
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response updateOffer(InternshipOfferDto offerDto) {
        if (offerDto == null || offerDto.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de l'offre obligatoire").build();
        }
        try {
            // récupère l'ancienne version pour garder l'entreprise
            InternshipOffer existing = offerService.getOfferById(offerDto.getId());
            if (existing == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Offre introuvable").build();
            }
            //  transforme le DTO en entité en gardant l'entreprise originale
            InternshipOffer toUpdate = Dto.fromDto(offerDto, existing.getEnterprise());
            // maj
            offerService.updateOffer(toUpdate);

            return Response.status(Response.Status.OK).entity(Dto.offerToDto(toUpdate)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Override
    public Response deleteOffer(@PathParam("id") Long id) {
        try {
            offerService.deleteOffer(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/enterprise/{id}")
    @Override
    public Response getOffersByEnterpriseId(@PathParam("id") Long enterpriseId) {
        try {
            Enterprise enterprise = enterpriseService.getEnterpriseById(enterpriseId);
            if (enterprise == null) return Response.status(Response.Status.NOT_FOUND).entity("Entreprise introuvable").build();
            List<InternshipOffer> offers = offerService.getOffersByEnterprise(enterprise.getId());
            List<InternshipOfferDto> dtos = offers.stream()
                    .map(Dto::offerToDto)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(dtos).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search")
    @Override
    public Response getOffersByTitle(@QueryParam("title") String title) {
        try {
            List<InternshipOffer> offers = offerService.getOffersByTitle(title);
            if (offers.isEmpty()) {

            }

            List<InternshipOfferDto> dtos = offers.stream()
                    .map(Dto::offerToDto)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(dtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}