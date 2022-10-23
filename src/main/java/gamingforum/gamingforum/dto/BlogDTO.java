package gamingforum.gamingforum.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class BlogDTO {
    private UUID blogId;
    private String blogTitle;
    private String blogDescription;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
