package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.ItineraryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItineraryPointRepository extends JpaRepository<ItineraryPoint, Long> {
    @Query(value="select ip from ItineraryPoint ip where ip.name like ?1 and ip.latitude = ?2 and ip.longitude = ?3")
    ItineraryPoint findByNameAndCoordinates(String name, double latitude, double longitude);
}
