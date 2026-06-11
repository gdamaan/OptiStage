package fr.ensitech.optistage.utils;

public class SanitizerUtil {

    /**
     * Neutralise les caractères spéciaux HTML pour contrer les injections XSS.
     */
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}