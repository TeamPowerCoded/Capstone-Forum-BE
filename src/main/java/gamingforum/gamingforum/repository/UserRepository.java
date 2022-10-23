package gamingforum.gamingforum.repository;

import gamingforum.gamingforum.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(UUID userId);

    @Transactional
    void deleteByEmail(String email);
}
