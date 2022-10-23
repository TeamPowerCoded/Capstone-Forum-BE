package gamingforum.gamingforum.entity;

import gamingforum.gamingforum.config.SchemaConfigration;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(schema = SchemaConfigration.SCHEMA_NAME, name="NEWS")
public class NewsEntity {

    @Id
    private UUID newsId;
    private String newsTitle;
    private String newsDescription;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewsEntity that = (NewsEntity) o;
        return newsId != null && Objects.equals(newsId, that.newsId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
