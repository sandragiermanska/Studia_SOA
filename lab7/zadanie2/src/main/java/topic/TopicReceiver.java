package topic;

import bean.AppBean;
import db.Follows;
import db.Notification;
import db.User;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Iterator;
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
                String notificationType = textMessage.getStringProperty("notificationType");
                String text = textMessage.getStringProperty("text");
                int id = textMessage.getIntProperty("id");

                AppBean appBean = AppBean.getInstance();
                EntityManagerFactory factory = appBean.getFactory();

                if (notificationType.equals(TopicSender.NotificationType.newBook.name())) {
                    EntityManager entityManager = factory.createEntityManager();
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                    Root<User> root = criteriaQuery.from(User.class);
                    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("wantNewBookNotifications"), true));
                    TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

                    for (User user : typedQuery.getResultList()) {
                        entityManager = factory.createEntityManager();
                        Notification notification = new Notification();
                        notification.setUser(user);
                        notification.setText(text);
                        entityManager.getTransaction().begin();
                        entityManager.persist(notification);
                        entityManager.getTransaction().commit();
                    }

                } else if (notificationType.equals(TopicSender.NotificationType.returnBook.name())) {
                    EntityManager entityManager = factory.createEntityManager();
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<Follows> criteriaQuery = criteriaBuilder.createQuery(Follows.class);
                    Root<Follows> root = criteriaQuery.from(Follows.class);
                    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("followedBook"), id));
                    TypedQuery<Follows> typedQuery = entityManager.createQuery(criteriaQuery);

                    List<Follows> list = typedQuery.getResultList();
                    for (Follows follows : list) {
                        entityManager = factory.createEntityManager();
                        Notification notification = new Notification();
                        notification.setUser(follows.getFollower());
                        notification.setText(text);
                        entityManager.getTransaction().begin();
                        entityManager.persist(notification);
                        entityManager.getTransaction().commit();
                    }

                    for (Iterator<Follows> iterator = list.iterator(); iterator.hasNext();) {
                        Follows c = iterator.next();
                        entityManager = factory.createEntityManager();
                        entityManager.getTransaction().begin();
                        entityManager.remove(entityManager.contains(c) ? c : entityManager.merge(c));
                        entityManager.getTransaction().commit();

                    }

                } else if (notificationType.equals(TopicSender.NotificationType.confirmation.name())) {
                    EntityManager entityManager = factory.createEntityManager();
                    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                    Root<User> root = criteriaQuery.from(User.class);
                    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
                    TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

                    if (!typedQuery.getResultList().isEmpty()) {
                        entityManager = factory.createEntityManager();
                        Notification notification = new Notification();
                        notification.setUser(typedQuery.getSingleResult());
                        notification.setText(text);
                        entityManager.getTransaction().begin();
                        entityManager.persist(notification);
                        entityManager.getTransaction().commit();
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
