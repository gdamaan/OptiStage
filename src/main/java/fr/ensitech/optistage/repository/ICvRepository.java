package fr.ensitech.optistage.repository;

import org.bson.Document;

public interface ICvRepository {
    /**
     * Sauvegarde ou écrase le CV d'un étudiant.
     */
    void saveCv(int studentId, String fileName, String base64Content);

    /**
     * Récupère le document CV d'un étudiant.
     */
    Document getCv(int studentId);
}