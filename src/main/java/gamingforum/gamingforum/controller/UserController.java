package gamingforum.gamingforum.controller;

import gamingforum.gamingforum.dto.UserDTO;
import gamingforum.gamingforum.model.UserRequest;
import gamingforum.gamingforum.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getCurrentUser/{email}")
    public UserDTO getUser(@PathVariable String email){
        return userService.getCurrentUser(email);
    }

    @PutMapping("/signup")
    public UserDTO createUser(@RequestBody @NonNull UserRequest userRequest){
        return userService.saveUser(userRequest);
    }
    
    @PostMapping("/login")
    public UserDTO loginUser(@RequestBody @NonNull UserRequest userRequest){
        return userService.loginUser(userRequest);
    }
    @PutMapping(path = "/{userId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> uploadUserImage(@PathVariable UUID userId, @RequestParam("file") MultipartFile file){
        return userService.uploadUserImage(userId, file);
    }

    @GetMapping(path = "{userId}/download")
    public byte[] downloadProductImage(@PathVariable("userId") UUID userProfileId){
        return userService.downloadUserImage(userProfileId);
    }

}
