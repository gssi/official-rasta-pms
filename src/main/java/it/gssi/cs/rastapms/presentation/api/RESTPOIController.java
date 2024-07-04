package it.gssi.cs.rastapms.presentation.api;

import com.fasterxml.jackson.annotation.JsonView;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/pois")
public class RESTPOIController {

    private POIService poiService;

    public RESTPOIController(POIService poiService) {
        this.poiService = poiService;
    }

    @JsonView(Views.POIPublic.class)
    @GetMapping
    public List<POI> findAll() {
        try {
            return poiService.findAllPOIs();
        } catch (BusinessException e) {
            throw new POIException("Error in the POIs list", e);
        }
    }
    @JsonView(Views.POIPublic.class)
    @GetMapping("categories")
    public List<POICategory> findAllCategories() {
        try {
            return poiService.findAllPOICategories();
        } catch (BusinessException e) {
            throw new POIException("Error in the POI categories list", e);
        }
    }

    @JsonView(Views.POIPublic.class)
    @GetMapping("municipalities")
    public List<Municipality> findAllMunicipalities() {
        try {
            return poiService.findAllMunicipalities();
        } catch (BusinessException e) {
            throw new POIException("Error in the municipalities list", e);
        }
    }

    @JsonView(Views.POIPublic.class)
    @GetMapping(value = "/{id}")
    public POI findOne(@PathVariable Long id) {
        try {
            return poiService.findPointOfInterestByID(id);
        } catch (Exception e) {
            throw new POINotFoundException("POI not found with id=" + id, e);
        }
    }

    @JsonView(Views.POIPublic.class)
    @PostMapping
    public POI create(@NotNull @Valid @RequestBody POI poi) {
        try {
            Municipality municipality = poiService.findMunicipalityByZipCode(poi.getMunicipality().getZipcode());
            poi.setMunicipality(municipality);
            Set<POICategory> newPOICategories = new HashSet<>();
            for (POICategory element : poi.getCategories()) {
                POICategory poiCategory = poiService.findPOICategoryByName(element.getName());
                newPOICategories.add(poiCategory);
            }
            poi.setCategories(newPOICategories);
            return poiService.createPointOfInterest(poi);
        } catch (Exception e) {
            throw new POIException("Error in the POI POST operation" + poi.getName(), e);
        }
    }

    @JsonView(Views.POIPublic.class)
    @PutMapping(value = "/{id}")
    public POI update(@PathVariable Long id, @NotNull @Valid @RequestBody POI poi) {
        poi.setId(id);
        try {
            Municipality municipality = poiService.findMunicipalityByZipCode(poi.getMunicipality().getZipcode());
            poi.setMunicipality(municipality);
            Set<POICategory> newPOICategories = new HashSet<>();
            for (POICategory element : poi.getCategories()) {
                POICategory poiCategory = poiService.findPOICategoryByName(element.getName());
                newPOICategories.add(poiCategory);
            }
            poi.setCategories(newPOICategories);
            return poiService.updatePointOfInterest(poi);
        } catch (Exception e) {
            throw new POIException("Error in the POI PUT operation" + poi.getName(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        POI poi = new POI();
        poi.setId(id);
        try {
            //The findPointOfInterestByID method invocation is done because if there is no poi with that id, an exception is thrown
            poiService.findPointOfInterestByID(id);
            poiService.deletePointOfInterest(poi);
            return ResponseEntity.ok("POI with id= " + id + " deleted");
        } catch (Exception e) {
            throw new POIException("Error in the POI DELETE operation with id=" + id, e);
        }
    }

}
