package fr.ensitech.myproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address", catalog = "myproject_db")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "number", nullable = true, length = 6)
	private String number;

	@Column(name = "type_street", nullable = true, length = 10)
	private String typeOfStreet;

	@Column(name = "street", nullable = true, length = 48)
	private String street;

	@Column(name = "postal_code", nullable = false, length = 5)
	private String postalCode;

	@Column(name = "city", nullable = false, length = 32)
	private String city;

	@Column(name = "country", nullable = false, length = 48)
	private String country;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
