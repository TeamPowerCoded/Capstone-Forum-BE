package gamingforum.gamingforum.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class CommentDTO {
    private UUID commentId;
    private UUID postId;
    private UUID userId;
    private String comment;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
