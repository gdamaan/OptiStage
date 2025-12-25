package fr.ensitech.myproject.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "question", catalog = "myproject_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Question
{
    @Id
    private int id;

    @Column(name = "question", nullable = false, length = 50)
    private String question;

}
