package fr.ensitech.optistage.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "administrator")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false, unique = true, length = 48)
    private String login;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "firstname", length = 48)
    private String firstname;

    @Column(name = "lastname", length = 48)
    private String lastname;
}