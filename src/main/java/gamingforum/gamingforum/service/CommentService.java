package gamingforum.gamingforum.service;


import gamingforum.gamingforum.dto.CommentDTO;
import gamingforum.gamingforum.dto.PostDTO;
import gamingforum.gamingforum.dto.UserDTO;
import gamingforum.gamingforum.entity.CommentEntity;
import gamingforum.gamingforum.entity.PostEntity;
import gamingforum.gamingforum.entity.UserEntity;
import gamingforum.gamingforum.exception.EntryAlreadyExist;
import gamingforum.gamingforum.model.CommentRequest;
import gamingforum.gamingforum.repository.CommentRepository;
import gamingforum.gamingforum.repository.PostRepository;
import gamingforum.gamingforum.repository.UserRepository;
import gamingforum.gamingforum.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    public List<CommentDTO> getAllComment(){
        // Get all data from database
        List<CommentEntity> allComments = commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        // Initialize dto
        List<CommentDTO> allCommentsDTO = new ArrayList<>();

        allComments.forEach(comment -> {
            allCommentsDTO.add(modelMapper.map(comment, CommentDTO.class));
        });

        return allCommentsDTO;
    }
    public List<CommentDTO> getComment(UUID postId){
        //get post details

        List<CommentEntity> allComment = commentRepository.findByPostId(postId, Sort.by(Sort.Direction.ASC, "createdDate"));

        List<CommentDTO> allCommentDto = new ArrayList<>();

        allComment.forEach(comment -> {
            allCommentDto.add(modelMapper.map(comment, CommentDTO.class));
        });

    return allCommentDto;

    }

    public List<CommentDTO> getUserComment(UUID userId){
        //get comment details
        List<CommentEntity> allCommentOfUser = commentRepository.findByUserId(userId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<CommentDTO> allCommentDto = new ArrayList<>();

        allCommentOfUser.forEach(comment -> {
            allCommentDto.add(modelMapper.map(comment, CommentDTO.class));
        });
        return allCommentDto;
    }
    public CommentDTO addComment(@NonNull CommentRequest newComment, String email, UUID postId){
        //Get user details
        UserEntity user = userRepository.findByEmail(email);

        if(user == null ) throw new EntryAlreadyExist("User does not exist");

        //Get post details

        PostEntity post = postRepository.findByPostId(postId);
        if(post == null ) throw new EntryAlreadyExist("Post does not exist");

        CommentEntity comment = CommentEntity
                .builder()
                .commentId(UUID.randomUUID())
                .postId(post.getPostId())
                .userId(user.getUserId())
                .comment(newComment.getComment())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save to database
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO deleteComment(UUID commentId){
        CommentEntity comment = commentRepository.findByCommentId(commentId);
        //check if comment exist
        if(comment == null) throw new EntryAlreadyExist("Comment does not exist");
        commentRepository.deleteByCommentId(commentId);
        return modelMapper.map(comment, CommentDTO.class);

    }

    public CommentDTO updateComment(CommentRequest editedComment, UUID commentId){
        CommentEntity comment = commentRepository.findByCommentId(commentId);
        //check if comment exist
        if(comment == null) throw new EntryAlreadyExist("Comment does not exist");

        CommentEntity updatedComment = CommentEntity
                .builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .comment(editedComment.getComment())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        commentRepository.save(updatedComment);

        return modelMapper.map(updatedComment, CommentDTO.class);

    }

}
