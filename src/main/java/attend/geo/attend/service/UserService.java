package attend.geo.attend.service;

import attend.geo.attend.dto.PageRequests;
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
//        try {
//            if (userDto != null) {
//                Random random = new Random();
//                long min = 100_000L;
//                long max = 999_999L;
//                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;
//
//                if (randomSixDigitNumber < 0) {
//                    randomSixDigitNumber = -randomSixDigitNumber;
//                }
//                String firstName = userDto.getFirstName();
//                String lastName = userDto.getLastName();
//                User user = new User();
//                user.setFirstName(userDto.getFirstName());
//                user.setLastName(userDto.getLastName());
//                user.setUserName(firstName+lastName);
//                user.setIsBlocked(userDto.getIsBlocked());
//                user.setPosition(userDto.getPosition());
//                user.setRandomCode(randomSixDigitNumber);
//                if (userDto.getTokens() != null && !userDto.getTokens().isEmpty()) {
//                    List<Images> images = new ArrayList<>();
//                    for (String token : userDto.getTokens()) {
//                        Optional<Images> imagesOptional = imageRepository.findByToken(UUID.fromString(token));
//                        imagesOptional.ifPresent(images::add);
//                    }
//                    user.setImages(images);
//                }
//                user = userRepository.save(user);
//                return Payload.ok(user).response();
//            } else {
//                return Payload.notFound().response();
//            }
//        } catch (Exception e) {
//            return Payload.notFound("Server bilan muammo").response();
//        }
        return null;
    }


    public HttpEntity<?> addUserAndFile(UserRequest request) {
        try {
            User user = new User();
            List<MultipartFile> files = request.getFiles();
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
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUserName(firstName + lastName);
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
            user.setConnection(false);
            User saveUser = userRepository.save(user);
            return Payload.ok(saveUser).response();
        }catch (Exception e){
            e.printStackTrace();
            return Payload.conflict(e.getMessage()).response();
        }
    }

    public HttpEntity<?> getAllUserRequest(PageRequests request) {
        try {
            if (request != null) {
                Page<User> all = userRepository.findAll(PageRequest.of(request.getPageNumber(), request.getPageSize()));
                List<User> userList = all.stream().map(r ->
                        new User(r.getId(),
                                r.getFirstName(),
                                r.getLastName(),
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

    public HttpEntity<?> updateUser(UserRequest request, Long id) {
        try {
            Optional<User> byId = userRepository.findById(id);
            if (!byId.isEmpty()) {
                User user = new User();
                List<MultipartFile> files = request.getFiles();
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
                String firstName = request.getFirstName();
                String lastName = request.getLastName();
                user.setId(id);
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setUserName(firstName + lastName);
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
                user.setConnection(false);
                User saveUser = userRepository.save(user);
                return Payload.ok(saveUser).response();
            }else {
                return Payload.conflict("User not found").response();
            }
        }catch (Exception e){
            e.printStackTrace();
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
                } else {
                    return Payload.conflict("There is no such user ID").response();
                }
            } else {
                return Payload.conflict("User id error !").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

    public HttpEntity<?> getUserId(Long id) {
        try {
            Optional<User> byId = userRepository.findById(id);
            if (!byId.isEmpty()) {
                return Payload.ok(byId.get()).response();
            } else {
                return Payload.conflict("User not found").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }
}