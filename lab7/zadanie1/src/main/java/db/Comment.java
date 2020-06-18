package db;

import javax.persistence.*;

@Entity
@Table(name = "comment_entity")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "thematicListId")
    private ThematicList thematicList;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "toUserId", nullable = true)
    private User toUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ThematicList getThematicList() {
        return thematicList;
    }

    public void setThematicList(ThematicList thematicList) {
        this.thematicList = thematicList;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
