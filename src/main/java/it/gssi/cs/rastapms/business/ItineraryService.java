package it.gssi.cs.rastapms.business;

import it.gssi.cs.rastapms.domain.*;

import java.util.List;

public interface ItineraryService {

    ResponseGrid<Itinerary> findAllItinerariesPaginated(RequestGrid requestGrid) throws BusinessException;

    Itinerary createItinerary(Itinerary itinerary) throws BusinessException;

    Itinerary findItineraryByID(Long id) throws BusinessException;

    Itinerary updateItinerary(Itinerary itinerary) throws BusinessException;

    void deleteItinerary(Itinerary itinerary) throws BusinessException;

    List<Difficulty> findAllDifficulties() throws BusinessException;

    List<ItineraryState> findAllItineraryStates() throws BusinessException;

    List<ItineraryCategory> findAllItineraryCategories() throws BusinessException;

    List<Itinerary> findAllItineraries() throws BusinessException;

    ItineraryPoint findItineraryPointByNameAndCoordinates(ItineraryPoint itineraryPoint) throws BusinessException;

    Difficulty findItineraryDifficultyByName(String difficultyName) throws BusinessException;

    ItineraryState findItineraryStateByName(String stateName) throws BusinessException;

    ItineraryCategory findItineraryCategoryByName(String categoryName) throws BusinessException;
}
