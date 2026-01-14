package fr.ensitech.myproject.entity.dto;

import lombok.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InternshipOfferDto {
    private Long id;
    private String title;
    private String description;
    private String ville;
    private Date startDate;
    private Date endDate;
    private double salary;
    private String enterpriseName; // On ne prend que le nom, pas toute l'entité Enterprise
    private Long enterpriseId; // Pour la création (Input)
}