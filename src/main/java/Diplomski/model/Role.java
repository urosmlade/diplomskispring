package Diplomski.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the uloga database table.
 * 
 */
@Entity
@Table(name="uloga")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="uloga_id_generator")
	@SequenceGenerator(name = "uloga_id_generator", sequenceName = "uloga_seq")	
	private Long iduloga;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private RoleName nameuloga;

	public Role() {
	}

	public Long getIduloga() {
		return this.iduloga;
	}

	public void setIduloga(Long iduloga) {
		this.iduloga = iduloga;
	}

	public RoleName getNameuloga() {
		return this.nameuloga;
	}

	public void setNameuloga(RoleName nameuloga) {
		this.nameuloga = nameuloga;
	}



}