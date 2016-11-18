package eu.me73.luncheon.repository.order;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDaoService extends JpaRepository<OrderEntity, Long> {

    Collection<OrderEntity> findByUserAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(Long id, LocalDate from, LocalDate to);
    Collection<OrderEntity> findByDateOrderByUser(LocalDate date);

    @Query("select o.user as id, count(o.meal) as count " +
            "from OrderEntity as o " +
            "where o.date BETWEEN :from_date and :to_date " +
            "group by o.user ")
    Collection<Object[]> findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(@Param(value = "from_date") LocalDate fdate, @Param(value = "to_date") LocalDate tdate);

    @Query("select count(o) from OrderEntity as o where (o.user = :user) and (o.date BETWEEN :from_date and :to_date) group by o.user")
    Long countByUserAndMonth(@Param(value = "user") Long id, @Param(value = "from_date") LocalDate fdate, @Param(value = "to_date") LocalDate tdate);

    @Query("select o from OrderEntity as o where (o.user = :user) and (o.date BETWEEN :from_date and :to_date)")
    Collection<OrderEntity> findByUserStatistics(@Param(value = "user") Long id, @Param(value = "from_date") LocalDate fdate, @Param(value = "to_date") LocalDate tdate);
}
