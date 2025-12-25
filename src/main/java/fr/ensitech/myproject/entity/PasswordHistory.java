package fr.ensitech.myproject.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "password_history")
@Getter
@Setter
@ToString
public class PasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // Le hash de l'ancien mot de passe
    @Column(name = "old_password_hash", nullable = false, length = 128)
    private String oldPasswordHash;

    // La date du changement de mot de passe
    @Column(name = "change_date", nullable = false)
    private Date changeDate;

}