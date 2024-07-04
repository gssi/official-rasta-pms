package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findByNameLike(String name, Pageable pageable);

}
