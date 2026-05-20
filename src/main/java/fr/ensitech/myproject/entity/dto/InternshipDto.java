package fr.ensitech.myproject.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipDto {
    private Long id;
    private String conventionStatus;
    private Double finalGrade;
    private String tutorFeedback;

    // Données croisées utiles pour l'affichage Front-end
    private Long applicationId;
    private String studentName;
    private String offerTitle;
    private String enterpriseName;

    private Long academicTutorId;
    private String academicTutorName;
}