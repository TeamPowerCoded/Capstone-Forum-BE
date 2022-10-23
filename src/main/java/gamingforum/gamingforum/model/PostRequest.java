package gamingforum.gamingforum.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder

public class PostRequest {

    private UUID userId;
    private String postTitle;
    private String postDescription;
    private String postCategory;

}