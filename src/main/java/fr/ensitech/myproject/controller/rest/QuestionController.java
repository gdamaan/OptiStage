package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Question;
import fr.ensitech.myproject.entity.dto.QuestionDto;
import fr.ensitech.myproject.repository.QuestionRepository;
import fr.ensitech.myproject.utils.Dto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("questions")
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController implements IQuestionController {
    private final QuestionRepository questionRepository = new QuestionRepository();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAllQuestions() {
        try {
            // Récupération des entités via le repository
            List<Question> questions = questionRepository.getQuestions();

            // Conversion en DTO suivant votre pattern
            List<QuestionDto> dtos = questions.stream()
                    .map(Dto::questionToDto)
                    .collect(Collectors.toList());

            // Retour d'une réponse HTTP 200 avec le JSON des DTOs
            return Response.status(Response.Status.OK).entity(dtos).build();
        } catch (Exception e) {
            // Retour d'une erreur 500 en cas de pépin
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des questions : " + e.getMessage())
                    .build();
        }
    }
}