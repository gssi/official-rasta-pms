package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.POICategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POICategoryRepository extends JpaRepository<POICategory, Long> {
    POICategory findByName(String name);
}
