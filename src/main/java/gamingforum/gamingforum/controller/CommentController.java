package gamingforum.gamingforum.controller;

import gamingforum.gamingforum.dto.CommentDTO;
import gamingforum.gamingforum.model.CommentRequest;
import gamingforum.gamingforum.service.CommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/getComment/{postId}")

    public List<CommentDTO> getComment(@PathVariable UUID postId){
        return commentService.getComment(postId);
    }

    @GetMapping("/getUserComment/{userId}")
    public List<CommentDTO> getUserComment(@PathVariable UUID userId){
        return commentService.getUserComment(userId);
    }

    @GetMapping("/getAll")
    public List<CommentDTO> getAllComment(){
        return commentService.getAllComment();
    }

    @PutMapping("/addComment/{email}/{postId}")

    public CommentDTO addComment(@RequestBody @NonNull CommentRequest commentRequest, @PathVariable String email, @PathVariable UUID postId){
        return commentService.addComment(commentRequest, email, postId);
    }

    @DeleteMapping("/deleteComment/{postId}")

    public CommentDTO deleteComment(@PathVariable UUID postId){
        return commentService.deleteComment(postId);
    }

    @PostMapping("/updateComment/{commentId}")

    public CommentDTO updateComment(@RequestBody CommentRequest commentRequest, @PathVariable UUID commentId){
        return commentService.updateComment(commentRequest, commentId);
    }

}
