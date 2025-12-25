package fr.ensitech.myproject.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import java.util.logging.Logger; // Utilisé ici pour la gestion des logs d'erreur
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.crypto.params.KeyParameter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe utilitaire pour gérer le hachage et la vérification des mots de passe
 * en utilisant l'algorithme sécurisé Argon2id.
 */
public class PasswordHasher {

    // --- Configuration d'Argon2id (Recommandations du marché) ---
    private static final Argon2 ARGON2 = Argon2Factory.create(Argon2Types.ARGON2id);
    private static final int ITERATIONS = 10;   // t=10 : Nombre d'itérations
    private static final int MEMORY = 65536;    // m=65536 : Quantité de mémoire en KiB (64 MB)
    private static final int PARALLELISM = 1;   // p=1 : Threads utilisés

    // Logger pour éviter de relâcher les informations sensibles dans la console (comme les mots de passe)
    private static final Logger LOGGER = Logger.getLogger(PasswordHasher.class.getName());

    // Empêche l'instanciation de cette classe utilitaire
    private PasswordHasher() {}

    /**
     * Hache un mot de passe en texte brut en utilisant Argon2id.
     * Le sel, la mémoire, les itérations et la parallélisation sont encodés dans la chaîne de sortie.
     * * @param rawPassword Le mot de passe en clair.
     * @return Le hachage complet prêt à être stocké en base de données.
     */
    public static String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            return null;
        }

        char[] passwordChars = rawPassword.toCharArray();
        String hash = null;
        try {
            // La méthode hash gère le salage (salt) automatiquement
            hash = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, passwordChars);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors du hachage du mot de passe: " + e.getMessage());
        } finally {
            // Sécurité : Il est essentiel de nettoyer le tableau de caractères
            ARGON2.wipeArray(passwordChars);
        }
        return hash;
    }

    /**
     * Vérifie si un mot de passe en texte brut correspond au hachage stocké.
     * * @param rawPassword Le mot de passe en clair soumis par l'utilisateur.
     * @param storedHash Le hachage stocké dans la base de données.
     * @return true si le mot de passe correspond au hachage, false sinon.
     */
    public static boolean verifyPassword(String rawPassword, String storedHash) {
        if (rawPassword == null || rawPassword.isEmpty() || storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        char[] passwordChars = rawPassword.toCharArray();
        boolean verified = false;
        try {
            // La méthode verify extrait le sel et les paramètres du storedHash et vérifie la correspondance.
            verified = ARGON2.verify(storedHash, passwordChars);
        } catch (Exception e) {
            // Ceci peut arriver si le format du hash stocké est invalide.
            LOGGER.severe("Erreur lors de la vérification du mot de passe : Hash invalide ? " + e.getMessage());
            verified = false;
        } finally {
            // Sécurité : Il est essentiel de nettoyer le tableau de caractères
            ARGON2.wipeArray(passwordChars);
        }
        return verified;
    }

    /**
     * Vérifie si le hachage doit être regénéré (par exemple, si les paramètres par défaut ont changé).
     * Optionnel, mais bonne pratique.
     */
    public static boolean needsRehash(String storedHash) {
        return ARGON2.needsRehash(storedHash, ITERATIONS, MEMORY, PARALLELISM);
    }


    // --- Constantes pour scrypt (Paramètres à définir) ---
    private static final int SCRYPT_N = 16384;  // Coût CPU/Mémoire (puissance de 2)
    private static final int SCRYPT_R = 8;     // Block size
    private static final int SCRYPT_P = 1;     // Parallelization (doit être >= 1)
    private static final int SCRYPT_KEY_LEN = 32; // Longueur de la clé dérivée (256 bits)
    private static final int SALT_LENGTH = 16; // Longueur du sel (128 bits)
    private static final SecureRandom RANDOM = new SecureRandom();

    // Format pour stocker le hash avec le sel (ex: Base64(salt):Base64(hash))
    private static final String SEPARATOR = ":";

    /**
     * Hache une réponse secrète en utilisant l'algorithme scrypt.
     * @param secretResponse La réponse secrète en clair.
     * @return La chaîne contenant le sel et le hash, séparés par un ':' (format Base64).
     */
    public static String hashSecretResponse(String secretResponse) {
        if (secretResponse == null || secretResponse.isEmpty()) {
            return null;
        }

        // 1. Générer un sel aléatoire
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);

        // 2. Hacher avec scrypt
        byte[] hashBytes = SCrypt.generate(
                secretResponse.getBytes(StandardCharsets.UTF_8),
                salt,
                SCRYPT_N,
                SCRYPT_R,
                SCRYPT_P,
                SCRYPT_KEY_LEN
        );

        // 3. Encoder et combiner le sel et le hash pour le stockage
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hashBytes);

        return encodedSalt + SEPARATOR + encodedHash;
    }

    /**
     * Vérifie si une réponse en clair correspond à un hash scrypt stocké.
     * @param rawResponse La réponse en clair soumise.
     * @param storedHashAndSalt La chaîne stockée contenant le sel et le hash (ex: "salt:hash").
     * @return true si la réponse est correcte.
     */
    public static boolean verifySecretResponse(String rawResponse, String storedHashAndSalt) {
        if (rawResponse == null || rawResponse.isEmpty() || storedHashAndSalt == null || !storedHashAndSalt.contains(SEPARATOR)) {
            return false;
        }

        try {
            String[] parts = storedHashAndSalt.split(SEPARATOR);
            String encodedSalt = parts[0];
            String encodedHash = parts[1];

            // 1. Décoder le sel stocké
            byte[] salt = Base64.getDecoder().decode(encodedSalt);

            // 2. Calculer le hash de la réponse soumise avec le sel récupéré
            byte[] submittedHashBytes = SCrypt.generate(
                    rawResponse.getBytes(StandardCharsets.UTF_8),
                    salt,
                    SCRYPT_N,
                    SCRYPT_R,
                    SCRYPT_P,
                    SCRYPT_KEY_LEN
            );

            // 3. Comparer le hash calculé avec le hash stocké
            byte[] storedHashBytes = Base64.getDecoder().decode(encodedHash);

            // Utiliser une comparaison constante pour éviter les attaques temporelles (timing attacks)
            return java.util.Arrays.equals(submittedHashBytes, storedHashBytes);

        } catch (Exception e) {
            // En cas d'erreur de décodage ou autre
            return false;
        }
    }
}