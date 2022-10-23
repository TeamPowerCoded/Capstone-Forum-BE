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
@Table(schema = SchemaConfigration.SCHEMA_NAME, name="USERS")

public class UserEntity {
    @Id
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String userType;
    private String imageLink;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
