package bean;

import db.*;
import topic.TopicSender;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminViewBean {

    private AppBean appBean = AppBean.getInstance();
    private int adminId;

    private int readerId;
    private String readerFirstName;
    private String readerLastName;

    private int authorId;
    private String authorFirstName;
    private String authorLastName;

    private int categoryId;
    private String categoryName;

    private int bookId;
    private String bookTitle;
    private int bookAuthorId;
    private String bookIsbn;
    private int bookCategoryId;

    public AdminViewBean () {
        try {
            EntityManagerFactory factory = appBean.getFactory();
            EntityManager entityManager = factory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot).where(
                    criteriaBuilder.equal(userRoot.get("firstName"), "admin"),
                    criteriaBuilder.equal(userRoot.get("lastName"), "admin"));
            TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                this.adminId = typedQuery.getSingleResult().getId();
            }
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }


    public List<User> getReaderList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Author> getAuthorList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);
        TypedQuery<Author> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Category> getCategoryList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.select(root);
        TypedQuery<Category> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
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

    public List<Notification> getConfirmationsList() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> root = criteriaQuery.from(Notification.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), adminId));
        TypedQuery<Notification> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    // ADD

//    public void addReader() {
//        EntityManagerFactory factory = appBean.getFactory();
//        EntityManager em = factory.createEntityManager();
//        try {
//            User user = new User();
//            user.setFirstName(readerFirstName);
//            user.setLastName(readerLastName);
//
//            em.getTransaction().begin();
//            em.persist(user);
//            em.getTransaction().commit();
//
//            TopicSender topicSender = new TopicSender();
//            topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Dodano uzytkownika "
//                    + readerFirstName + " " + readerLastName, adminId);
//        } catch (Exception e) {
//            System.err.println("Blad przy dodawaniu rekordu: " + e);
//            System.err.println(e.getMessage());
//        }
//    }

    public void addAuthor() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            Author author = new Author();
            author.setFirstName(authorFirstName);
            author.setLastName(authorLastName);

            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();

            TopicSender topicSender = new TopicSender();
            topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Dodano autora "
                    + authorFirstName + " " + authorLastName, adminId);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addCategory() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            Category category = new Category();
            category.setName(categoryName);

            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();

            TopicSender topicSender = new TopicSender();
            topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Dodano kategorie " + categoryName, adminId);

        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addBook() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            Book b = new Book();
            b.setAuthor(em.getReference(Author.class, bookAuthorId));
            b.setCategory(em.getReference(Category.class, bookCategoryId));
            b.setIsbn(bookIsbn);
            b.setTitle(bookTitle);

            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();

            TopicSender topicSender = new TopicSender();
            topicSender.sendMessage(TopicSender.NotificationType.newBook, "Dodano ksiazke " + bookTitle, 0);
            topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Dodano ksiazke " + bookTitle, adminId);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }


    //DELETE

    public void deleteUser() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), readerId));
            TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                User user = typedQuery.getSingleResult();
                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();

                TopicSender topicSender = new TopicSender();
                topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Usunieto uzytkownika " +
                        user.getFirstName() + " " + user.getLastName(), adminId);
            }
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void deleteAuthor() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
            Root<Author> root = criteriaQuery.from(Author.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), authorId));
            TypedQuery<Author> typedQuery = em.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                Author author = typedQuery.getSingleResult();
                em.getTransaction().begin();
                em.remove(author);
                em.getTransaction().commit();

                TopicSender topicSender = new TopicSender();
                topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Usunieto autora "
                        + author.getFirstName() + " " + author.getLastName(), adminId);
            }
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void deleteCategory() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), categoryId));
            TypedQuery<Category> typedQuery = em.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                Category category = typedQuery.getSingleResult();
                em.getTransaction().begin();
                em.remove(category);
                em.getTransaction().commit();

                TopicSender topicSender = new TopicSender();
                topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Usunieto kategorie " + category.getName(), adminId);
            }
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void deleteBook() {
        EntityManagerFactory factory = appBean.getFactory();
        EntityManager em = factory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), bookId));
            TypedQuery<Book> typedQuery = em.createQuery(criteriaQuery);
            if (!typedQuery.getResultList().isEmpty()) {
                Book book = typedQuery.getSingleResult();
                em.getTransaction().begin();
                em.remove(book);
                em.getTransaction().commit();

                TopicSender topicSender = new TopicSender();
                topicSender.sendMessage(TopicSender.NotificationType.confirmation, "Usunieto ksiazke " + book.getTitle(), adminId);
            }
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void deleteConfirmation(Notification notification) {
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


    //<gettery i settery>

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getReaderFirstName() {
        return readerFirstName;
    }

    public void setReaderFirstName(String readerFirstName) {
        this.readerFirstName = readerFirstName;
    }

    public String getReaderLastName() {
        return readerLastName;
    }

    public void setReaderLastName(String readerLastName) {
        this.readerLastName = readerLastName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(int bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(int bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    //</gettery i settery>

}
