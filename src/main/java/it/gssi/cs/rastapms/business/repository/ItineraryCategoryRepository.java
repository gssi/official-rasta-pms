package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.ItineraryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryCategoryRepository extends JpaRepository<ItineraryCategory, Long> {
    ItineraryCategory findByName(String name);
}
