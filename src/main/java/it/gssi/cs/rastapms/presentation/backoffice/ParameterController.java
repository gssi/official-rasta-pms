package it.gssi.cs.rastapms.presentation.backoffice;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.domain.Parameter;
import it.gssi.cs.rastapms.domain.ParameterType;
import it.gssi.cs.rastapms.domain.Sensor;
import it.gssi.cs.rastapms.domain.SensorType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("backoffice/sensorparameter")
public class ParameterController {

    private POIService poiService;

    public ParameterController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/list")
    public String list(@RequestParam("sensorID") Long sensorID, Model model) throws BusinessException {
        Sensor sensor = poiService.findSensorByID(sensorID);
        model.addAttribute("sensor", sensor);
        return "/backoffice/sensorparameter/list";
    }

    //@JsonView(Views.POIPrivate.class)
    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<Parameter> findAllPaginated(@RequestParam("sensorID") Long sensorID, @RequestBody RequestGrid requestGrid)
            throws BusinessException {
        return poiService.findAllParametersBySensorIDPaginated(sensorID, requestGrid);
    }

    @GetMapping("/create")
    public String createStart(@RequestParam("sensorID") Long sensorID, Model model) throws BusinessException {
        Sensor sensor = poiService.findSensorByID(sensorID);
        Parameter parameter = new Parameter();
        parameter.setSensor(sensor);
        model.addAttribute("parameter", parameter);
        return "/backoffice/sensorparameter/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("parameter") Parameter parameter) throws BusinessException {
        poiService.createParameter(parameter);
        return "redirect:/backoffice/sensorparameter/list?sensorID=" + parameter.getSensor().getId();
    }

    @GetMapping("/update")
    public String updateStart(@RequestParam Long id, Model model) throws BusinessException {
        Parameter parameter = poiService.findParameterByID(id);
        model.addAttribute("parameter", parameter);
        return "/backoffice/sensorparameter/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("parameter") Parameter parameter) throws BusinessException {
        poiService.updateParameter(parameter);
        return "redirect:/backoffice/sensorparameter/list?sensorID=" + parameter.getSensor().getId();
    }

    @GetMapping("/delete")
    public String deleteStart(@RequestParam Long id, Model model) throws BusinessException {
        Parameter parameter = poiService.findParameterByID(id);
        model.addAttribute("parameter", parameter);
        return "/backoffice/sensorparameter/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("parameter") Parameter parameter) throws BusinessException {
        poiService.deleteParameter(parameter);
        return "redirect:/backoffice/sensorparameter/list?sensorID=" + parameter.getSensor().getId();
    }

    @ModelAttribute
    public void populateFormfields(Model model) throws BusinessException {

        model.addAttribute("types", ParameterType.values());

    }
}
