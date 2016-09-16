package eu.me73.luncheon.lunch.api;

import eu.me73.luncheon.repository.lunch.LunchEntity;
import java.util.Date;

public class Lunch {

    private Long id = 0L;
    private Boolean soup;
    private Date date;
    private String description;


    public Lunch(final LunchEntity entity) {
        this.id = entity.getId();
        this.soup = entity.getSoup();
        this.date = entity.getDate();
        this.description = entity.getDescription();

    }

    public Lunch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    public LunchEntity toEntity() {
        LunchEntity lunchEntity = new LunchEntity();
        lunchEntity.setId(this.getId());
        lunchEntity.setSoup(this.soup);
        lunchEntity.setDate(this.date);
        lunchEntity.setDescription(this.description);
        return lunchEntity;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "id=" + id +
                ", soup=" + soup +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lunch lunch = (Lunch) o;

        if (!id.equals(lunch.id)) return false;
        if (soup != null ? !soup.equals(lunch.soup) : lunch.soup != null) return false;
        if (date != null ? !date.equals(lunch.date) : lunch.date != null) return false;
        return description != null ? description.equals(lunch.description) : lunch.description == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (soup != null ? soup.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}

