package gamingforum.gamingforum.service;

import gamingforum.gamingforum.dto.NewsDTO;
import gamingforum.gamingforum.dto.PostDTO;
import gamingforum.gamingforum.entity.NewsEntity;
import gamingforum.gamingforum.entity.PostEntity;
import gamingforum.gamingforum.entity.UserEntity;
import gamingforum.gamingforum.exception.EntryAlreadyExist;
import gamingforum.gamingforum.model.NewsRequest;
import gamingforum.gamingforum.model.PostRequest;
import gamingforum.gamingforum.repository.NewsRepository;
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
public class NewsService {
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final DateTimeUtil dateTimeUtil;

    public List<NewsDTO> getAllNews(){
        //Get All News from database

        List<NewsEntity> allNews = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        // Initialize dto

        List<NewsDTO> allNewsDto = new ArrayList<>();

        allNews.forEach(news -> {
            allNewsDto.add(modelMapper.map(news, NewsDTO.class));
        });

        return allNewsDto;
    }

    public List<NewsDTO> addNews(@NonNull NewsRequest newNews){
        //Get user details

        //Add news
        NewsEntity news = NewsEntity
                .builder()
                .newsId(UUID.randomUUID())
                .newsTitle(newNews.getNewsTitle())
                .newsDescription(newNews.getNewsDescription())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save to database
        newsRepository.save(news);

        return getAllNews();
    }

    public List<NewsDTO> deleteNews(UUID newsId){
        //get news
        NewsEntity news =  newsRepository.findByNewsId(newsId);

        //check if news Exist
        if(news == null) throw new EntryAlreadyExist("News does not exist");

        //delete news
        newsRepository.deleteByNewsId(newsId);

        return getAllNews();
    }

    public NewsDTO updateNews(NewsRequest editedNews, UUID newsId){
        //get news
        NewsEntity news =  newsRepository.findByNewsId(newsId);
        //check if news Exist
        if(news == null) throw new EntryAlreadyExist("News does not exist");

        //update news
        NewsEntity updatedNews = NewsEntity
                .builder()
                .newsId(news.getNewsId())
                .newsTitle(editedNews.getNewsTitle())
                .newsDescription(editedNews.getNewsDescription())
                .imageLink(null)
                .createdDate(news.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        //save updated post
        newsRepository.save(updatedNews);

        return modelMapper.map(updatedNews, NewsDTO.class);
    }
}
