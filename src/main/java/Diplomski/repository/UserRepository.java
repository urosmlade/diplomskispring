package Diplomski.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Diplomski.jpa.Kategorija;
import Diplomski.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	  Optional<User> findByUsername(String username);
	  
	  Boolean existsByUsername(String username);
	  
	  Boolean existsByEmail(String email);
	  
	  List<User> findAllByUsername(String username);
	  
	  @Query("select username from User")
	  List<String> findUsername();
	  
	 
	  Collection<User> findByUsernameStartingWith(String username);
	  	  
	  Collection<User> findByUsernameAndKategorijasNaziv (String username, String naziv);
	  
	  
	  Optional<User> findById(Long id);

	  User findByEmail(String email);
	  
	  @Query("select kategorijas from User where username = ?1")
	  Collection<Kategorija> gett(String username);
	  
	  

}
