package it.gssi.cs.rastapms.presentation.backoffice;


import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.ItineraryService;
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

@Hidden
@Controller
@RequestMapping("backoffice/itinerary")
public class ItineraryController {

    private ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping("/list")
    public String list() {
        return "backoffice/itinerary/list";
    }

    @JsonView(Views.ItineraryPrivate.class)
    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<Itinerary> findAllPaginated(@RequestBody RequestGrid requestGrid) throws BusinessException {
        return itineraryService.findAllItinerariesPaginated(requestGrid);
    }

    @GetMapping("/create")
    public String createStart(Model model) {
        Itinerary itinerary = new Itinerary();
        model.addAttribute("itinerary", itinerary);

        model.addAttribute("maprequired", true);
        return "backoffice/itinerary/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("itinerary") Itinerary itinerary, @RequestParam("mapfile") MultipartFile mapfile, @RequestParam("imagefile") MultipartFile imagefile) throws BusinessException, IOException {
        itinerary.setMap(new String(mapfile.getBytes()));
        itinerary.setImage(imagefile.getBytes());
        itineraryService.createItinerary(itinerary);
        return "redirect:/backoffice/itinerary/list";
    }

    @GetMapping("/update")
    public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        model.addAttribute("itinerary", itinerary);
        model.addAttribute("maprequired", ((itinerary.getMap() == null) ? true : itinerary.getMap().length() == 0));
        model.addAttribute("mapadded", ((itinerary.getMap() == null) ? false : itinerary.getMap().length() != 0));
        model.addAttribute("imageadded", ((itinerary.getImage() == null) ? false : itinerary.getImage().length != 0));
        return "backoffice/itinerary/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("itinerary") Itinerary itinerary, @RequestParam("mapfile") MultipartFile mapfile, @RequestParam("imagefile") MultipartFile imagefile) throws BusinessException, IOException {
        if (mapfile.getBytes().length == 0 || imagefile.getBytes().length == 0) {
            Itinerary oldItinerary = itineraryService.findItineraryByID(itinerary.getId());
            if (mapfile.getBytes().length == 0) {
                itinerary.setMap(oldItinerary.getMap());
            } else {
                itinerary.setMap(new String(mapfile.getBytes()));
            }
            if (imagefile.getBytes().length == 0) {
                itinerary.setImage(oldItinerary.getImage());
            } else {
                itinerary.setImage(imagefile.getBytes());
            }
        } else {
            itinerary.setMap(new String(mapfile.getBytes()));
            itinerary.setImage(imagefile.getBytes());
        }

        itineraryService.updateItinerary(itinerary);
        return "redirect:/backoffice/itinerary/list";
    }

    @GetMapping("/delete")
    public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        model.addAttribute("itinerary", itinerary);
        return "backoffice/itinerary/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("itinerary") Itinerary itinerary) throws BusinessException, IOException {
        itineraryService.deleteItinerary(itinerary);
        return "redirect:/backoffice/itinerary/list";
    }

    @GetMapping("/getimage")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam("id") Long id) throws BusinessException {
        Itinerary itinerary = itineraryService.findItineraryByID(id);
        byte[] data = itinerary.getImage();
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
    public void populateFormFields(Model model) throws BusinessException {
        List<Difficulty> difficulties = itineraryService.findAllDifficulties();
        model.addAttribute("difficulties", difficulties);

        List<ItineraryState> states = itineraryService.findAllItineraryStates();
        model.addAttribute("states", states);

        List<ItineraryCategory> categories = itineraryService.findAllItineraryCategories();
        model.addAttribute("categories", categories);

    }


}
