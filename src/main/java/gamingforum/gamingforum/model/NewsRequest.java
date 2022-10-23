package gamingforum.gamingforum.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewsRequest {
    private UUID newsId;
    private String newsTitle;
    private String newsDescription;
}
