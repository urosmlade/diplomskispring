package Diplomski.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Diplomski.jpa.Kategorija;

public interface KategorijaRepository extends JpaRepository<Kategorija, Integer>{
	Kategorija findByNazivIgnoreCase (String naziv);
	
	//Collection<Kategorija> findByKategorija (Kategorija kategorija);

	/*Collection<Kategorija> findByKategorijaNull();
	
	Collection<Kategorija> findByKategorijaNotNullOrderByNaziv();
*/
	
	
}
