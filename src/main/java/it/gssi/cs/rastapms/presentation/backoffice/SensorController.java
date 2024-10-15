package it.gssi.cs.rastapms.presentation.backoffice;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.domain.Image;
import it.gssi.cs.rastapms.domain.POI;
import it.gssi.cs.rastapms.domain.Sensor;
import it.gssi.cs.rastapms.domain.SensorType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("backoffice/sensor")
public class SensorController {
    private POIService poiService;

    public SensorController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/list")
    public String list(@RequestParam("poiID") Long poiID, Model model) {
        model.addAttribute("poiID", poiID);
        return "/backoffice/sensor/list";
    }

    //@JsonView(Views.POIPrivate.class)
    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<Sensor> findAllPaginated(@RequestParam("poiID") Long poiID, @RequestBody RequestGrid requestGrid)
            throws BusinessException {
        return poiService.findAllSensorsByPOIPaginated(poiID, requestGrid);
    }

    @GetMapping("/create")
    public String createStart(@RequestParam("poiID") Long poiID, Model model) {
        POI poi = new POI();
        poi.setId(poiID);
        Sensor sensor = new Sensor();
        sensor.setPoi(poi);
        model.addAttribute("sensor", sensor);
        return "/backoffice/sensor/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("sensor") Sensor sensor) throws BusinessException {
        poiService.createSensor(sensor);
        return "redirect:/backoffice/sensor/list?poiID=" + sensor.getPoi().getId();
    }

    @GetMapping("/update")
    public String updateStart(@RequestParam Long id, Model model) throws BusinessException {
        Sensor sensor = poiService.findSensorByID(id);
        model.addAttribute("sensor", sensor);
        return "/backoffice/sensor/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("sensor") Sensor sensor) throws BusinessException {
        poiService.updateSensor(sensor);
        return "redirect:/backoffice/sensor/list?poiID=" + sensor.getPoi().getId();
    }

    @GetMapping("/delete")
    public String deleteStart(@RequestParam Long id, Model model) throws BusinessException {
        Sensor sensor = poiService.findSensorByID(id);
        model.addAttribute("sensor", sensor);
        return "/backoffice/sensor/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("sensor") Sensor sensor) throws BusinessException {
        poiService.deleteSensor(sensor);
        return "redirect:/backoffice/sensor/list?poiID=" + sensor.getPoi().getId();
    }

    @ModelAttribute
    public void populateFormfields(Model model) throws BusinessException {
        List<SensorType> types = poiService.findAllSensorsType();
        model.addAttribute("types", types);

    }
}
