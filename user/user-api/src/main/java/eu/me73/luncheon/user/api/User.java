package eu.me73.luncheon.user.api;

import eu.me73.luncheon.repostiory.UserEntity;
import eu.me73.luncheon.repostiory.UserRelation;
import java.util.Objects;

public class User {

    private static final String SPACE = " ";

    private Long id = 0L;
    private String barCode;
    private String firstName;
    private String lastName;
    private UserRelation relation;

    public User(final Long id,
                final String barCode,
                final String firstName,
                final String lastName,
                final UserRelation relation) {
        this.id = id;
        this.barCode = barCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.relation = relation;
    }

    public User(final UserEntity entity) {
        this.id = entity.getId();
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

    public String getLongName(){
        return firstName + SPACE + lastName;
    }

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(this.getId());
        userEntity.setBarCode(this.getBarCode());
        userEntity.setFirstName(this.getFirstName());
        userEntity.setLastName(this.getLastName());
        userEntity.setRelation(this.getRelation());
        return userEntity;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", barCode='" + barCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relation=" + relation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!barCode.equals(user.barCode)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        return relation == user.relation;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + barCode.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (relation != null ? relation.hashCode() : 0);
        return result;
    }
}

