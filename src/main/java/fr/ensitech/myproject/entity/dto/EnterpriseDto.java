package fr.ensitech.myproject.entity.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EnterpriseDto {
    private Long id;
    private String name;
    private String siret;
    private String sector;
    private String description;
    private String website;

    private Long managerId;
    private String managerName;
    private String managerEmail;
}