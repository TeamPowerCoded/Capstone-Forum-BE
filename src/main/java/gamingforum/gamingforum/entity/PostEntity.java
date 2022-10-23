package gamingforum.gamingforum.entity;
import gamingforum.gamingforum.config.SchemaConfigration;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(schema = SchemaConfigration.SCHEMA_NAME, name="POSTS")
public class PostEntity implements Serializable {

    @Id
    private UUID postId;
    private UUID userId;
    private String postTitle;
    private String postDescription;
    private String postCategory;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostEntity that = (PostEntity) o;
        return postId != null && Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
