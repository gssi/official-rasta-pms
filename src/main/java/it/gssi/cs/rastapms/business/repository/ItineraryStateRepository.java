package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.ItineraryState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItineraryStateRepository extends JpaRepository<ItineraryState, Long> {
    ItineraryState findByName(String name);
}
