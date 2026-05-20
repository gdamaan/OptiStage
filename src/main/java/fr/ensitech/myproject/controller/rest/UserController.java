package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.annotation.Secured; // <-- NOTRE NOUVEAU PORTIQUE
import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.entity.dto.LoginRequest;
import fr.ensitech.myproject.entity.dto.UserDto;
import fr.ensitech.myproject.service.IUserService;
import fr.ensitech.myproject.service.UserService;
import fr.ensitech.myproject.utils.Dto;
import fr.ensitech.myproject.utils.JwtUtil;
import fr.ensitech.myproject.utils.PasswordHasher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController implements IUserController{

    private final IUserService userService =  new UserService();
    private static final long MAX_PASSWORD_AGE_MS = TimeUnit.DAYS.toMillis(12 * 7);

    // uri => http://localhost:9991/ws/rest/users/infos
    @Override
    @GET
    @Path("/infos")
    public String getInfos() {
        return "Bonjour de la part de Ryadh";
    }

    // uri => http://localhost:9991/ws/rest/users/all
    @Override
    @Path("/all")
    @GET
    @Secured // <-- VERROUILLÉ : Seuls les utilisateurs avec badge peuvent voir la liste
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDtos = new ArrayList<>();

            users.forEach(u -> userDtos.add(Dto.userToDto(u)));

            return Response.status(Response.Status.OK)
                    .header("API REST", "Ecommerce")
                    .header("Content-Type", "application/json")
                    .header("Ecole", "Ensitech")
                    .entity(userDtos)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // uri => http://localhost:9991/ws/rest/users/28
    @GET
    @Path("/{ident}")
    @Secured // <-- VERROUILLÉ : On protège les données individuelles
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getUserById(@PathParam("ident") @DefaultValue("0") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing ID")
                    .build();
        }
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User with id = " + id + " not found").build();
            }
            return Response.status(Response.Status.OK).entity(Dto.userToDto(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    // => http://127.0.0.1:9991/ws/rest/users/create
    // PUBLIC : La porte d'entrée doit rester ouverte pour les nouveaux arrivants
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response createUser(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()
                || user.getFirstname() == null || user.getFirstname().isBlank()
                || user.getLastname() == null || user.getLastname().isBlank()
                || user.getBirthdate() == null ||
                user.getResponse() == null || user.getResponse().isBlank() ||
                user.getQuestion() == null
        ) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid user / user parameters")
                    .build();
        }
        try {
            userService.subscribe(user);
            return Response.status(Response.Status.CREATED).entity(Dto.userToDto(user)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // => http://127.0.0.1:9991/ws/rest/users/update
    @PUT
    @Path("update")
    @Secured // <-- VERROUILLÉ : Personne ne modifie un profil sans être connecté
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response updateProfile(User user) {
        if (user == null
                || user.getFirstname() == null || user.getFirstname().isBlank()
                || user.getLastname() == null || user.getLastname().isBlank()
                || user.getBirthdate() == null
                || user.getEmail() == null || user.getEmail().isBlank()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid user / user parameters")
                    .build();
        }
        try {
            userService.updateProfile(user);
            return Response.status(Response.Status.ACCEPTED)
                    .entity("Profile updated with success.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // => http://127.0.0.1:9991/ws/rest/users/activate/4
    @PATCH
    @Path("/activate/{id}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response activate(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid user id.")
                    .build();
        }
        try {
            userService.activate(id);
            return Response.status(Response.Status.ACCEPTED)
                    .entity("User activated with success.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @PATCH
    @Path("/unsubscribe/{email}")
    @Secured // <-- VERROUILLÉ
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response unsubscribe(@PathParam("email") String email) {
        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing email")
                    .build();
        }
        try {
            userService.unsubscribe(email);
            return Response.status(Response.Status.ACCEPTED)
                    .entity("User unsubscribed with success.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginReq) {
        if (loginReq == null || loginReq.getEmail() == null || loginReq.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Identifiants manquants")
                    .build();
        }

        try {
            User user = userService.getUserByEmail(loginReq.getEmail());

            if (user == null || !PasswordHasher.verifyPassword(loginReq.getPassword(), user.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Email ou mot de passe incorrect")
                        .build();
            }

            Date lastUpdateDate = user.getLastPasswordUpdate();
            if (lastUpdateDate == null || (new Date().getTime() - lastUpdateDate.getTime() > MAX_PASSWORD_AGE_MS)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Mot de passe expiré")
                        .build();
            }

            String token = JwtUtil.generateToken(user.getEmail(), user.getRole().getName());

            NewCookie authCookie = new NewCookie(
                    "AUTH_SESSION",
                    token,
                    "/",
                    null,
                    "Auth Cookie JWT",
                    36000,
                    false,
                    true
            );

            return Response.ok(Dto.userToDto(user))
                    .cookie(authCookie)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/logout/{email}")
    @Secured // <-- VERROUILLÉ : On ne déconnecte que si on est déjà connecté
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response logout(@PathParam("email") String email) {
        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing email")
                    .build();
        }
        try {
            return Response.status(Response.Status.OK)
                    .entity("Logout successful for email: " + email)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/question/{email}/{pwd}")
    // PUBLIC : L'utilisateur a oublié son mot de passe, il n'a donc pas de badge
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestion(@PathParam("email") String email, @PathParam("pwd") String oldPassword) throws Exception {
        if (email == null || email.isBlank() || oldPassword == null || oldPassword.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing email/oldPassword")
                    .build();
        }
        try {
            String question = userService.getQuestion(email, oldPassword);
            return Response.status(Response.Status.OK)
                    .entity(question)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

    }

    @PUT
    @Path("/check/{email}/{pwd}/{response}")
    // PUBLIC : Même logique, c'est pour rétablir un accès perdu
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response checkResponse(@PathParam("email") String email, @PathParam("pwd") String newpwd, @PathParam("response") String response) throws Exception {
        if (email == null || email.isBlank() || newpwd == null || newpwd.isBlank() || response == null || response.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing email/response/new password")
                    .build();
        }
        try {
            User user = userService.getUserByEmail(email);

            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
            }

            String storedResponseHash = user.getResponse();

            if (PasswordHasher.verifySecretResponse(response, storedResponseHash) ) {

                userService.updatePassword(user.getId(), newpwd);

                return Response.status(Response.Status.OK)
                        .entity("Password updated with success.")
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Incorrect response.")
                        .build();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}