package eu.me73.luncheon.repository.order;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_order")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 3162207396887246017L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "lunch")
    Long lunchId;

    @Column(name = "user")
    Long userId;

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLunchId() {
        return lunchId;
    }

    public void setLunchId(Long lunchId) {
        this.lunchId = lunchId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", lunchId=" + lunchId +
                ", userId=" + userId +
                '}';
    }
}
