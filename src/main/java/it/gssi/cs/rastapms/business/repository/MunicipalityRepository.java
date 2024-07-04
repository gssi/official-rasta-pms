package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    @Query(value="select m from Municipality m where m.zipcode= ?1")
    Municipality findMunicipalityByZipCode(String zipcode);
}
