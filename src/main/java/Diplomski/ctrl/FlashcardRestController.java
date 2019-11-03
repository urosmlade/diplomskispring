package Diplomski.ctrl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Diplomski.jpa.Flashcard;
import Diplomski.jpa.Kategorija;
import Diplomski.model.User;
import Diplomski.repository.KategorijaRepository;
import Diplomski.repository.UserRepository;
import Diplomski.reps.FlashcardRepository;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/auth")

public class FlashcardRestController {

	@Autowired
	private KategorijaRepository kategorijaRepository;
	
	@Autowired
	private FlashcardRepository flashcardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@CrossOrigin
	@GetMapping("flashcard")
	public Collection<Flashcard> getAll(){
		return flashcardRepository.findAll(Sort.by("id").descending());
	}
	
	@CrossOrigin
	@DeleteMapping("flashcard/{id}")
	public ResponseEntity<HttpStatus> deleteFlashcard(@PathVariable Integer id){
		if(flashcardRepository.existsById(id)) {
			flashcardRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@CrossOrigin
	@GetMapping("flashcard/{id}")
	public Flashcard getOne(@PathVariable("id") Integer id) {
		return flashcardRepository.getOne(id);

	}
	
	@CrossOrigin
	@PostMapping("flashcard")
	public ResponseEntity<HttpStatus> addFlashcard(@RequestBody Flashcard flashcard){
		flashcardRepository.save(flashcard);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@GetMapping("flashcard/kategorijaBean/{id}")
	public Collection<Flashcard> getByOdgovor(@PathVariable("id") Integer id){
		Kategorija k = kategorijaRepository.getOne(id);
		return flashcardRepository.findByKategorijaBean(k);
	}
	
	@CrossOrigin
	@GetMapping("flashcard/kategorijaBean/naziv/{naziv}")
	public Collection<Flashcard> getByNaziv (@PathVariable("naziv") String naziv){
		Kategorija k = (Kategorija) kategorijaRepository.findByNazivIgnoreCase(naziv);
		
		return flashcardRepository.findByKategorijaBean(k);
	}
	
	
	@CrossOrigin
	@GetMapping("flashcard/autor/username/{username}")
	public Collection<Flashcard> getByAutor (@PathVariable ("username") String username){
		Optional<User> u = userRepository.findByUsername(username);
		return flashcardRepository.findByKorisnikOrderByIdDesc(u);
	}
	

	
	@CrossOrigin
	@PutMapping ("flashcard/{id}")
	public ResponseEntity<HttpStatus> updateFlashcard (@PathVariable ("id") Integer id, @RequestBody Flashcard flashcard){
		if(flashcardRepository.existsById(id)) {
			flashcard.setId(id);
			flashcardRepository.save(flashcard);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@CrossOrigin
	@GetMapping("flashcard/{username}/{privatno}")
	public Collection<Flashcard> getAllPrivatnoByUser(@PathVariable("privatno")Boolean privatno, @PathVariable("username") String username){
		Optional<User> u = userRepository.findByUsername(username);
		flashcardRepository.findByKorisnikOrderByIdDesc(u);
		return flashcardRepository.findByPrivatnoAndKorisnikOrderByIdDesc(privatno,u);
	}
	
	
	@CrossOrigin
	@GetMapping("flashcard/privatno/{bool}")
	public Collection<Flashcard>getAllPrivatno(@PathVariable("bool") Boolean bool){
		return flashcardRepository.findByPrivatnoOrderByIdDesc(bool);
	}
	
	
	@CrossOrigin
	@GetMapping("flashcard/godina/{godina}")
	public Collection<Flashcard> getByGodina(@PathVariable ("godina") String godina){
		return flashcardRepository.findByKategorijaBeanNaziv(godina);
	}
	
	@CrossOrigin
	@GetMapping("flashcard/kategorijaBean/{b}/{godina}")
	public Collection<Flashcard> getByGodinaa(@PathVariable("b") Boolean b,@PathVariable ("godina") String godina){
		return flashcardRepository.findByPrivatnoAndKategorijaBeanNazivOrderByIdDesc(b,godina);
	}
	
	
	
	
	
	
	
}
