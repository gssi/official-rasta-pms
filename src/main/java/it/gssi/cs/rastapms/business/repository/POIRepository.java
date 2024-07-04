package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.POI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POIRepository extends JpaRepository<POI, Long> {

    Page<POI> findByNameLike(String name, Pageable pageable);

}
