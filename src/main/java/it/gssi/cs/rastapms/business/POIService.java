package it.gssi.cs.rastapms.business;


import it.gssi.cs.rastapms.domain.*;

import java.util.List;

public interface POIService {

    ResponseGrid<POI> findAllPointOfInterestsPaginated(RequestGrid requestGrid) throws BusinessException;

    List<POI> findAllPOIs() throws BusinessException;

    POI createPointOfInterest(POI poi) throws BusinessException;

    POI findPointOfInterestByID(Long id) throws BusinessException;

    POI updatePointOfInterest(POI poi) throws BusinessException;

    void deletePointOfInterest(POI poi) throws BusinessException;

    ResponseGrid<Image> findAllImagesByPOIPaginated(Long poiID, RequestGrid requestGrid) throws BusinessException;

    List<POICategory> findAllPOICategories() throws BusinessException;

    List<Municipality> findAllMunicipalities() throws BusinessException;

    void createImage(Image image) throws BusinessException;

    Image findImageByID(Long id) throws BusinessException;

    void updateImage(Image image) throws BusinessException;

    void deleteImage(Image image) throws BusinessException;

    Municipality findMunicipalityByZipCode(String zipcode) throws BusinessException;

    POICategory findPOICategoryByName(String name) throws BusinessException;

    ResponseGrid<Sensor> findAllSensorsByPOIPaginated(Long poiID, RequestGrid requestGrid) throws BusinessException;

    void createSensor(Sensor sensor) throws BusinessException;

    List<SensorType> findAllSensorsType() throws BusinessException;

    Sensor findSensorByID(Long id) throws BusinessException;

    void updateSensor(Sensor sensor) throws BusinessException;

    void deleteSensor(Sensor sensor) throws BusinessException;

    ResponseGrid<Parameter> findAllParametersBySensorIDPaginated(Long sensorID, RequestGrid requestGrid) throws BusinessException;

    void createParameter(Parameter parameter) throws BusinessException;

    Parameter findParameterByID(Long id) throws BusinessException;

    void updateParameter(Parameter parameter) throws BusinessException;

    void deleteParameter(Parameter parameter) throws BusinessException;

    POI findPointOfInterestByIDWithAugmentedDescription(Long id) throws BusinessException;

    POICategory createPOICategory(POICategory poiCategory) throws BusinessException;

    List<Parameter> findAllParameterByPoiID(Long poiID) throws BusinessException;

    Parameter findParameterByPoiIDAndName(Long poiID, String parameterName) throws BusinessException;
}
