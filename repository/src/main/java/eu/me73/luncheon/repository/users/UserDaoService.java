package eu.me73.luncheon.repository.users;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDaoService extends JpaRepository<UserEntity, Long> {

    Collection<UserEntity> findByBarCode(String barCode);
    UserEntity findByPid(String pid);

    Collection<UserEntity> findAllByOrderByLastNameAscFirstNameAsc();
}
