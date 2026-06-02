package fr.ensitech.optistage.service;

import org.bson.Document;

public interface ICvService {
    /**
     * Traite et valide le CV avant envoi au stockage NoSQL.
     */
    void saveCv(int studentId, String fileName, String base64Content);

    /**
     * Récupère le document CV de l'étudiant.
     */
    Document getCv(int studentId);
}