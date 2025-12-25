package fr.ensitech.myproject.controller.rest;

import fr.ensitech.myproject.entity.User;
import fr.ensitech.myproject.entity.dto.UserDto;
import fr.ensitech.myproject.service.IUserService;
import fr.ensitech.myproject.service.UserService;
import fr.ensitech.myproject.utils.Dto;
import fr.ensitech.myproject.utils.PasswordHasher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> userDtos = new ArrayList<>();

            // boucle avec foreach (Depuis Java 7)
            //for (User user : users) {
            //    userDtos.add(Dto.userToDto(user));
            //}

            // boucle avec l'API Expressions Lambda (Depuis Java 8)
            users.forEach(u -> userDtos.add(Dto.userToDto(u)));

            //return Response.ok(users).build();
            return Response.status(Response.Status.OK)
                    .header("API REST", "Ecommerce")
                    .header("Content-Type", "application/json")
                    .header("Ecole", "Ensitech")
                    .entity(userDtos)
                    .build();
        } catch (Exception e) {
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // uri => http://localhost:9991/ws/rest/users/28
    @GET
    @Path("/{ident}")
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
            //return Response.ok(Dto.userToDto(user)).build();
            //return Response.ok().entity(Dto.userToDto(user)).build();
            return Response.status(Response.Status.OK).entity(Dto.userToDto(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    // => http://127.0.0.1:9991/ws/rest/users/create
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
    @Path("/login/{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response login(@PathParam("email") String email, @PathParam("password") String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("invalid or missing email/password")
                    .build();
        }
        try {
            User user = userService.getUserByEmail(email);


            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("invalid email/password")
                        .build();
            }

            if (!PasswordHasher.verifyPassword(password, user.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("invalid email/password")
                        .build();
            }
            Date lastUpdateDate = user.getLastPasswordUpdate(); // Assumez que ce getter existe

            if (lastUpdateDate == null) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Password expiration date is missing. Please update your password.")
                        .build();
            }

            long ageMs = new Date().getTime() - lastUpdateDate.getTime();

            if (ageMs > MAX_PASSWORD_AGE_MS) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Password expired. Please use the /check endpoint to update your password.")
                        .build();
            }

            // Si tout est bon
            return Response.status(Response.Status.OK)
                    .entity("Login successful." + Dto.userToDto(user))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @POST
    @Path("/logout/{email}")
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
