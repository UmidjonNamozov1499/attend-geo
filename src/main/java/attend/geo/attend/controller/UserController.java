package attend.geo.attend.controller;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.PageRequests;
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
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/addUser")
    public HttpEntity<?> addUser(@RequestBody UserDto request ) {
        return userService.addUser(request);
    }

    @PostMapping(value = "/addUserAndFile")
    public HttpEntity<?> addUserAndFile(@ModelAttribute UserRequest request){
        return userService.addUserAndFile(request);
    }
    @GetMapping(value = "/getUsers")
    public HttpEntity<?> getAllUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        PageRequests request = new PageRequests(pageNumber,pageSize);
        return userService.getAllUserRequest(request);
    }
    @GetMapping(value = "/getUsers/{id}")
    HttpEntity<?> getUserId(@PathVariable Long id){
        return userService.getUserId(id);
    }
    @PutMapping(value = "/updateUser/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> updateUser(@ModelAttribute UserRequest request,@PathVariable Long id){
        return userService.updateUser(request,id);
    }
    @DeleteMapping(value = "/deleteUser/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
