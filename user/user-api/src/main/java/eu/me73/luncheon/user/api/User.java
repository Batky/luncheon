package eu.me73.luncheon.user.api;

import eu.me73.luncheon.repository.user.UserEntity;
import eu.me73.luncheon.repository.user.UserRelation;
import java.util.Objects;

public class User {

    private static final String SPACE = " ";

    private Long id = 0L;
    private String pid;
    private String barCode;
    private String firstName;
    private String lastName;
    private UserRelation relation;

    public User(final Long id,
                final String pid,
                final String barCode,
                final String firstName,
                final String lastName,
                final UserRelation relation) {
        this.id = id;
        this.pid = pid;
        this.barCode = barCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.relation = relation;
    }

    public User(final UserEntity entity) {
        this.id = entity.getId();
        this.pid = entity.getPid();
        this.barCode = entity.getBarCode();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        if (Objects.nonNull(entity.getRelation())) {
            this.relation = UserRelation.EMPLOYEE;
        } else {
            this.relation = entity.getRelation();
        }
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(final String barCode) {
        this.barCode = barCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public UserRelation getRelation() {
        return relation;
    }

    public void setRelation(final UserRelation relation) {
        this.relation = relation;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLongName() {
        return firstName + SPACE + lastName;
    }

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(this.getId());
        userEntity.setPid(this.getPid());
        userEntity.setBarCode(this.getBarCode());
        userEntity.setFirstName(this.getFirstName());
        userEntity.setLastName(this.getLastName());
        userEntity.setRelation(this.getRelation());
        return userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!pid.equals(user.pid)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        return lastName != null ? lastName.equals(user.lastName) : user.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = pid.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", barCode='" + barCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relation=" + relation +
                '}';
    }

}

