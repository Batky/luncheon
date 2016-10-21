package eu.me73.luncheon.order.api;

import org.springframework.stereotype.Service;

@Service
public interface OrderEnabledService {

    void save(final EnabledOrderDate orderDate);
    EnabledOrderDate getDate();

}
