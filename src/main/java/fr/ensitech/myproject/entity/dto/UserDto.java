package fr.ensitech.myproject.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
    private Boolean isActive;
}
