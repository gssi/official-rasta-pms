package it.gssi.cs.rastapms.presentation.frontoffice;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.domain.Image;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("images")
public class PublicImagePOIController {

    private POIService poiService;

    public PublicImagePOIController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/getimage")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam("id") Long id) throws BusinessException {
        Image image = poiService.findImageByID(id);
        byte[] data = image.getContent();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                //.header("Content-type","application/octet-stream")
                //.header("content-disposition","attachment; filename=\"" + fileName + "\"")
                .header("Cache-Control", "no-cache")
                .body(resource);

    }
}
