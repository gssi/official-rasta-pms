package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.POI;
import it.gssi.cs.rastapms.domain.Sensor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    @Query(value="select s from Sensor s where s.poi.id=?1 and s.name like ?2")
    Page<Sensor> findByPoiIDAndName(Long poiID, String name, Pageable pageable);

    @Query(value="select s from Sensor s where s.poi.id=?1 and s.name = ?2")
    Sensor findByPoiIDAndName(Long poiID, String name);

    @Query(value="select count(*) from Sensor s where s.poi.id=?1")
    long countByPOIID(Long poiID);

    Long poi(@NotNull(message = "point of interest is mandatory") POI poi);
}
