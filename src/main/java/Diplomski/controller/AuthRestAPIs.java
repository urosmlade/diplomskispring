package Diplomski.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Diplomski.jpa.Kategorija;
import Diplomski.jwt.JwtProvider;
import Diplomski.model.Role;
import Diplomski.model.User;
import Diplomski.repository.KategorijaRepository;
import Diplomski.repository.RoleRepository;
import Diplomski.repository.UserRepository;
import Diplomski.request.LoginForm;
import Diplomski.request.SignUpForm;
import Diplomski.response.JwtResponse;
import Diplomski.response.ResponseMessage;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	KategorijaRepository kategorijaRepository;

	@CrossOrigin
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Username vec postoji, molimo izaberite drugi!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Email vec postoji, molimo izaberite drugi!"),
					HttpStatus.BAD_REQUEST);
		}
		
		

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getPrezime() , signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		/*Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByNameuloga(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "pm":
				Role pmRole = roleRepository.findByNameuloga(RoleName.ROLE_PM)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByNameuloga(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});*/

		//user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@GetMapping("korisnik")
	public Collection<User> getAll() {
		return userRepository.findAll();
	}


	
	
	
	
	
	@CrossOrigin
	@GetMapping("korisnik/username/{username}")
	public Optional<User> getOne(@PathVariable("username") String username) {
		return userRepository.findByUsername(username);
	}
	
	
	
	
	/*
	@CrossOrigin
	@GetMapping("uloga")
	public Collection<Role> getAllUloga() {
		return roleRepository.findAll();
	}


	@CrossOrigin
	@GetMapping("uloga/{id}")
	public Role getUloga(@PathVariable("id") Long id) {
		return roleRepository.getOne(id);
	}*/

	@CrossOrigin
	@PutMapping ("korisnik/{id}")
	public ResponseEntity<HttpStatus> updateKorisnik (@PathVariable ("id") Long id, @RequestBody User user){
		if(userRepository.existsById(id)) {
			user.setId(id);
			userRepository.save(user);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin
	@GetMapping("korisnik/searchusername")
	public List<String> getAllime() {
		return userRepository.findUsername();
	}
	
	
	/*
	@CrossOrigin
	@GetMapping("korisnik/slajs")
	public Page<User> showPage (@RequestParam(defaultValue="0") int page){
		return userRepository.findAll(PageRequest.of(page, 2));
	}*/
	
	/*
	@CrossOrigin
	@GetMapping("korisnik/lajk/")
	public Collection<User> f(){
		return null;
	}
	*/
	
	
	
	@CrossOrigin
	@GetMapping("korisnik/lajk/{lajk}")
	public Collection<User> findUsernameLike(@PathVariable("lajk") String lajk){
		return userRepository.findByUsernameStartingWith(lajk);
	}
	
	
	@CrossOrigin
	@GetMapping("korisnik/kategorija/{username}/{kategorija}")
	public Collection<User> findkategorija (@PathVariable("username") String username,@PathVariable("kategorija") String kategorija){
		return userRepository.findByUsernameAndKategorijasNaziv(username, kategorija);
	}
	
	
	@CrossOrigin
	@PostMapping("korisnik/username/{email}/{naziv}")
	public ResponseEntity<HttpStatus>addKorisnikKategorija(@PathVariable("email")String email, @PathVariable("naziv")String naziv, @RequestBody Kategorija kategorija){
		User user = userRepository.findByEmail(email);
		kategorija = kategorijaRepository.findByNazivIgnoreCase(naziv);
		kategorija.getKorisniks().add(user);
		user.getKategorijas().add(kategorija);
		userRepository.save(user);
		kategorijaRepository.save(kategorija);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@GetMapping("korisnik/username/kategorije/{username}")
	public Collection<Kategorija>gege(@PathVariable("username")String username){
		return userRepository.gett(username);
	}
	
	
	@CrossOrigin
	@DeleteMapping("korisnik/username/{email}/{naziv}")
	public ResponseEntity<HttpStatus>removeKorisnikKategorija(@PathVariable("email")String email, @PathVariable("naziv")String naziv){
		User user = userRepository.findByEmail(email);
		Kategorija kategorija = kategorijaRepository.findByNazivIgnoreCase(naziv);
		kategorija.getKorisniks().remove(user);
		user.getKategorijas().remove(kategorija);
		userRepository.save(user);
		kategorijaRepository.save(kategorija);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
	
	
	
}









