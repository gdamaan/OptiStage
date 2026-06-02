package fr.ensitech.optistage.entity.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private Date applyDate;
    private String status;
    private String motivationLetter;

    // Le fameux lien vers le CV NoSQL
    private String cvId;

    // Infos de l'étudiant (pour le recruteur)
    private Long studentId;
    private String studentName;

    // Infos de l'offre (pour l'étudiant)
    private Long offerId;
    private String offerTitle;
    private String enterpriseName;
}