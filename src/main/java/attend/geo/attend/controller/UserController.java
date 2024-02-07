package attend.geo.attend.controller;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.UserDto;
import attend.geo.attend.dto.UserRequest;
import attend.geo.attend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ComponentScan("controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/addUser")
    public HttpEntity<?> addUser(@RequestBody UserDto request ) {
        return userService.addUser(request);
    }

    @PostMapping(value = "/addUserAndFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> addUserAndFile(@RequestPart(name = "file",required = false) MultipartFile file, @RequestPart(name = "files",required = false) List<MultipartFile> files, @RequestPart(name = "request") UserRequest request){
        return userService.addUserAndFile(file,files,request);
    }

    @GetMapping(value = "/getUsers")
    public HttpEntity<?> getAllUsers(@RequestBody GetAllUserRequest request){
        return userService.getAllUserRequest(request);
    }
    @PutMapping(value = "/updateUser/{id}")
    public HttpEntity<?> updateUser(@RequestBody UserDto userDto,@PathVariable Long id){
        return userService.updateUser(userDto,id);
    }
    @DeleteMapping(value = "/deleteUser/{id}")
    public HttpEntity<?> deleteUser(@RequestParam Long id){
        return userService.deleteUser(id);
    }
}
