package it.gssi.cs.rastapms.presentation.backoffice;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.POIService;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.domain.Image;
import it.gssi.cs.rastapms.domain.POI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("backoffice/image")
public class ImagePOIController {

    private POIService service;

    public ImagePOIController(POIService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String list(@RequestParam("poiID") Long poiID, Model model) {
        model.addAttribute("poiID", poiID);
        return "/backoffice/image/list";
    }

    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<Image> findAllPaginated(@RequestParam("poiID") Long poiID, @RequestBody RequestGrid requestGrid)
            throws BusinessException {
        return service.findAllImagesByPOIPaginated(poiID, requestGrid);
    }

    @GetMapping("/create")
    public String createStart(@RequestParam("poiID") Long poiID, Model model) {
        POI poi = new POI();
        poi.setId(poiID);
        Image image = new Image();
        image.setPoi(poi);
        model.addAttribute("image", image);
        model.addAttribute("imageadded", false);
        return "/backoffice/image/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("image") Image image, @RequestParam("imagefile") MultipartFile imageFile) throws BusinessException, IOException {
        image.setContent(imageFile.getBytes());
        image.setType(imageFile.getContentType());
        service.createImage(image);
        return "redirect:/backoffice/image/list?poiID=" + image.getPoi().getId();
    }

    @GetMapping("/update")
    public String updateStart(@RequestParam Long id, Model model) throws BusinessException {
        Image image = service.findImageByID(id);
        model.addAttribute("image", image);
        model.addAttribute("imageadded", image.getContent().length != 0);
        return "/backoffice/image/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("image") Image image, @RequestParam("imagefile") MultipartFile imageFile) throws BusinessException, IOException {
        if (imageFile.getBytes().length == 0) {
            Image oldImage = service.findImageByID(image.getId());
            image.setContent(oldImage.getContent());
            image.setType(oldImage.getType());
        } else {
            image.setContent(imageFile.getBytes());
            image.setType(imageFile.getContentType());
        }
        service.updateImage(image);
        return "redirect:/backoffice/image/list?poiID=" + image.getPoi().getId();
    }

    @GetMapping("/delete")
    public String deleteStart(@RequestParam Long id, Model model) throws BusinessException {
        Image image = service.findImageByID(id);
        model.addAttribute("image", image);
        model.addAttribute("imageadded", image.getContent().length != 0);
        return "/backoffice/image/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("image") Image image) throws BusinessException {
        service.deleteImage(image);
        return "redirect:/backoffice/image/list?poiID=" + image.getPoi().getId();
    }

    @GetMapping("/getimage")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam("id") Long id) throws BusinessException {
        Image image = service.findImageByID(id);
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
