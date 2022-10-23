package gamingforum.gamingforum.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CommentRequest {
    private UUID postId;
    private UUID userId;
    private String comment;
}
