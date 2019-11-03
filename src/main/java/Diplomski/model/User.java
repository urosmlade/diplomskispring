package Diplomski.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Diplomski.jpa.Flashcard;
import Diplomski.jpa.Kategorija;

import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@Table(name = "korisnik", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_generator")
	@SequenceGenerator(name="user_id_generator", sequenceName = "kor_seq")		private Long id;

	private String email;

	private String ime;

	private String password;

	private String prezime;

	private String username;

	//bi-directional many-to-one association to Flashcard
	@OneToMany(mappedBy="korisnik")
	@JsonIgnore
	private List<Flashcard> flashcards;

	//bi-directional many-to-many association to Kategorija
	@ManyToMany
	@JoinTable(
		name="korisnikkategorija"
		, joinColumns={
			@JoinColumn(name="idkorisnika")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idkategorije")
			}
		)
	private List<Kategorija> kategorijas;

	
	public User() {
	}
	
	public User(String name, String prezime, String username, String email, String password) {
        this.ime = name;
        this.prezime = prezime;
        this.username = username;
        this.email = email;
        this.password = password;
    }
	
	
	
	
	


	public User(String username) {
		super();
		this.username = username;
	}

	public User(Long id, List<Kategorija> kategorijas) {
		super();
		this.id = id;
		this.kategorijas = kategorijas;
	}

	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Flashcard> getFlashcards() {
		return this.flashcards;
	}

	public void setFlashcards(List<Flashcard> flashcards) {
		this.flashcards = flashcards;
	}

	public Flashcard addFlashcard(Flashcard flashcard) {
		getFlashcards().add(flashcard);
		flashcard.setKorisnik(this);

		return flashcard;
	}

	public Flashcard removeFlashcard(Flashcard flashcard) {
		getFlashcards().remove(flashcard);
		flashcard.setKorisnik(null);

		return flashcard;
	}

	public List<Kategorija> getKategorijas() {
		return this.kategorijas;
	}

	public void setKategorijas(List<Kategorija> kategorijas) {
		this.kategorijas = kategorijas;
	}

}