package gamingforum.gamingforum.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class PostDTO {
    private UUID postId;
    private UUID userId;
    private String postTitle;
    private String postDescription;
    private String postCategory;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
