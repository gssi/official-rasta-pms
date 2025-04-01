package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    @Query(value="select p from Parameter p where p.sensor.id=?1 and p.name like ?2")
    Page<Parameter> findBySensorIDAndName(Long sensorID, String name, Pageable pageable);

    @Query(value="select p from Parameter p where p.sensor.id=?1 and p.name like ?2")
    Parameter findBySensorIDAndName(Long sensorID, String name);

    @Query(value="select count(*) from Parameter p where p.sensor.id=?1")
    long countBySensorID(Long poiID);

    @Query(value="select p from Parameter p where p.name = ?1")
    Parameter findParameterByName(String name);

    @Query(value="select p from Parameter p where p.sensor.poi.id=?1 and p.name = ?2")
    Parameter findParameterByPoiIDAndName(Long poiID, String parameterName);

    @Query(value="select p from Parameter p where p.sensor.id=?1")
    List<Parameter> findParametersBySensorId(Long idSensor);
}
