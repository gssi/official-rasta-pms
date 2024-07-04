package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value="select i from Image i where i.poi.id=?1 and i.name like ?2")
    Page<Image> findByPoiIDAndName(Long poiID, String name, Pageable pageable);

    @Query(value="select count(*) from Image i where i.poi.id=?1")
    long countByPOIID(Long poiID);
}
