package eu.me73.luncheon.repository.lunch;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchDaoService extends JpaRepository<LunchEntity, Long> {

    Collection<LunchEntity> findByDateGreaterThanEqualOrderByDate(LocalDate date);

}
