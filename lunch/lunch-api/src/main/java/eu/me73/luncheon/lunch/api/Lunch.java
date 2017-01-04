package eu.me73.luncheon.lunch.api;

import eu.me73.luncheon.repository.lunch.LunchEntity;
import java.time.LocalDate;
import java.util.Objects;

public class Lunch {

    private Long id = 0L;
    private Boolean soup;
    private LocalDate date;
    private String description;
    private Boolean stable;


    public Lunch(final LunchEntity entity) {
        this.id = entity.getId();
        this.soup = entity.getSoup();
        this.date = entity.getDate();
        this.description = entity.getDescription();
        this.stable = Objects.nonNull(entity.getStable()) ? entity.getStable() : false;
    }

    public Lunch(
            final Long id,
            final Boolean soup,
            final LocalDate date,
            final String description,
            final Boolean stable) {
        this.id = id;
        this.soup = soup;
        this.date = date;
        this.description = description;
        this.stable = stable;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "id=" + id +
                ", soup=" + soup +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", stable=" + stable +
                '}';
    }

    public Boolean getStable() {
        return stable;
    }

    public void setStable(Boolean stable) {
        this.stable = stable;
    }

    public LunchEntity toEntity() {
        LunchEntity lunchEntity = new LunchEntity();
        lunchEntity.setId(this.getId());
        lunchEntity.setSoup(this.soup);
        lunchEntity.setDate(this.date);
        lunchEntity.setDescription(this.description);
        lunchEntity.setStable(this.stable);
        return lunchEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lunch lunch = (Lunch) o;

        return id != null ? id.equals(lunch.id) : lunch.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

