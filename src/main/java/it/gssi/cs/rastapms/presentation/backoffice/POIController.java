package it.gssi.cs.rastapms.presentation.backoffice;

import com.fasterxml.jackson.annotation.JsonView;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.domain.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("backoffice/poi")
public class POIController {

    private POIService poiService;

    public POIController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/list")
    public String list() {
        return "/backoffice/poi/list";
    }

    @JsonView(Views.POIPrivate.class)
    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<POI> findAllPaginated(@RequestBody RequestGrid requestGrid)
            throws BusinessException {
        return poiService.findAllPointOfInterestsPaginated(requestGrid);
    }

    @GetMapping("/create")
    public String createStart(Model model) {
        POI poi = new POI();
        Image primaryImage = new Image();
        poi.setPrimaryImage(primaryImage);
        model.addAttribute("poi", poi);
        model.addAttribute("primaryimageadded", false);
        return "/backoffice/poi/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("poi") POI poi, @RequestParam("categoriesadded") String categoriesadded, @RequestParam("primaryimagefile") MultipartFile primaryImageFile) throws BusinessException, IOException {
        createPOICategories(poi, categoriesadded);
        poi.getPrimaryImage().setContent(primaryImageFile.getBytes());
        poi.getPrimaryImage().setType(primaryImageFile.getContentType());
        poiService.createPointOfInterest(poi);
        return "redirect:/backoffice/poi/list";
    }

    @GetMapping("/update")
    public String updateStart(@RequestParam Long id, Model model) throws BusinessException {
        POI poi = poiService.findPointOfInterestByID(id);
        model.addAttribute("poi", poi);
        model.addAttribute("primaryimageadded", poi.getPrimaryImage().getContent().length != 0);
        return "/backoffice/poi/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("poi") POI poi, @RequestParam("categoriesadded") String categoriesadded, @RequestParam("primaryimagefile") MultipartFile primaryimagefile) throws BusinessException, IOException {
        createPOICategories(poi, categoriesadded);
        if (primaryimagefile.getBytes().length == 0) {
            POI oldPoi = poiService.findPointOfInterestByID(poi.getId());
            poi.getPrimaryImage().setContent(oldPoi.getPrimaryImage().getContent());
            poi.getPrimaryImage().setType(oldPoi.getPrimaryImage().getType());
        } else {
            poi.getPrimaryImage().setContent(primaryimagefile.getBytes());
            poi.getPrimaryImage().setType(primaryimagefile.getContentType());
        }

        poiService.updatePointOfInterest(poi);
        return "redirect:/backoffice/poi/list";
    }

    @GetMapping("/delete")
    public String deleteStart(@RequestParam Long id, Model model) throws BusinessException {
        POI poi = poiService.findPointOfInterestByID(id);
        model.addAttribute("poi", poi);
        model.addAttribute("primaryimageadded", poi.getPrimaryImage().getContent().length != 0);
        return "/backoffice/poi/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("poi") POI poi) throws BusinessException {
        poiService.deletePointOfInterest(poi);
        return "redirect:/backoffice/poi/list";
    }

    @GetMapping("/getimage")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam("id") Long id) throws BusinessException {
        POI poi = poiService.findPointOfInterestByID(id);
        byte[] data = poi.getPrimaryImage().getContent();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                //.header("Content-type","application/octet-stream")
                //.header("content-disposition","attachment; filename=\"" + fileName + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);

    }

    @ModelAttribute
    public void populateFormfields(Model model) throws BusinessException {
        List<POICategory> categories = poiService.findAllPOICategories();
        model.addAttribute("categories", categories);

        List<Municipality> municipalities = poiService.findAllMunicipalities();
        model.addAttribute("municipalities", municipalities);


    }

    private void createPOICategories(POI poi, String categoriesadded) throws BusinessException {
        if (!"".equals(categoriesadded)) {
            for (String category: categoriesadded.split(",")) {
                POICategory poiCategory = new POICategory();
                poiCategory.setName(category);
                POICategory newPoiCategory = poiService.createPOICategory(poiCategory);
                poi.getCategories().add(newPoiCategory);
            }
        }
    }
}
