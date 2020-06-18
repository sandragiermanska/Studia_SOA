package bean;

import db.*;
import topic.TopicSender;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {

    private AppBean appBean = AppBean.getInstance();

    private String userFirstName;
    private String userLastName;
    private User user;
    private boolean userWantNewBookNotifications;

    private int borrowBookId;


    public String signIn() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(
                criteriaBuilder.equal(userRoot.get("firstName"), this.userFirstName),
                criteriaBuilder.equal(userRoot.get("lastName"), this.userLastName));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        if (typedQuery.getResultList().isEmpty()) {
            // add new user
            User user = new User();
            user.setFirstName(this.userFirstName);
            user.setLastName(this.userLastName);
            user.setWantNewBookNotifications(this.userWantNewBookNotifications);
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                this.user = user;
            } catch (Exception e) {
                return "signIn";
            }
        } else {
            this.user = typedQuery.getSingleResult();
        }
        return "user";
    }

    public String signInAsAdmin() {
        this.userFirstName = "admin";
        this.userLastName = "admin";
        this.userWantNewBookNotifications = false;
        String res = signIn();
        if (res.equals("user")) return "admin";
        else return res;
    }


    public List<Notification> getNotificationsList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> root = criteriaQuery.from(Notification.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user.getId()));
        TypedQuery<Notification> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public void deleteNotification(Notification notification) {
        EntityManagerFactory factory = appBean.getFactory();
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

    public List<Book> getBookList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);
        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Book> getMyBookList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Borrow> criteriaQuery = criteriaBuilder.createQuery(Borrow.class);
        Root<Borrow> root = criteriaQuery.from(Borrow.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), this.user));
        TypedQuery<Borrow> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            ArrayList<Book> result = new ArrayList<Book>();
            for (Borrow b : typedQuery.getResultList()) {
                if (b.getReturnDate() == null) {
                    entityManager = factory.createEntityManager();
                    criteriaBuilder = entityManager.getCriteriaBuilder();
                    CriteriaQuery<Book> criteriaQuery1 = criteriaBuilder.createQuery(Book.class);
                    Root<Book> root1 = criteriaQuery1.from(Book.class);
                    criteriaQuery1.select(root1).where(criteriaBuilder.equal(root1.get("id"), b.getBook().getId()));
                    TypedQuery<Book> typedQuery1 = entityManager.createQuery(criteriaQuery1);
                    if (!typedQuery1.getResultList().isEmpty()) {
                        result.add(typedQuery1.getSingleResult());
                    }
                }
            }
            return result;
        }
        return new ArrayList<Book>();
    }


    @Transactional
    public void borrowBook() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (borrowBookId != 0) {

                Query q = em.createNativeQuery("UPDATE Book_entity SET isBorrowed = true " +
                        "where id = :id and isBorrowed = false");
                q.setParameter("id", borrowBookId);
                em.joinTransaction();
                int res = q.executeUpdate();
                if (res != 0) {
                    Borrow b = new Borrow();
                    b.setBook(em.getReference(Book.class, borrowBookId));
                    b.setBorrowDate(new Date());
                    b.setUser(em.getReference(User.class, user.getId()));
                    em.persist(b);
                    em.getTransaction().commit();

                    TopicSender topicSender = new TopicSender();
                    topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Wypozyczono ksiazke o id "
                            + borrowBookId, user.getId());
                }
            }

        } catch (Exception e) {
            System.err.println("Blad przy modyfikacji rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void returnBook() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();

        try {
            if (borrowBookId != 0) {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Borrow> criteriaQuery = criteriaBuilder.createQuery(Borrow.class);
                Root<Borrow> root = criteriaQuery.from(Borrow.class);
                criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), this.user),
                        criteriaBuilder.isNull(root.get("returnDate")));
                TypedQuery<Borrow> typedQuery = em.createQuery(criteriaQuery);
                if (!typedQuery.getResultList().isEmpty()) {
                    Borrow borrowTemp = null;
                    for (Borrow b : typedQuery.getResultList()) {
                        if (b.getBook().getId() == this.borrowBookId) {
                            borrowTemp = b;
                            break;
                        }
                    }
                    if (borrowTemp != null) {
                        em.getTransaction().begin();
                        Borrow borrow = em.find(Borrow.class, borrowTemp.getId());
                        em.merge(borrow);
                        borrow.setReturnDate(new Date());
                        em.getTransaction().commit();

                        em.getTransaction().begin();
                        Query q = em.createNativeQuery("UPDATE Book_entity SET isBorrowed = false " +
                                "where id = :id");
                        q.setParameter("id", borrowBookId);
                        em.joinTransaction();
                        q.executeUpdate();

                        em.getTransaction().commit();

                        TopicSender topicSender = new TopicSender();
                        topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Oddano ksiazke o id "
                                + borrowBookId, user.getId());
                        topicSender.sendMessage(TopicSender.NotificationType.returnBook, "Oddano ksiazke ktora " +
                                "chcesz wypozyczyc ( id=" + borrowBookId + ")", borrowBookId);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Blad przy modyfikacji rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void followBook() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), borrowBookId));
        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Book book = typedQuery.getSingleResult();
            entityManager = factory.createEntityManager();
            Follows follows = new Follows();
            follows.setFollower(this.user);
            follows.setFollowedBook(book);
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(follows);
                entityManager.getTransaction().commit();

                TopicSender topicSender = new TopicSender();
                topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Obserwujesz ksiazke "
                        + book.getTitle(), user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // GETTERY I SETTERY


    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public boolean isUserWantNewBookNotifications() {
        return userWantNewBookNotifications;
    }

    public void setUserWantNewBookNotifications(boolean userWantNewBookNotifications) {
        this.userWantNewBookNotifications = userWantNewBookNotifications;
    }

    public int getBorrowBookId() {
        return borrowBookId;
    }

    public void setBorrowBookId(int borrowBookId) {
        this.borrowBookId = borrowBookId;
    }

}

