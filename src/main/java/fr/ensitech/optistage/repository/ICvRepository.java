package fr.ensitech.optistage.repository;

import org.bson.Document;

public interface ICvRepository {

    // Même chose ici, on renvoie une chaîne de caractères (L'ID)
    String saveCv(String fileName, String base64Content);

    // On cherche via la chaîne de caractères
    Document getCv(String cvId);
}