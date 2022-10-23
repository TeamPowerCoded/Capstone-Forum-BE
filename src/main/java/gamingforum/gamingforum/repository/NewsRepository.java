package gamingforum.gamingforum.repository;

import gamingforum.gamingforum.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, BigInteger> {

    NewsEntity findByNewsId(UUID newsId);

    @Transactional
    void deleteByNewsId(UUID newsId);


}
