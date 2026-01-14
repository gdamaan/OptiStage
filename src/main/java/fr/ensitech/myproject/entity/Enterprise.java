package fr.ensitech.myproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "enterprise", catalog = "myproject_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "siret", nullable = false, length = 14, unique = true)
    private String siret;

    @Column(name = "sector", nullable = true, length = 50)
    private String sector;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "website", nullable = true, length = 100)
    private String website;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

}