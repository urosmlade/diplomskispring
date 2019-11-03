package Diplomski.ctrl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Diplomski.jpa.Kategorija;
import Diplomski.repository.KategorijaRepository;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/auth")
public class KategorijaRestController {

	@Autowired
	KategorijaRepository kategorijaRepository;
	
	@CrossOrigin
	@GetMapping("kategorija")
	public Collection<Kategorija>getAll(){
		return kategorijaRepository.findAll(Sort.by("naziv"));
	}
	
	@CrossOrigin
	@GetMapping("kategorija/naziv/{naziv}")
	public Kategorija getByNaziv(@PathVariable ("naziv") String naziv){
		return kategorijaRepository.findByNazivIgnoreCase(naziv);
	}
	
	@CrossOrigin
	@PutMapping ("kategorija/{id}")
	public ResponseEntity<HttpStatus> updateKategorija (@PathVariable ("id") Integer id, @RequestBody Kategorija kategorija){
		if(kategorijaRepository.existsById(id)) {
			kategorija.setId(id);
			kategorijaRepository.save(kategorija);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	/*@CrossOrigin
	@GetMapping("kategorija/drugakolona/{naziv}")
	public Collection<Kategorija> getByKategorija(@PathVariable("naziv") String naziv) {
		Kategorija k = kategorijaRepository.findByNazivIgnoreCase(naziv);
		return kategorijaRepository.findByKategorija(k);
	}
	*/
	/*@CrossOrigin
	@GetMapping("kategorija/drugakolona")
	public Collection<Kategorija> getDrugaKolona(){
		return kategorijaRepository.findByKategorijaNotNullOrderByNaziv();
		
	}
	
	
	
	@CrossOrigin
	@GetMapping("kategorija/prvakolona")
	public Collection<Kategorija> getSamoKategorija(){
		return kategorijaRepository.findByKategorijaNull();
		
	}
	
*/
	
}
