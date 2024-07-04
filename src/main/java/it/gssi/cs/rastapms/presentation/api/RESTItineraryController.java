package it.gssi.cs.rastapms.presentation.api;

import com.fasterxml.jackson.annotation.JsonView;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.ItineraryService;
import it.gssi.cs.rastapms.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/v1/itineraries")
public class RESTItineraryController {

    private ItineraryService itineraryService;

    public RESTItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @JsonView(Views.ItineraryPublic.class)
    @GetMapping
    public List<Itinerary> findAll() {
        try {
            return itineraryService.findAllItineraries();
        } catch (BusinessException e) {
            throw new ItineraryException("Error in the itineraries list", e);
        }
    }

    @JsonView(Views.ItineraryPublic.class)
    @GetMapping("difficulties")
    public List<Difficulty> findAllDifficulties() {
        try {
            return itineraryService.findAllDifficulties();
        } catch (BusinessException e) {
            throw new POIException("Error in the itinerary difficulties list", e);
        }
    }

    @JsonView(Views.ItineraryPublic.class)
    @GetMapping("states")
    public List<ItineraryState> findAllStates() throws BusinessException {
        try {
            return itineraryService.findAllItineraryStates();
        } catch (BusinessException e) {
            throw new POIException("Error in the itinerary states list", e);
        }
    }

    @JsonView(Views.ItineraryPublic.class)
    @GetMapping("categories")
    public List<ItineraryCategory> findAllCategories() throws BusinessException {
        try {
            return itineraryService.findAllItineraryCategories();
        } catch (BusinessException e) {
            throw new POIException("Error in the itinerary categories list", e);
        }


    }

    @JsonView(Views.ItineraryPublic.class)
    @GetMapping(value = "/{id}")
    public Itinerary findOne(@PathVariable Long id) {
        try {
            return itineraryService.findItineraryByID(id);
        } catch (Exception e) {
            throw new ItineraryNotFoundException("Itinerary not found with id=" + id, e);
        }
    }

    @JsonView(Views.ItineraryPublic.class)
    @PostMapping
    public Itinerary create(@NotNull @Valid @RequestBody Itinerary itinerary) {

        try {
            Difficulty difficulty = itineraryService.findItineraryDifficultyByName(itinerary.getDifficulty().getName());
            itinerary.setDifficulty(difficulty);
            ItineraryState state = itineraryService.findItineraryStateByName(itinerary.getState().getName());
            itinerary.setState(state);
            Set<ItineraryCategory> newItineraryCategories = new HashSet<>();
            for (ItineraryCategory element : itinerary.getCategories()) {
                ItineraryCategory itineraryCategory = itineraryService.findItineraryCategoryByName(element.getName());
                newItineraryCategories.add(itineraryCategory);
            }
            itinerary.setCategories(newItineraryCategories);
            return itineraryService.createItinerary(itinerary);
        } catch (Exception e) {
            throw new ItineraryException("Error in the itinerary POST operation" + itinerary.getName(), e);
        }
    }
    @JsonView(Views.ItineraryPublic.class)
    @PutMapping(value = "/{id}")
    public Itinerary update(@PathVariable Long id, @NotNull @Valid @RequestBody Itinerary itinerary) {
        itinerary.setId(id);
        try {
            Difficulty difficulty = itineraryService.findItineraryDifficultyByName(itinerary.getDifficulty().getName());
            itinerary.setDifficulty(difficulty);
            ItineraryState state = itineraryService.findItineraryStateByName(itinerary.getState().getName());
            itinerary.setState(state);
            Set<ItineraryCategory> newItineraryCategories = new HashSet<>();
            for (ItineraryCategory element : itinerary.getCategories()) {
                ItineraryCategory itineraryCategory = itineraryService.findItineraryCategoryByName(element.getName());
                newItineraryCategories.add(itineraryCategory);
            }
            itinerary.setCategories(newItineraryCategories);
            return itineraryService.updateItinerary(itinerary);
        } catch (Exception e) {
            throw new ItineraryException("Error in the itinerary PUT operation" + itinerary.getName(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Itinerary itinerary = new Itinerary();
        itinerary.setId(id);
        try {
            //The findItineraryByID method invocation is done because if there is no itinerary with that id, an exception is thrown
            itineraryService.findItineraryByID(id);
            itineraryService.deleteItinerary(itinerary);
            return ResponseEntity.ok("itinerary with id= " + id + " deleted");
        } catch (Exception e) {
            throw new ItineraryException("Error in the itinerary DELETE operation with id=" + id, e);
        }
    }

}
