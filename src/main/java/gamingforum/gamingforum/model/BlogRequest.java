package gamingforum.gamingforum.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BlogRequest {
    private UUID blogId;
    private String blogTitle;
    private String blogDescription;
}
