package eu.me73.luncheon.repository.lunch;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LunchDaoService extends JpaRepository<LunchEntity, Long> {

    //TODO: decide which one is better approach and why Query annotation or spring JPA 'terrible' mathod name combination
    @Query(value = "SELECT lunch FROM LunchEntity AS lunch WHERE lunch.date BETWEEN :from_date AND :to_date")
    Collection<LunchEntity> findActualTwoWeeks(
            @Param("from_date") LocalDate fromDate,
            @Param("to_date") LocalDate toDate
    );
    Collection<LunchEntity> findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(LocalDate fromDate, LocalDate toDate);

    Collection<LunchEntity> findByDateAndSoupOrderById(LocalDate date, boolean soup);

    Collection<LunchEntity> findByDate(LocalDate date);
}
