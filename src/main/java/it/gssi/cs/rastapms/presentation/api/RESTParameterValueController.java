package it.gssi.cs.rastapms.presentation.api;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.impl.InfluxDBService;
import it.gssi.cs.rastapms.domain.Parameter;
import it.gssi.cs.rastapms.domain.Views;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Point of interest parameters")
@RestController
@RequestMapping(value = "/api/v1/pois")
@CrossOrigin
public class RESTParameterValueController {
    private POIService poiService;
    private InfluxDBService influxDBService;

    public RESTParameterValueController(POIService poiService, InfluxDBService influxDBService) {
        this.poiService = poiService;
        this.influxDBService = influxDBService;
    }

    @JsonView(Views.POIPublic.class)
    @GetMapping("/{poiID}/parameters")
    public List<Parameter> findAll(@PathVariable("poiID")Long poiID) {
        try {
            return poiService.findAllParameterByPoiID(poiID);
        } catch (BusinessException e) {
            throw new ParameterException("Error in the parameters list", e);
        }
    }
    @GetMapping("/{poiID}/parameters/{parameterName}/value")
    public String getParameterValue(@PathVariable("poiID")Long poiID, @PathVariable("parameterName") String parameterName) {
        try {
            Parameter parameter = poiService.findParameterByPoiIDAndName(poiID, parameterName);
            return influxDBService.getParameterValue(parameter).getValue();
        } catch (BusinessException e) {
            throw new ParameterException("Error in the parameter value", e);
        }

    }
}
