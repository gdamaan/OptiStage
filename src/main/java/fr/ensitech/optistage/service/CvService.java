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
    public void saveCv(int studentId, String fileName, String base64Content) {
        // Validation de sécurité élémentaire
        if (base64Content == null || base64Content.trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du CV en Base64 ne peut pas être vide");
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "cv_etudiant_" + studentId + ".pdf";
        }

        // Transmission au stockage
        cvRepository.saveCv(studentId, fileName, base64Content);
    }

    @Override
    public Document getCv(int studentId) {
        return cvRepository.getCv(studentId);
    }
}