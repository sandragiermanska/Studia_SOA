package db;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "wantNewBookNotifications")
    private boolean wantNewBookNotifications;

    @OneToMany(mappedBy = "user")
    private List<Borrow> borrows;

    @OneToMany(mappedBy = "follower")
    private List<Follows> follows;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    public boolean isWantNewBookNotifications() {
        return wantNewBookNotifications;
    }

    public void setWantNewBookNotifications(boolean wantNewBookNotifications) {
        this.wantNewBookNotifications = wantNewBookNotifications;
    }

    public List<Follows> getFollows() {
        return follows;
    }

    public void setFollows(List<Follows> follows) {
        this.follows = follows;
    }
}

