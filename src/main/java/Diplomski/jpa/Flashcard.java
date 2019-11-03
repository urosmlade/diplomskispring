package Diplomski.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Diplomski.model.User;


/**
 * The persistent class for the flashcard database table.
 * 
 */
@Entity
@NamedQuery(name="Flashcard.findAll", query="SELECT f FROM Flashcard f")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flashcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="flashcard_id_generator", sequenceName = "flashcard_seq", allocationSize = 1) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flashcard_id_generator")
	private Integer id;

	private String odgovor;

	private String pitanje;

	private Boolean privatno;

	//bi-directional many-to-one association to Kategorija
	@ManyToOne
	@JoinColumn(name="kategorija")
	private Kategorija kategorijaBean;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="autor")
	private User korisnik;

	public Flashcard() {
	}

	public Flashcard(Integer id, String odgovor, String pitanje, Boolean privatno, Kategorija kategorijaBean,
			User korisnik) {
		super();
		this.id = id;
		this.odgovor = odgovor;
		this.pitanje = pitanje;
		this.privatno = privatno;
		this.kategorijaBean = kategorijaBean;
		this.korisnik = korisnik;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOdgovor() {
		return this.odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
	}

	public String getPitanje() {
		return this.pitanje;
	}

	public void setPitanje(String pitanje) {
		this.pitanje = pitanje;
	}

	public Boolean getPrivatno() {
		return this.privatno;
	}

	public void setPrivatno(Boolean privatno) {
		this.privatno = privatno;
	}

	public Kategorija getKategorijaBean() {
		return this.kategorijaBean;
	}

	public void setKategorijaBean(Kategorija kategorijaBean) {
		this.kategorijaBean = kategorijaBean;
	}

	public User getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(User korisnik) {
		this.korisnik = korisnik;
	}

}