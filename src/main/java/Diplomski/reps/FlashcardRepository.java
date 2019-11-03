package Diplomski.reps;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Diplomski.jpa.Flashcard;
import Diplomski.jpa.Kategorija;
import Diplomski.model.User;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
	Collection<Flashcard> findByKategorijaBean(Kategorija k);
	
	Collection<Flashcard> findByKategorijaBeanNaziv(String k);
	
	Collection<Flashcard> findByKorisnikOrderByIdDesc(Optional<User> u);
	
	Collection<Flashcard> findByPrivatnoOrderByIdDesc(Boolean bool);
	
	Collection<Flashcard> findByPrivatnoAndKorisnikOrderByIdDesc(Boolean privatno, Optional<User> korisnik);

	
	Collection<Flashcard> findByPrivatnoAndKategorijaBeanNazivOrderByIdDesc(Boolean privatno, String naziv);

	
	
}
