package db;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "follows_entity")
public class Follows implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book followedBook;

    @Id
    @ManyToOne
    @JoinColumn(name = "followerId")
    private User follower;

    public Book getFollowedBook() {
        return followedBook;
    }

    public void setFollowedBook(Book followedBook) {
        this.followedBook = followedBook;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}