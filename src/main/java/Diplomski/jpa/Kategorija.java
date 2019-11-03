package Diplomski.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Diplomski.model.User;

import java.util.List;


/**
 * The persistent class for the kategorija database table.
 * 
 */
@Entity
@NamedQuery(name="Kategorija.findAll", query="SELECT k FROM Kategorija k")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kategorija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="Kategorija_id_generator", sequenceName = "Kategorija_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Kategorija_id_generator")
	private Integer id;

	private String naziv;

	//bi-directional many-to-one association to Flashcard
	@OneToMany(mappedBy="kategorijaBean")
	@JsonIgnore
	private List<Flashcard> flashcards;

	/*//bi-directional many-to-one association to Kategorija
	@ManyToOne
	@JoinColumn(name="potkategorija")
	@JsonIgnore
	private Kategorija kategorija;

	//bi-directional many-to-one association to Kategorija
	@OneToMany(mappedBy="kategorija")
	@JsonIgnore
	private List<Kategorija> kategorijas;*/

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="kategorijas")
	@JsonIgnore
	private List<User> korisniks;

	public Kategorija() {
	}

	
	
	
	public Kategorija(Integer id, String naziv, List<Flashcard> flashcards, Kategorija kategorija,
			List<Kategorija> kategorijas, List<User> korisniks) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.flashcards = flashcards;
	//	this.kategorija = kategorija;
		//this.kategorijas = kategorijas;
		this.korisniks = korisniks;
	}
	
	
	public Kategorija(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Flashcard> getFlashcards() {
		return this.flashcards;
	}

	public void setFlashcards(List<Flashcard> flashcards) {
		this.flashcards = flashcards;
	}

	public Flashcard addFlashcard(Flashcard flashcard) {
		getFlashcards().add(flashcard);
		flashcard.setKategorijaBean(this);

		return flashcard;
	}

	public Flashcard removeFlashcard(Flashcard flashcard) {
		getFlashcards().remove(flashcard);
		flashcard.setKategorijaBean(null);

		return flashcard;
	}

	/*public Kategorija getKategorija() {
		return this.kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public List<Kategorija> getKategorijas() {
		return this.kategorijas;
	}

	public void setKategorijas(List<Kategorija> kategorijas) {
		this.kategorijas = kategorijas;
	}

	public Kategorija addKategorija(Kategorija kategorija) {
		getKategorijas().add(kategorija);
		kategorija.setKategorija(this);

		return kategorija;
	}

	public Kategorija removeKategorija(Kategorija kategorija) {
		getKategorijas().remove(kategorija);
		kategorija.setKategorija(null);

		return kategorija;
	}*/

	public List<User> getKorisniks() {
		return this.korisniks;
	}

	public void setKorisniks(List<User> korisniks) {
		this.korisniks = korisniks;
	}

}