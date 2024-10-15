package it.gssi.cs.rastapms.presentation.frontoffice;


import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.domain.POI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("pois")
public class PublicPOIController {

    private POIService poiService;

    public PublicPOIController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/list")
    public String list(Model model) throws BusinessException {
        List<POI> pois = poiService.findAllPOIs();
        model.addAttribute("pois", pois);
        return "/frontoffice/poi/list";
    }

    @GetMapping("/detail")
    public String poiDetail(Model model, @RequestParam("id") Long id) throws BusinessException {
        POI poi = poiService.findPointOfInterestByIDWithAugmentedDescription(id);
        model.addAttribute("poi", poi);
        return "/frontoffice/poi/detail";
    }

}
