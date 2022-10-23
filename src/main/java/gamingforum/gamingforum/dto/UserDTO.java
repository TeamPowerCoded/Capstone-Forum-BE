package gamingforum.gamingforum.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String username;
    private String email;
    private String userType;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
}
