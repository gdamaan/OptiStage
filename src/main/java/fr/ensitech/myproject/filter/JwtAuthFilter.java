package fr.ensitech.myproject.filter;

import fr.ensitech.myproject.annotation.Secured;
import fr.ensitech.myproject.utils.JwtUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
public class JwtAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // 1. On cherche le cookie
        Cookie authCookie = requestContext.getCookies().get("AUTH_SESSION");
        if (authCookie == null) {
            // Pas de badge ? On bloque l'entrée avant même d'arriver au Controller.
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Veuillez vous connecter.").build());
            return;
        }

        // 2. On vérifie le badge JWT
        String email = JwtUtil.extractEmail(authCookie.getValue());
        if (email == null) {
            // Badge falsifié ou expiré -> On bloque.
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Session expirée ou badge falsifié.").build());
            return;
        }

        // On glisse l'email décrypté dans la requête pour que le Controller puisse s'en servir si besoin !
        requestContext.setProperty("userEmail", email);
    }
}