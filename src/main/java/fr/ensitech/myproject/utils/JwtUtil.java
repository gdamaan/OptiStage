package fr.ensitech.myproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // La clé secrète du serveur (Générée automatiquement pour plus de sécurité)
    // Dans un vrai projet, cette clé serait lue depuis un fichier de configuration (application.properties)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Le jeton sera valide pendant 10 heures
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * Fabrique un nouveau JWT.
     * @param email L'email de l'utilisateur (sera stocké dans le "Subject" du jeton)
     * @param role Le rôle de l'utilisateur (stocké comme information supplémentaire)
     * @return Le jeton JWT sous forme de String
     */
    public static String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Le sujet principal du jeton
                .claim("role", role) // On y ajoute le rôle pour éviter de devoir chercher en BDD à chaque requête
                .setIssuedAt(new Date(System.currentTimeMillis())) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Date de péremption
                .signWith(SECRET_KEY) // La signature infalsifiable
                .compact();
    }

    /**
     * Décrypte le jeton et récupère l'email (Subject)
     */
    public static String extractEmail(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            // Si la signature est mauvaise, que le token est expiré ou malformé
            return null;
        }
    }
}