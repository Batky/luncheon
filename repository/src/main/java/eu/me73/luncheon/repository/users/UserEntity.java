package eu.me73.luncheon.repository.users;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -3817243569456830258L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    Long id;

    @Column(name = "pid", unique = true, nullable = false)
    String pid;

    @Column(name = "barcode", unique = true)
    String barCode;

    @Column(name = "first")
    String firstName;

    @Column(name = "last")
    String lastName;

    @Column(name = "relation")
    UserRelation relation;

    public UserEntity() {
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", barCode='" + barCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relation=" + relation +
                '}';
    }

}
