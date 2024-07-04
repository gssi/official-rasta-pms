package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}
