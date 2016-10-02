package eu.me73.luncheon.repository.order;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import eu.me73.luncheon.repository.users.UserEntity;

public interface OrderDaoService extends JpaRepository<OrderEntity, Long> {

    Collection<OrderEntity> findByUserAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(Long id, LocalDate from, LocalDate to);
    Collection<OrderEntity> findByDateOrderByUser(LocalDate date);
//    @Query("select o.user_id, count(o.meal) " +
//            "from OrderEntity as o " +
//            "where o.date BETWEEN :from_date and :to_date " +
//            "group by o.user_id " +
//            "order by o.user_id")
    @Query("select u.lastname, u.firstName, count(o.meal) " +
            "from OrderEntity as o " +
            "inner join UserEntity as u " +
            "where o.date BETWEEN :from_date and :to_date " +
            "group by u.lastName, u.firstName, o.user " +
            "order by u.lastName, u.firstName")
    Collection<MonthlyReportEntity> findMonthlyOrders(@Param("from_date") LocalDate fdate, @Param("to_date") LocalDate tdate);
}
