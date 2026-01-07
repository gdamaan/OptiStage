package fr.ensitech.myproject.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "administrator", catalog = "myproject_db")
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