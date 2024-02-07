package attend.geo.attend.service;

import attend.geo.attend.entity.Images;
import attend.geo.attend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final String upload = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadImage\\";

    public HttpEntity<?> uploadFile(List<MultipartFile> files, MultipartFile fileOne) {

        if (files == null && fileOne != null ) {
            if (fileOne.isEmpty() || fileOne.getOriginalFilename() == null) {
                return ResponseEntity.status(404).body("File is empty");
            }
            Images images = new Images();
            try {
                //**
                String originalFileName = fileOne.getOriginalFilename();
                String[] split = originalFileName.split("\\.");
                String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                images.setActualName(actualName);

                fileOne.transferTo(new File(upload + actualName));

                images.setFileName(originalFileName);
                images.setContentType(fileOne.getContentType());
                images.setFileSize(fileOne.getSize());
                images.setExtension(split[split.length - 1]);
                Images image = imageRepository.save(images);
                UUID token = image.getToken();
                return ResponseEntity.ok(token);
            } catch (Exception e) {
                imageRepository.delete(images);
                return ResponseEntity.ok(e.getMessage());
            }
        } else {
            List<String> imageTokens = new ArrayList<>();
            for (MultipartFile file : files) {
                if ( file.isEmpty() || file.getOriginalFilename() == null) {
                    return ResponseEntity.ok("File is empty");
                }
                Images images = new Images();
                try {
                    String originalFileName = file.getOriginalFilename();
                    String[] split = originalFileName.split("\\.");
                    String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                    images.setActualName(actualName);
                    file.transferTo(new File(upload + actualName));
                    images.setExtension(split[split.length - 1]);
                    images.setFileName(originalFileName);
                    images.setFileSize(file.getSize());
                    images.setContentType(file.getContentType());
                    images = imageRepository.save(images);
                    imageTokens.add(images.getToken().toString());
                } catch (Exception e) {
                    return ResponseEntity.ok(e.getMessage());
                }
            }
            return ResponseEntity.ok(imageTokens);
        }
    }

    public HttpEntity<?> updateFile(Long id,List<Long>ids, List<MultipartFile> files, MultipartFile fileOne) {

        if (id != null && !fileOne.isEmpty()) {
            try {
                Images images = new Images();
                String originalFileName = fileOne.getOriginalFilename();
                String[] split = originalFileName.split("\\.");
                String actualName = UUID.randomUUID()+"."+split[split.length-1];
                fileOne.transferTo(new File(upload+actualName));
                Optional<Images> byId = imageRepository.findById(id);

                images.setId(id);
                images.setFileName(fileOne.getName());
                images.setFileSize(fileOne.getSize());
                images.setExtension(split[split.length - 1]);
                images.setContentType(fileOne.getContentType());
                images.setActualName(actualName);
                images.setToken(byId.get().getToken());
                Images updateImage = imageRepository.save(images);
                return ResponseEntity.ok(updateImage.getToken());
            }catch (Exception e){
                return ResponseEntity.ok(e.getMessage());
            }
        }
        return null;
    }
}
