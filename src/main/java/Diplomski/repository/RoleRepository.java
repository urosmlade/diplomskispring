package Diplomski.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import Diplomski.model.Role;
import Diplomski.model.RoleName;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByNameuloga(RoleName roleName);
}
