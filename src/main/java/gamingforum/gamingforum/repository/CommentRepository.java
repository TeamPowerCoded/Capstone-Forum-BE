package gamingforum.gamingforum.repository;

import gamingforum.gamingforum.entity.CommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, BigInteger> {
    List<CommentEntity> findByPostId(UUID postId, Sort createdDate);
    List<CommentEntity> findByUserId(UUID postId, Sort createdDate);
    CommentEntity findByCommentId(UUID commentId);


    @Transactional
    void deleteByCommentId(UUID commentId);
}

