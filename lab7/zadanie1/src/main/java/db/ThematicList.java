package db;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thematicList_entity")
public class ThematicList {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    @OneToMany(mappedBy = "thematicList")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "subscribedThematicList")
    private List<Subscribes> subscribes = new ArrayList<Subscribes>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Subscribes> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<Subscribes> subscribes) {
        this.subscribes = subscribes;
    }
}
