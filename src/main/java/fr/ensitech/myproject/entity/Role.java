package fr.ensitech.myproject.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter @Setter @NoArgsConstructor @ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;
}