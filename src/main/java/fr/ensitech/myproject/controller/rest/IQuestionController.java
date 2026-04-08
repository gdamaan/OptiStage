package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Question;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/questions")
public interface IQuestionController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Question> getAllQuestions();
}