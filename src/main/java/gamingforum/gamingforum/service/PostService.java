package gamingforum.gamingforum.service;

import gamingforum.gamingforum.dto.PostDTO;
import gamingforum.gamingforum.entity.PostEntity;
import gamingforum.gamingforum.entity.UserEntity;
import gamingforum.gamingforum.exception.EntryAlreadyExist;
import gamingforum.gamingforum.model.PostRequest;
import gamingforum.gamingforum.repository.PostRepository;
import gamingforum.gamingforum.repository.UserRepository;
import gamingforum.gamingforum.util.DateTimeUtil;
import gamingforum.gamingforum.util.S3StorageUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    private final S3StorageUtil s3StorageUtil;

    public List<PostDTO> getAllPost(){
        //Get All Post from database

        List<PostEntity> allPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        // Initialize dto

        List<PostDTO> allPostDto = new ArrayList<>();

        allPosts.forEach(post -> {
            allPostDto.add(modelMapper.map(post, PostDTO.class));
        });

        return allPostDto;
    }
    public List<PostDTO> addPost(@NonNull PostRequest newPost, String email){
        //Get user details

        UserEntity user = userRepository.findByEmail(email);
        if(user == null) throw new EntryAlreadyExist("User does not exist");
        //Add post
        PostEntity post = PostEntity
                .builder()
                .postId(UUID.randomUUID())
                .userId(user.getUserId())
                .postTitle(newPost.getPostTitle())
                .postDescription(newPost.getPostDescription())
                .postCategory(newPost.getPostCategory())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save to database
        postRepository.save(post);

        return getAllPost();
    }
    public List<PostDTO> deletePost(UUID postId){
        //get post
        PostEntity post =  postRepository.findByPostId(postId);

        //check if post Exist
        if(post == null) throw new EntryAlreadyExist("Post does not exist");

        //delete post
        postRepository.deleteByPostId(postId);

        return getAllPost();
    }

    public PostDTO getPost(UUID postId){
        //get post
        PostEntity post = postRepository.findByPostId(postId);
        //check if post Exist
        if(post == null) throw new EntryAlreadyExist("Post does not exist");

        return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO updatePost(PostRequest editedPost, UUID postId){
        //get post
        PostEntity post =  postRepository.findByPostId(postId);
        //check if post Exist
        if(post == null) throw new EntryAlreadyExist("Post does not exist");

        //update post
        PostEntity updatedPost = PostEntity
                .builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .postTitle(editedPost.getPostTitle())
                .postDescription(editedPost.getPostDescription())
                .postCategory(post.getPostCategory())
                .createdDate(post.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save updated post
        postRepository.save(updatedPost);

        return modelMapper.map(updatedPost, PostDTO.class);
    }

}
