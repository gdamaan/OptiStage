package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.Question;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IQuestionController {
    Response getAllQuestions();
}