package gamingforum.gamingforum.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class NewsDTO {
    private UUID newsId;
    private String newsTitle;
    private String newsDescription;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
