package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    Page<Itinerary> findByNameLike(String name, Pageable pageable);
}
