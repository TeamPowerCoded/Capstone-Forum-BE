package gamingforum.gamingforum.repository;

import gamingforum.gamingforum.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, BigInteger> {
    PostEntity findByPostId(UUID postId);

    @Transactional
    void deleteByPostId(UUID postId);
}
