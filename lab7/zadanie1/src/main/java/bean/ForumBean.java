package bean;

import db.*;
import topic.TopicSender;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@ManagedBean(name = "forumBean")
@SessionScoped
public class ForumBean {

    // form -> index (sign in)
    private String login;

    // form -> create thematic list
    private String createThematicListsName;

    // form -> show thematic list
    private ThematicList showThematicLists;
    private String comment;
    private String commentToLogin;
    private int commentToID;

    // data
    private AppBean appBean = AppBean.getInstance();
    private EntityManagerFactory factory = appBean.getFactory();
    private User user;
    private List<ThematicList> thematicLists;

    public String signIn() {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("login"), this.login));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        if (typedQuery.getResultList().isEmpty()) {
            // add new user
            User user = new User();
            user.setLogin(this.login);
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                this.user = user;
            } catch (Exception e) {
                return "index";
            }
        } else {
            this.user = typedQuery.getSingleResult();
        }
        return "mainSite";
    }

    public String createThematicListSite() {
        return "createThematicList";
    }

    public String createThematicList() {
        ThematicList thematicList = new ThematicList();
        thematicList.setName(this.createThematicListsName);
        thematicList.setOwner(this.user);
        try {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(thematicList);
            entityManager.getTransaction().commit();
            this.showThematicLists = thematicList;
            return "thematicList";
        } catch (Exception e) {
            return "mainSite";
        }
    }

    public void sendToAll() {
        TopicSender topicSender = new TopicSender();
        topicSender.sendMessage(this.showThematicLists.getName(), "Dodano komentarz: " + this.comment, "");

        Comment comment = new Comment();
        comment.setMessage(this.comment);
        comment.setAuthor(this.user);
        comment.setThematicList(this.showThematicLists);
        comment.setToUser(null);
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void sendToOne() {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("login"), this.commentToLogin));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);

        if (!typedQuery.getResultList().isEmpty()) {
            TopicSender topicSender = new TopicSender();
            topicSender.sendMessage(this.showThematicLists.getName(), "Dodano komentarz dla ciebie: " + this.comment, typedQuery.getSingleResult().getLogin());

            Comment comment = new Comment();
            comment.setMessage(this.comment);
            comment.setAuthor(this.user);
            comment.setThematicList(this.showThematicLists);
            comment.setToUser(typedQuery.getSingleResult());
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(comment);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                entityManager.getTransaction().rollback();
            }
        }
    }

    public String getThematicListName() {
        return this.showThematicLists.getName();
    }

    public List<Comment> getThematicListComments() {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> subscribesRoot = criteriaQuery.from(Comment.class);
        criteriaQuery.select(subscribesRoot).where(criteriaBuilder.equal(subscribesRoot.get("thematicList"), showThematicLists.getId()));
        TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Comment> result = typedQuery.getResultList();

        if (result != null) {
            for (Iterator<Comment> iterator = result.iterator();iterator.hasNext();) {
                Comment c = iterator.next();
                if (c.getToUser() != null && c.getToUser().getId() != user.getId()) {
                    iterator.remove();
                }
            }
        }
        return result;
    }

    public void subscribe(ThematicList thematicList) {
        EntityManager entityManager = factory.createEntityManager();
        Subscribes subscribes = new Subscribes();
        subscribes.setSubscribedThematicList(thematicList);
        subscribes.setSubscriber(this.user);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(subscribes);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void unsubscribe(ThematicList thematicList) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Subscribes> criteriaQuery = criteriaBuilder.createQuery(Subscribes.class);
            Root<Subscribes> subscribesRoot = criteriaQuery.from(Subscribes.class);
            criteriaQuery.select(subscribesRoot).where(
                    criteriaBuilder.equal(subscribesRoot.get("subscribedThematicList"), thematicList.getId()),
                    criteriaBuilder.equal(subscribesRoot.get("subscriber"), user.getId()));
            TypedQuery<Subscribes> typedQuery = entityManager.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                Subscribes subscribes = typedQuery.getSingleResult();
                entityManager.getTransaction().begin();
                entityManager.remove(subscribes);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            System.err.println("Error by delete: " + e);
            System.err.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    public boolean isSubscribed(ThematicList thematicList) {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscribes> criteriaQuery = criteriaBuilder.createQuery(Subscribes.class);
        Root<Subscribes> subscribesRoot = criteriaQuery.from(Subscribes.class);
        criteriaQuery.select(subscribesRoot).where(criteriaBuilder.equal(subscribesRoot.get("subscribedThematicList"), thematicList.getId()));
        TypedQuery<Subscribes> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Subscribes> list = typedQuery.getResultList();

        for (Subscribes s : list) {
            if (s.getSubscriber().getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }

    public String open(ThematicList thematicList) {
        this.showThematicLists = thematicList;
        return "thematicList";
    }

    public List<Notification> getNotificationsList() {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> root = criteriaQuery.from(Notification.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user.getId()));
        TypedQuery<Notification> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public void deleteNotification(Notification notification) {
        EntityManager em = factory.createEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
            Root<Notification> root = criteriaQuery.from(Notification.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), notification.getId()));
            TypedQuery<Notification> typedQuery = em.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                Notification n = typedQuery.getSingleResult();
                em.getTransaction().begin();
                em.remove(n);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }


    // getters and setters

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<ThematicList> getThematicLists() {
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ThematicList> criteriaQuery = criteriaBuilder.createQuery(ThematicList.class);
        Root<ThematicList> thematicListRoot = criteriaQuery.from(ThematicList.class);
        criteriaQuery.select(thematicListRoot);
        TypedQuery<ThematicList> typedQuery = entityManager.createQuery(criteriaQuery);
        thematicLists = typedQuery.getResultList();
        return thematicLists;
    }

    public void setThematicLists(List<ThematicList> thematicLists) {
        this.thematicLists = thematicLists;
    }

    public String getCreateThematicListsName() {
        return createThematicListsName;
    }

    public void setCreateThematicListsName(String createThematicListsName) {
        this.createThematicListsName = createThematicListsName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentToID() {
        return commentToID;
    }

    public void setCommentToID(int commentToID) {
        this.commentToID = commentToID;
    }

    public ThematicList getShowThematicLists() {
        return showThematicLists;
    }

    public void setShowThematicLists(ThematicList showThematicLists) {
        this.showThematicLists = showThematicLists;
    }

    public String getCommentToLogin() {
        return commentToLogin;
    }

    public void setCommentToLogin(String commentToLogin) {
        this.commentToLogin = commentToLogin;
    }

}
