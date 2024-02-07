package attend.geo.attend.service;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.UserDto;
import attend.geo.attend.dto.UserRequest;
import attend.geo.attend.entity.Images;
import attend.geo.attend.entity.User;
import attend.geo.attend.payload.Payload;
import attend.geo.attend.repository.ImageRepository;
import attend.geo.attend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final String upload = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadImage\\";


    public HttpEntity<?> addUser(UserDto userDto) {
        try {
            if (userDto != null) {
                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }

                User user = new User();
                user.setUserName(userDto.getUserName());
                user.setIsBlocked(userDto.getIsBlocked());
                user.setPosition(userDto.getPosition());
                user.setRandomCode(randomSixDigitNumber);
                if (userDto.getTokens() != null && !userDto.getTokens().isEmpty()) {
                    List<Images> images = new ArrayList<>();
                    for (String token : userDto.getTokens()) {
                        Optional<Images> imagesOptional = imageRepository.findByToken(UUID.fromString(token));
                        imagesOptional.ifPresent(images::add);
                    }
                    user.setImages(images);
                }
                user = userRepository.save(user);
                return Payload.ok(user).response();
            } else {
                return Payload.notFound().response();
            }
        } catch (Exception e) {
            return Payload.notFound("Server bilan muammo").response();
        }
    }


    public HttpEntity<?> addUserAndFile(MultipartFile file, List<MultipartFile> files, UserRequest request) {
        try {
            User user = new User();
            if (file == null && files != null && request != null) {
                List<Images> images = new ArrayList<>();
                for (MultipartFile multipartFile : files) {
                    Images images1 = new Images();
                    images1.setFileName(multipartFile.getName());
                    images1.setContentType(multipartFile.getContentType());
                    images1.setFileSize(multipartFile.getSize());
                    String originalName = multipartFile.getOriginalFilename();
                    String[] split = originalName.split("\\.");
                    String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                    images1.setActualName(actualName);
                    imageRepository.save(images1);
                    images.add(images1);
                }

                user.setUserName(request.getUserName());
                user.setImages(images);

                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }
                user.setRandomCode(randomSixDigitNumber);
                user.setPosition(request.getPosition());
                user.setIsBlocked(request.getIsBlocked());
                user.setConnection(false);
                User saveUser = userRepository.save(user);
                return Payload.ok(saveUser).response();
            } else if (!file.isEmpty() && files == null && request != null) {
                List<Images> images = new ArrayList<>();
                Images image = new Images();
                image.setFileName(file.getName());
                image.setFileSize(file.getSize());
                image.setContentType(file.getContentType());
                String originalName = file.getOriginalFilename();
                String[] split = originalName.split("\\.");
                String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                image.setActualName(actualName);
                images.add(image);
                imageRepository.save(image);
                file.transferTo(new File(upload + actualName));

                user.setPosition(request.getPosition());
                user.setIsBlocked(request.getIsBlocked());
                user.setUserName(request.getUserName());
                user.setImages(images);

                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }
                user.setRandomCode(randomSixDigitNumber);
                user.setIsBlocked(true);
                user.setConnection(false);
                User saveUser = userRepository.save(user);
                return Payload.ok(saveUser).response();
            } else {
                return ResponseEntity.badRequest().body("Request error");
            }
        } catch (IOException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    public HttpEntity<?> getAllUserRequest(GetAllUserRequest request) {
        try {
            if (request != null) {
                Page<User> all = userRepository.findAll(PageRequest.of(request.getPageNumber(), request.getPageSize()));
                List<User> userList = all.stream().map(r ->
                        new User(r.getId(),
                                r.getUserName(),
                                r.getRandomCode(),
                                r.getPosition(),
                                r.getConnection(),
                                r.getDevice(),
                                r.getIsBlocked(),
                                r.getImages()
                        )).collect(Collectors.toList());
                return Payload.ok(userList).response();
            } else {
                return ResponseEntity.ok("Request error");
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

    public HttpEntity<?> updateUser(UserDto userDto, Long id) {
        try {
            if (id != null & userDto != null) {
                Optional<User> byId = userRepository.findById(id);
                User user = byId.get();
                user.setId(id);
                user.setUserName(userDto.getUserName());
                user.setPosition(userDto.getPosition());
                user.setIsBlocked(userDto.getIsBlocked());
                if (userDto.getTokens() != null && !userDto.getTokens().isEmpty()) {
                    List<Images> images = new ArrayList<>();
                    for (String token : userDto.getTokens()) {
                        Optional<Images> imagesOptional = imageRepository.findByToken(UUID.fromString(token));
                        imagesOptional.ifPresent(images::add);
                    }
                    user.setImages(images);
                }
                User updateUser = userRepository.save(user);
                return Payload.ok(updateUser).response();
            } else {
                return Payload.notFound("Request error").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

    public HttpEntity<?> deleteUser(Long id) {
        try {
            if (id != null) {
                Optional<User> byId = userRepository.findById(id);
                if (!byId.isEmpty()) {
                    userRepository.deleteById(id);
                    Optional<User> delete = userRepository.findById(id);
                    if (delete.isEmpty()) {
                        return Payload.ok(byId.get()).response();
                    } else {
                        return Payload.conflict("User did not delete").response();
                    }
                }else {
                    return Payload.conflict("There is no such user ID").response();
                }
            }else {
                return Payload.conflict("User id error !").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }
}