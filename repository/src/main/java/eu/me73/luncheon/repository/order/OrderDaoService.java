package eu.me73.luncheon.repository.order;

import java.time.LocalDate;
import java.util.Collection;
import java.util.function.BooleanSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDaoService extends JpaRepository<OrderEntity, Long> {

    Collection<OrderEntity> findByUserAndDateGreaterThanEqualAndDateLessThanEqualOrderByDate(Long id, LocalDate from, LocalDate to);
    Collection<OrderEntity> findByDateOrderByUser(LocalDate date);
}
