package attend.geo.attend.controller;

import attend.geo.attend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ComponentScan("controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@CrossOrigin
public class ImagesController {

    @Autowired
    ImageService imageService;

    @PostMapping("/uploadImage")
    public HttpEntity<?> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) {
        return imageService.uploadFile(files, file);
    }

    @PutMapping("/updateImage/{id}")
    public HttpEntity<?> update(
            @PathVariable(required = false) Long id,
            @PathVariable(required = false) List<Long> ids,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) {
        return imageService.updateFile(id,ids,files,file);
    }
}
