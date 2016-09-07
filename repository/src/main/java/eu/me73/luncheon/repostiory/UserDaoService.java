package eu.me73.luncheon.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDaoService extends JpaRepository<UserEntity, Long> {

}
