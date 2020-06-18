package db;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subscribe_entity")
public class Subscribes implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "thematicListId")
    private ThematicList subscribedThematicList;

    @Id
    @ManyToOne
    @JoinColumn(name = "subscriberId")
    private User subscriber;

    public ThematicList getSubscribedThematicList() {
        return subscribedThematicList;
    }

    public void setSubscribedThematicList(ThematicList subscribedThematicList) {
        this.subscribedThematicList = subscribedThematicList;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }
}
