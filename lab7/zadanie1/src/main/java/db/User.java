package db;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @OneToMany(mappedBy = "owner")
    private List<ThematicList> thematicLists = new ArrayList<ThematicList>();

    @OneToMany(mappedBy = "subscriber")
    private List<Subscribes> subscribes = new ArrayList<Subscribes>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<Notification>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<ThematicList> getThematicLists() {
        return thematicLists;
    }

    public void setThematicLists(List<ThematicList> thematicLists) {
        this.thematicLists = thematicLists;
    }

    public List<Subscribes> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<Subscribes> subscribes) {
        this.subscribes = subscribes;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
