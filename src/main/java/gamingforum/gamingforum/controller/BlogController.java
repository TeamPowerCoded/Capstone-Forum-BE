package gamingforum.gamingforum.controller;

import gamingforum.gamingforum.dto.BlogDTO;
import gamingforum.gamingforum.dto.NewsDTO;
import gamingforum.gamingforum.model.BlogRequest;
import gamingforum.gamingforum.model.NewsRequest;
import gamingforum.gamingforum.service.BlogService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/getAll")
    public List<BlogDTO> getAllBlog(){
        return blogService.getAllBlog();
    }

    @PutMapping("/addBlog")
    public List<BlogDTO> addBlog(@RequestBody @NonNull BlogRequest blogRequest){
        return blogService.addBlog(blogRequest);
    }

    @DeleteMapping("/deleteBlog/{blogId}")
    public List<BlogDTO> deleteBlog(@PathVariable UUID blogId){
        return blogService.deleteBlog(blogId);
    }

    @PostMapping("/updateBlog/{blogId}")
    public BlogDTO updateNews(@RequestBody BlogRequest blogRequest, @PathVariable UUID blogId){
        return blogService.updateBlog(blogRequest, blogId);
    }
}
