package fr.ensitech.optistage.service;

import fr.ensitech.optistage.repository.ICvRepository;
import fr.ensitech.optistage.repository.CvRepository;
import org.bson.Document;

public class CvService implements ICvService {

    private final ICvRepository cvRepository;

    public CvService() {
        this.cvRepository = new CvRepository();
    }

    @Override
    public String saveCv(String fileName, String base64Content) {
        // Validation de sécurité élémentaire
        if (base64Content == null || base64Content.trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du CV en Base64 ne peut pas être vide");
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "cv_candidature.pdf"; // Nom générique de secours
        }

        // Transmission au stockage et récupération de l'ID généré (Mongo ObjectId en String)
        return cvRepository.saveCv(fileName, base64Content);
    }

    @Override
    public Document getCv(String cvId) {
        // On demande désormais à la base de chercher via l'identifiant NoSQL
        return cvRepository.getCv(cvId);
    }
}