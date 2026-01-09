package fr.ensitech.myproject.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "application", catalog = "myproject_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    //TODO ajouter le CV au champ et à la bdd

    @Column(name = "motivation_letter", columnDefinition = "TEXT")
    private String motivationLetter;

    // L'étudiant qui postule
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private User student;

    // L'offre concernée
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    @ToString.Exclude
    private InternshipOffer offer;
}