package eu.me73.luncheon.repository.order;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_enabled")
public class EnabledEntity implements Serializable {

    private static final long serialVersionUID = 4287341775115004532L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "month")
    Integer month;

    @Column(name = "year")
    Integer year;

    public EnabledEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "EnabledEntity{" +
                "id=" + id +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

}
