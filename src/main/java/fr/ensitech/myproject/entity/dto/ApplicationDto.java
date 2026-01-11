package fr.ensitech.myproject.entity.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private Date applyDate;
    private String status;
    private String motivationLetter;

    // Infos de l'étudiant (pour le recruteur)
    private Long studentId;
    private String studentName;

    // Infos de l'offre (pour l'étudiant)
    private Long offerId;
    private String offerTitle;
    private String enterpriseName;
}