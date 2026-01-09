package fr.ensitech.myproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user", catalog = "myproject_db")
@NamedQueries({
	@NamedQuery(name = "User::findAll", query = "from User u"),
	@NamedQuery(name = "User::findByBirthdate", query = "from User u where u.birthdate = :birthdate"),
	@NamedQuery(name = "User::findByEmail", query = "from User u where u.email = :email")
})
@Getter @Setter @ToString
public class User {

    public User() {
        this.addresses = new ArrayList<Address>();
    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstname", nullable = false, length = 48)
	private String firstname;

	@Column(name = "lastname", nullable = false, length = 48)
	private String lastname;

	@Column(name = "email", nullable = false, length = 48, unique = true)
	private String email;

	@Column(name = "pwd", nullable = false, length = 32)
	private String password;

	@Column(name = "birthdate", nullable = true)
	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;

	@Column(name = "active", nullable = false)
	private Boolean isActive;

    @Column(name = "role", nullable = false, length = 16)
    private String role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Address> addresses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "response",nullable = false, length = 32)
    private String response;

    @Column(name = "last_password_update")
    private Date lastPasswordUpdate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Enterprise enterprise;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Application> applications;
}
