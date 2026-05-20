package fr.ensitech.myproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "internship", catalog = "myproject_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Internship  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", unique = true, nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id")
    private User professor;

    @Column (name = "dateDebut", nullable = false)
    private Date dateDebut;

    @Column(name = "dateFin", nullable = false)
    private Date dateFin;

    @Column(name = "missions", columnDefinition = "TEXT", nullable = false)
    private String missions;

    @Column(name = "convention_status", nullable = false)
    private String conventionStatus = "EN_COURS";

    @Column(name = "note_finale")
    private Double note_finale;

    @Column(name = "rapport_tuteur", columnDefinition = "TEXT")
    private String rapport_tuteur;

    @Column(name = "estValide", nullable = false)
    private Boolean estValide = false;


}