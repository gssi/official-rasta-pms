package it.gssi.cs.rastapms.business.impl;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.business.repository.ImageRepository;
import it.gssi.cs.rastapms.business.repository.MunicipalityRepository;
import it.gssi.cs.rastapms.business.repository.POICategoryRepository;
import it.gssi.cs.rastapms.business.repository.POIRepository;
import it.gssi.cs.rastapms.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAPOIServiceImpl implements POIService {

    private POIRepository poiRepository;
    private POICategoryRepository poiCategoryRepository;
    private MunicipalityRepository municipalityRepository;
    private ImageRepository imageRepository;

    public JPAPOIServiceImpl(POIRepository poiRepository, POICategoryRepository poiCategoryRepository, MunicipalityRepository municipalityRepository, ImageRepository imageRepository) {
        this.poiRepository = poiRepository;
        this.poiCategoryRepository = poiCategoryRepository;
        this.municipalityRepository = municipalityRepository;
        this.imageRepository = imageRepository;
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
}
