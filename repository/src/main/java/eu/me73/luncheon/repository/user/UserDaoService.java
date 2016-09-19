package eu.me73.luncheon.repository.user;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDaoService extends JpaRepository<UserEntity, Long> {

    Collection<UserEntity> findByBarCode(String barCode);
}
