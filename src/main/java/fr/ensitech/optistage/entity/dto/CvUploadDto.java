package fr.ensitech.optistage.entity.dto;

import lombok.Data;

@Data // Cette simple balise génère tous les getters, setters et constructeurs en cachette !
public class CvUploadDto {
    private String fileName;
    private String base64Content;
}