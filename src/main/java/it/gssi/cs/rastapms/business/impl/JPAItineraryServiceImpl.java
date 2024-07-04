package it.gssi.cs.rastapms.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.ItineraryService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.business.repository.*;
import it.gssi.cs.rastapms.domain.*;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class JPAItineraryServiceImpl implements ItineraryService {
    private static final String PARTENZA = "Partenza";
    private static final String ARRIVO = "Arrivo";

    private ItineraryPointRepository itineraryPointRepository;
    private ItineraryRepository itineraryRepository;
    private DifficultyRepository difficultyRepository;
    private ItineraryStateRepository itineraryStateRepository;
    private ItineraryCategoryRepository itineraryCategoryRepository;

    private ObjectMapper objectMapper;

    public JPAItineraryServiceImpl(ItineraryPointRepository itineraryPointRepository, ItineraryRepository itineraryRepository, DifficultyRepository difficultyRepository, ItineraryStateRepository itineraryStateRepository, ItineraryCategoryRepository itineraryCategoryRepository, ObjectMapper objectMapper) {
        this.itineraryPointRepository = itineraryPointRepository;
        this.itineraryRepository = itineraryRepository;
        this.difficultyRepository = difficultyRepository;
        this.itineraryStateRepository = itineraryStateRepository;
        this.itineraryCategoryRepository = itineraryCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseGrid<Itinerary> findAllItinerariesPaginated(RequestGrid requestGrid) throws BusinessException {
        Pageable pageable = PageRequest.of(requestGrid.getStartPage().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<Itinerary> page = itineraryRepository.findByNameLike(Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<Itinerary> response = new ResponseGrid<>(requestGrid.getDraw(), itineraryRepository.count(), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public Itinerary createItinerary(Itinerary itinerary) throws BusinessException {
        try {
            FeatureCollection featureCollection = objectMapper.readValue(new ByteArrayInputStream(itinerary.getMap().getBytes()), FeatureCollection.class);
            ItineraryPoint start = getItineraryPointByName(featureCollection, PARTENZA);
            ItineraryPoint end = getItineraryPointByName(featureCollection, ARRIVO);
            itinerary.setStart(start);
            itinerary.setEnd(end);

            ItineraryPoint startRetrieved = itineraryPointRepository.findByNameAndCoordinates(start.getName(), start.getLatitude(), start.getLongitude());
            if (startRetrieved != null) {
                itinerary.setStart(startRetrieved);
            }
            ItineraryPoint endRetrieved = itineraryPointRepository.findByNameAndCoordinates(end.getName(), end.getLatitude(), end.getLongitude());
            if (endRetrieved != null) {
                itinerary.setEnd(endRetrieved);
            }
            return itineraryRepository.save(itinerary);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Itinerary findItineraryByID(Long id) throws BusinessException {
        return itineraryRepository.findById(id).get();
    }

    @Override
    public Itinerary updateItinerary(Itinerary itinerary) throws BusinessException {
        //itineraryRepository.save(itinerary);
        //Viene invocato il metodo createItinerary perché il metodo invocato nel repository è lo stesso ovvero save
        return createItinerary(itinerary);
    }

    @Override
    public void deleteItinerary(Itinerary itinerary) throws BusinessException {
        itineraryRepository.delete(itinerary);
    }

    @Override
    public List<Difficulty> findAllDifficulties() throws BusinessException {
        return difficultyRepository.findAll();
    }

    @Override
    public List<ItineraryState> findAllItineraryStates() throws BusinessException {
        return itineraryStateRepository.findAll();
    }

    @Override
    public List<ItineraryCategory> findAllItineraryCategories() throws BusinessException {
        return itineraryCategoryRepository.findAll();
    }

    @Override
    public List<Itinerary> findAllItineraries() throws BusinessException {
        return itineraryRepository.findAll();
    }

    @Override
    public ItineraryPoint findItineraryPointByNameAndCoordinates(ItineraryPoint itineraryPoint) throws BusinessException {
        return itineraryPointRepository.findByNameAndCoordinates(itineraryPoint.getName(), itineraryPoint.getLatitude(), itineraryPoint.getLongitude());
    }

    @Override
    public Difficulty findItineraryDifficultyByName(String difficultyName) {
        return difficultyRepository.findByName(difficultyName);
    }

    @Override
    public ItineraryState findItineraryStateByName(String stateName) throws BusinessException {
        return itineraryStateRepository.findByName(stateName);
    }

    @Override
    public ItineraryCategory findItineraryCategoryByName(String categoryName) throws BusinessException {
        return itineraryCategoryRepository.findByName(categoryName);
    }

    private ItineraryPoint getItineraryPointByName(FeatureCollection featureCollection, String name) throws BusinessException {
        ItineraryPoint result = new ItineraryPoint();
        for (Feature feature : featureCollection) {
            String value = feature.getProperty("name");
            if (name.equalsIgnoreCase(value)) {
                result.setName(feature.getProperty("description"));
                LngLatAlt coordinates = ((Point) feature.getGeometry()).getCoordinates();
                result.setLatitude(coordinates.getLatitude());
                result.setLongitude(coordinates.getLongitude());
            }
        }
        return result;
    }
}
