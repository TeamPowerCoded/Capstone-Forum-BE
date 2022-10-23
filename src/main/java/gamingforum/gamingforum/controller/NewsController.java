package gamingforum.gamingforum.controller;

import gamingforum.gamingforum.dto.NewsDTO;
import gamingforum.gamingforum.model.NewsRequest;
import gamingforum.gamingforum.service.NewsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/getAll")
    public List<NewsDTO> getAllNews(){
        return newsService.getAllNews();
    }

    @PutMapping("/addNews")
    public List<NewsDTO> addNews(@RequestBody @NonNull NewsRequest newsRequest){
        return newsService.addNews(newsRequest);
    }

    @DeleteMapping("/deleteNews/{newsId}")
    public List<NewsDTO> deleteNews(@PathVariable UUID newsId){
        return newsService.deleteNews(newsId);
    }

    @PostMapping("/updateNews/{newsId}")
    public NewsDTO updateNews(@RequestBody NewsRequest newsRequest, @PathVariable UUID newsId){
        return newsService.updateNews(newsRequest, newsId);
    }
}
