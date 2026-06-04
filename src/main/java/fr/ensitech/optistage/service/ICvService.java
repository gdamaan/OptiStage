package fr.ensitech.optistage.service;

import org.bson.Document;

public interface ICvService {

    // Sauvegarde le CV et renvoie l'ID généré par MongoDB
    String saveCv(String fileName, String base64Content);

    // Récupère le CV grâce à cet ID
    Document getCv(String cvId);
}