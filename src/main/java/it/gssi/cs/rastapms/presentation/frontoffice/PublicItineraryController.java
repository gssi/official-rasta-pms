package it.gssi.cs.rastapms.presentation.frontoffice;

import io.swagger.v3.oas.annotations.Hidden;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.ItineraryService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.domain.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Controller
@RequestMapping("itineraries")
public class PublicItineraryController {

    private ItineraryService itineraryService;

    public PublicItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping("/list")
    public String list(Model model) throws BusinessException {
        List<Itinerary> itineraries = itineraryService.findAllItineraries();
        model.addAttribute("itineraries", itineraries);
        return "frontoffice/itinerary/list";
    }

    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<Itinerary> findAllPaginated(@RequestBody RequestGrid requestGrid) throws BusinessException {
        return itineraryService.findAllItinerariesPaginated(requestGrid);
    }

    @PostMapping("/getmap")
    public ResponseEntity<ByteArrayResource> getMap(@RequestParam("id") Long id) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        String data = itinerary.getMap();
        ByteArrayResource resource = new ByteArrayResource(data.getBytes());
        return ResponseEntity
                .ok()
                .contentLength(data.length())
                .header("Content-type","application/geo+json")
                //.header("content-disposition","attachment; filename=\"" + fileName + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);


    }

    @GetMapping("/detail")
    public String poiDetail(Model model, @RequestParam("id") Long id) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        model.addAttribute("itinerary", itinerary);
        return "/frontoffice/itinerary/detail";
    }

    @GetMapping("/getimage")
    public ResponseEntity<ByteArrayResource> getImage(Model model, @RequestParam("id") Long id) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        ByteArrayResource resource = new ByteArrayResource(itinerary.getImage());
        return ResponseEntity
                .ok()
                .contentLength(itinerary.getImage().length)
                //.header("Content-type","application/octet-stream")
                //.header("content-disposition","attachment; filename=\"" + fileName + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);

    }


}
