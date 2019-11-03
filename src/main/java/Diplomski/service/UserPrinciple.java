package Diplomski.service;

import java.util.List;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Diplomski.jpa.Flashcard;
import Diplomski.model.User;

public class UserPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;
    
    private String prezime;

    private String username;

    private String email;
    
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    
	private List<Flashcard> flashcards;

    public UserPrinciple(Long id, String name, String prezime,
			    		String username, String email, String password, 
			    		//Collection<? extends GrantedAuthority> authorities, 
			    		List<Flashcard> flashcards) {
        this.id = id;
        this.name = name;
        this.prezime = prezime;
        this.username = username;
        this.email = email;
        this.password = password;
      //  this.authorities = authorities;
        this.flashcards = flashcards;
    }

    public static UserPrinciple build(User user) {
      /*  List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getNameuloga().name())
        ).collect(Collectors.toList());
*/
        return new UserPrinciple(
                user.getId(),
                user.getIme(),
                user.getPrezime(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                //authorities,
                user.getFlashcards()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPrezime() {
    	return prezime;
    }
    
   
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

	public List<Flashcard> getFlashcards() {
		return flashcards;
	}
	}
	
	
	