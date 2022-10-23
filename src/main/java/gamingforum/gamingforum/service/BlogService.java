package gamingforum.gamingforum.service;

import gamingforum.gamingforum.dto.BlogDTO;
import gamingforum.gamingforum.dto.NewsDTO;
import gamingforum.gamingforum.entity.BlogEntity;
import gamingforum.gamingforum.entity.NewsEntity;
import gamingforum.gamingforum.exception.EntryAlreadyExist;
import gamingforum.gamingforum.model.BlogRequest;
import gamingforum.gamingforum.model.NewsRequest;
import gamingforum.gamingforum.repository.BlogRepository;
import gamingforum.gamingforum.repository.NewsRepository;
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
public class BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    public List<BlogDTO> getAllBlog(){
        //Get All News from database

        List<BlogEntity> allBlog = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        // Initialize dto

        List<BlogDTO> allBlogDto = new ArrayList<>();

        allBlog.forEach(blog -> {
            allBlogDto.add(modelMapper.map(blog, BlogDTO.class));
        });

        return allBlogDto;
    }

    public List<BlogDTO> addBlog(@NonNull BlogRequest newBlog){

        //Add blog
        BlogEntity blog = BlogEntity
                .builder()
                .blogId(UUID.randomUUID())
                .blogTitle(newBlog.getBlogTitle())
                .blogDescription(newBlog.getBlogDescription())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save to database
        blogRepository.save(blog);

        return getAllBlog();
    }

    public List<BlogDTO> deleteBlog(UUID blogId){
        //get blog
        BlogEntity blog =  blogRepository.findByBlogId(blogId);

        //check if blog Exist
        if(blog == null) throw new EntryAlreadyExist("Blog does not exist");

        //delete news
        blogRepository.deleteByBlogId(blogId);

        return getAllBlog();
    }

    public BlogDTO updateBlog(BlogRequest editedBlog, UUID blogId){
        //get blog
        BlogEntity blog =  blogRepository.findByBlogId(blogId);
        //check if blog Exist
        if(blog == null) throw new EntryAlreadyExist("Blog does not exist");

        //update news
        BlogEntity updatedBlog = BlogEntity
                .builder()
                .blogId(blog.getBlogId())
                .blogTitle(editedBlog.getBlogTitle())
                .blogDescription(editedBlog.getBlogDescription())
                .imageLink(null)
                .createdDate(blog.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save updated blog
        blogRepository.save(updatedBlog);

        return modelMapper.map(updatedBlog, BlogDTO.class);
    }
}
