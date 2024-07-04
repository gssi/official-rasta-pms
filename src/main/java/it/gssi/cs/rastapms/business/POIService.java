package it.gssi.cs.rastapms.business;


import it.gssi.cs.rastapms.domain.Image;
import it.gssi.cs.rastapms.domain.Municipality;
import it.gssi.cs.rastapms.domain.POI;
import it.gssi.cs.rastapms.domain.POICategory;

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
}
