package gamingforum.gamingforum.controller;

import gamingforum.gamingforum.dto.PostDTO;
import gamingforum.gamingforum.dto.UserDTO;
import gamingforum.gamingforum.model.PostRequest;
import gamingforum.gamingforum.service.PostService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/getAll")
    public List<PostDTO> getAllPost(){
        return postService.getAllPost();
    }

    @GetMapping("/getPost/{postId}")
    public PostDTO getPost(@PathVariable UUID postId){
        return postService.getPost(postId);
    }

    @PutMapping("/addPost/{email}")
    public List<PostDTO> createPost(@RequestBody @NonNull PostRequest postRequest, @PathVariable String email){
        return postService.addPost(postRequest, email);
    }

//    @PutMapping(path = "/addPost/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<PostDTO> createPost(@RequestBody @NonNull PostRequest postRequest, @PathVariable String email, @RequestParam("file") MultipartFile file){
//        return postService.addPost(postRequest, email, file);
//    }

    @DeleteMapping("/deletePost/{postId}")
    public List<PostDTO> deletePost(@PathVariable UUID postId){
        return postService.deletePost(postId);
    }

    @PostMapping("/updatePost/{postId}")
    public PostDTO updatePost( @RequestBody PostRequest postRequest, @PathVariable UUID postId){
        return postService.updatePost(postRequest, postId);
    }


}
