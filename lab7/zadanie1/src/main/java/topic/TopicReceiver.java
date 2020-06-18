package topic;

import bean.AppBean;
import db.*;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = "java:/jms/topic/SOA_Test"),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "javax.jms.Topic")
        })
public class TopicReceiver implements MessageListener {

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String thematicListName = textMessage.getStringProperty("name");
                String comment = textMessage.getStringProperty("comment");
                String toUser = textMessage.getStringProperty("toUser");

                AppBean appBean = AppBean.getInstance();
                EntityManager entityManager = appBean.getFactory().createEntityManager();

                if (toUser.equals("")) {
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<ThematicList> criteriaQuery = criteriaBuilder.createQuery(ThematicList.class);
                    Root<ThematicList> thematicListRoot = criteriaQuery.from(ThematicList.class);
                    criteriaQuery.select(thematicListRoot).where(criteriaBuilder.equal(thematicListRoot.get("name"), thematicListName));
                    TypedQuery<ThematicList> typedQuery = entityManager.createQuery(criteriaQuery);
                    ThematicList thematicList = typedQuery.getSingleResult();

                    criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<Subscribes> c = criteriaBuilder.createQuery(Subscribes.class);
                    Root<Subscribes> root = c.from(Subscribes.class);
                    c.select(root).where(criteriaBuilder.equal(root.get("subscribedThematicList"), thematicList.getId()));
                    TypedQuery<Subscribes> t = entityManager.createQuery(c);

                    List<Subscribes> list = t.getResultList();
                    if (!list.isEmpty()) {
                        try {
                            entityManager.getTransaction().begin();
                            for (Subscribes s : list) {
                                Notification notification = new Notification();
                                notification.setText(thematicListName + ": " + comment);
                                notification.setUser(s.getSubscriber());
                                entityManager.persist(notification);
                            }
                            entityManager.getTransaction().commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                    Root<User> userRoot = criteriaQuery.from(User.class);
                    criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("login"), toUser));
                    TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

                    if (!typedQuery.getResultList().isEmpty()) {
                        try {
                            User user = typedQuery.getSingleResult();
                            entityManager.getTransaction().begin();
                            Notification notification = new Notification();
                            notification.setText(thematicListName + ": " + comment);
                            notification.setUser(user);
                            entityManager.persist(notification);
                            entityManager.getTransaction().commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
