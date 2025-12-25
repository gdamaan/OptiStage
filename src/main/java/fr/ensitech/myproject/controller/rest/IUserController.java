package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.User;

import javax.ws.rs.core.Response;

public interface IUserController {

    String getInfos();
    Response getUsers();
    Response getUserById(Long id);
    Response createUser(User user);
    Response updateProfile(User user);
    Response activate(Long id);
    Response unsubscribe(String email);
    Response login(String email, String password);
    Response logout(String email);
    Response getQuestion(String email, String oldPassword) throws Exception;
    Response checkResponse(String email, String response, String Response) throws Exception;

}
