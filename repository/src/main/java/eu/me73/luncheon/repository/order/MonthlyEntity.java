package eu.me73.luncheon.repository.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class MonthlyEntity implements Serializable {

    private static final long serialVersionUID = 3162207396887246447L;

    @Id
    @Column
    private Long id;

    @Column
    private Long count;

    public MonthlyEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
