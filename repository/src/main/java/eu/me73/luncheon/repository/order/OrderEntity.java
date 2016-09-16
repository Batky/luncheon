package eu.me73.luncheon.repository.order;

import eu.me73.luncheon.repository.lunch.LunchEntity;
import eu.me73.luncheon.repository.user.UserEntity;
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
    LunchEntity lunch;

    @Column(name = "user")
    UserEntity user;

    public OrderEntity(LunchEntity lunch) {
        this.lunch = lunch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LunchEntity getLunch() {
        return lunch;
    }

    public void setLunch(LunchEntity lunch) {
        this.lunch = lunch;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", lunch=" + lunch +
                ", user=" + user +
                '}';
    }
}
