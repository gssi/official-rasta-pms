package it.gssi.cs.rastapms.business.impl;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.business.repository.*;
import it.gssi.cs.rastapms.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JPAPOIServiceImpl implements POIService {

    private POIRepository poiRepository;
    private POICategoryRepository poiCategoryRepository;
    private MunicipalityRepository municipalityRepository;
    private ImageRepository imageRepository;
    private SensorRepository sensorRepository;
    private SensorTypeRepository sensorTypeRepository;
    private ParameterRepository parameterRepository;
    private InfluxDBService influxDBService;

    public JPAPOIServiceImpl(POIRepository poiRepository, POICategoryRepository poiCategoryRepository, MunicipalityRepository municipalityRepository, ImageRepository imageRepository, SensorRepository sensorRepository, SensorTypeRepository sensorTypeRepository, ParameterRepository parameterRepository, InfluxDBService influxDBService) {
        this.poiRepository = poiRepository;
        this.poiCategoryRepository = poiCategoryRepository;
        this.municipalityRepository = municipalityRepository;
        this.imageRepository = imageRepository;
        this.sensorRepository = sensorRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.parameterRepository = parameterRepository;
        this.influxDBService = influxDBService;
    }

    @Override
    public ResponseGrid<POI> findAllPointOfInterestsPaginated(RequestGrid requestGrid) throws BusinessException {
        Pageable pageable = PageRequest.of(requestGrid.getStartPage().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<POI> page = poiRepository.findByNameLike(Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<POI> response = new ResponseGrid<>(requestGrid.getDraw(), poiRepository.count(), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public List<POI> findAllPOIs() throws BusinessException {
        return poiRepository.findAll();
    }

    @Override
    public POI createPointOfInterest(POI poi) throws BusinessException {
        return poiRepository.save(poi);
    }

    @Override
    public POI findPointOfInterestByID(Long id) throws BusinessException {
        return poiRepository.findById(id).get();
    }


    @Override
    public POI findPointOfInterestByIDWithAugmentedDescription(Long id) throws BusinessException {
        POI poi = poiRepository.findById(id).get();
        String description = poi.getDescription();
        List<Parameter> parameters = extractParameters(description, id);
        for (Parameter parameter : parameters) {
            ParameterValue parameterValue = influxDBService.getParameterValue(parameter);
            String descriptionParameterValue = createDescriptionParameterValue(parameterValue);
            String descriptionParameter = "\\[\\#" + parameter.getSensor().getName() + "\\!" + parameter.getName() + "\\]";
            description = description.replaceAll(descriptionParameter, descriptionParameterValue);

        }
        poi.setDescription(description);
        return poi;
    }

    @Override
    public POICategory createPOICategory(POICategory poiCategory) throws BusinessException {
        return poiCategoryRepository.save(poiCategory);
    }

    @Override
    public List<Parameter> findAllParameterByPoiID(Long PoiID) {
        return parameterRepository.findAll();
    }

    @Override
    public Parameter findParameterByPoiIDAndName(Long poiID, String parameterName) throws BusinessException {
        return parameterRepository.findParameterByPoiIDAndName(poiID, parameterName);
    }

    private String createDescriptionParameterValue(ParameterValue parameterValue) {
        if (parameterValue != null) {
            return parameterValue.getValue();
        } else {
            return "Non Disponibile";
        }

    }

    private List<Parameter> extractParameters(String description, Long poiID) {
        List<Parameter> result = new ArrayList<>();
        boolean openParameterDefinition = false;
        StringBuilder parameterDefinition = new StringBuilder();
        for (char c : description.toCharArray()) {
            if ("[".equalsIgnoreCase(String.valueOf(c))) {
                openParameterDefinition = true;
                continue;
            }
            if ("]".equalsIgnoreCase(String.valueOf(c))) {
                openParameterDefinition = false;
                String sensorName = parameterDefinition.substring(1, parameterDefinition.indexOf("!"));
                String parameterName = parameterDefinition.substring(parameterDefinition.indexOf("!") + 1);
                Sensor sensor = sensorRepository.findByPoiIDAndName(poiID, sensorName);
                Parameter parameter = parameterRepository.findBySensorIDAndName(sensor.getId(), parameterName);
                if (sensorName.equals(parameter.getSensor().getName())) {
                    result.add(parameter);
                }
                parameterDefinition = new StringBuilder();
                continue;
            }
            if (openParameterDefinition) {
                parameterDefinition.append(c);
            }
        }
        return result;
    }

    @Override
    public POI updatePointOfInterest(POI poi) throws BusinessException {
        return poiRepository.save(poi);
    }

    @Override
    public void deletePointOfInterest(POI poi) throws BusinessException {
        poiRepository.delete(poi);
    }

    @Override
    public ResponseGrid<Image> findAllImagesByPOIPaginated(Long poiID, RequestGrid requestGrid) throws BusinessException {
        Pageable pageable = PageRequest.of(requestGrid.getStart().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<Image> page = imageRepository.findByPoiIDAndName(poiID, Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<Image> response = new ResponseGrid<>(requestGrid.getDraw(), imageRepository.countByPOIID(poiID), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public void createImage(Image image) throws BusinessException {
        imageRepository.save(image);
    }

    @Override
    public List<POICategory> findAllPOICategories() throws BusinessException {
        return poiCategoryRepository.findAll();
    }

    @Override
    public List<Municipality> findAllMunicipalities() throws BusinessException {
        return municipalityRepository.findAll();
    }

    @Override
    public Image findImageByID(Long id) throws BusinessException {
        return imageRepository.findById(id).get();
    }

    @Override
    public void updateImage(Image image) throws BusinessException {
        imageRepository.save(image);
    }

    @Override
    public void deleteImage(Image image) throws BusinessException {
        imageRepository.delete(image);
    }

    @Override
    public Municipality findMunicipalityByZipCode(String zipcode) throws BusinessException {
        return municipalityRepository.findMunicipalityByZipCode(zipcode);
    }

    @Override
    public POICategory findPOICategoryByName(String name) throws BusinessException {
        return poiCategoryRepository.findByName(name);
    }

    @Override
    public ResponseGrid<Sensor> findAllSensorsByPOIPaginated(Long poiID, RequestGrid requestGrid) throws BusinessException {
        Pageable pageable = PageRequest.of(requestGrid.getStart().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<Sensor> page = sensorRepository.findByPoiIDAndName(poiID, Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<Sensor> response = new ResponseGrid<>(requestGrid.getDraw(), sensorRepository.countByPOIID(poiID), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public void createSensor(Sensor sensor) throws BusinessException {
        sensorRepository.save(sensor);
    }

    @Override
    public List<SensorType> findAllSensorsType() throws BusinessException {
        return sensorTypeRepository.findAll();
    }

    @Override
    public Sensor findSensorByID(Long id) throws BusinessException {
        return sensorRepository.findById(id).get();
    }

    @Override
    public void updateSensor(Sensor sensor) throws BusinessException {
        sensorRepository.save(sensor);
    }

    @Override
    public void deleteSensor(Sensor sensor) throws BusinessException {
        List<Parameter> parameters = parameterRepository.findParametersBySensorId(sensor.getId());
        parameterRepository.deleteAll(parameters);
        sensorRepository.delete(sensor);
    }

    @Override
    public ResponseGrid<Parameter> findAllParametersBySensorIDPaginated(Long sensorID, RequestGrid requestGrid) throws BusinessException {
        Pageable pageable = PageRequest.of(requestGrid.getStart().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<Parameter> page = parameterRepository.findBySensorIDAndName(sensorID, Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<Parameter> response = new ResponseGrid<>(requestGrid.getDraw(), parameterRepository.countBySensorID(sensorID), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public void createParameter(Parameter parameter) throws BusinessException {
        parameterRepository.save(parameter);
    }

    @Override
    public Parameter findParameterByID(Long id) throws BusinessException {
        return parameterRepository.findById(id).get();
    }

    @Override
    public void updateParameter(Parameter parameter) throws BusinessException {
        parameterRepository.save(parameter);
    }

    @Override
    public void deleteParameter(Parameter parameter) throws BusinessException {
        parameterRepository.delete(parameter);
    }
}
