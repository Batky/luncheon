package eu.me73.luncheon.repository.lunch;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_lunch")
public class LunchEntity implements Serializable {

    private static final long serialVersionUID = 1676565102643643720L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "soup")
    Boolean soup;

    @Column(name = "date")
    Date date;

    @Column(name = "description")
    String description;

    public LunchEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSoup() {
        return soup;
    }

    public void setSoup(Boolean soup) {
        this.soup = soup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LunchEntity(Boolean soup) {
        this.soup = soup;


    }

    @Override
    public String toString() {
        return "LunchEntity{" +
                "id=" + id +
                ", soup=" + soup +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
