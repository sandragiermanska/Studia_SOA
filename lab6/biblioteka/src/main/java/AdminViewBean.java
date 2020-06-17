import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ManagedBean(name = "adminBean")
@RequestScoped
public class AdminViewBean {

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

    private int bookCatalogId;
    private int bookCatalogBookId;
    private boolean bookCatalogBorrowed;


    public List<Reader> getReaderList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM Reader", Reader.class);
            List<Reader> readers = q.getResultList();
            return readers;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Author> getAuthorList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM Author", Author.class);
            List<Author> authors = q.getResultList();
            return authors;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getCategoryList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM Category", Category.class);
            List<Category> categories = q.getResultList();
            return categories;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Book> getBookList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM Book", Book.class);
            List<Book> books = q.getResultList();
            return books;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<BookCatalog> getBookCatalogList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM BookCategory", BookCatalog.class);
            List<BookCatalog> bookCatalogs = q.getResultList();
            return bookCatalogs;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    // ADD

    public void addReader() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Reader reader = new Reader();
            reader.setFirstName(readerFirstName);
            reader.setLastName(readerLastName);

            em.getTransaction().begin();
            em.persist(reader);
            em.getTransaction().commit();

            System.out.println("Zapisano w bazie: " + reader);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addAuthor() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Author author = new Author();
            author.setFirstName(authorFirstName);
            author.setLastName(authorLastName);

            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();

            System.out.println("Zapisano w bazie: " + author);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addCategory() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Category category = new Category();
            category.setName(categoryName);

            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();

            System.out.println("Zapisano w bazie: " + category);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addBook() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
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

            System.out.println("Zapisano w bazie: " + b);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void addBookCatalogEntity() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            BookCatalog bookCatalog = new BookCatalog();
            bookCatalog.setBookI(em.getReference(Book.class, bookCatalogId));
            bookCatalog.setBorrowed(bookCatalogBorrowed);

            em.getTransaction().begin();
            em.persist(bookCatalog);
            em.getTransaction().commit();

            System.out.println("Zapisano w bazie: " + bookCatalog);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    //DELETE

    @Transactional
    public void deleteReader() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM Reader r where r.id = :id");
            q.setParameter("id", readerId);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteAuthor() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM Author a where a.id = :id");
            q.setParameter("id", authorId);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteCategory() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM Category ca where ca.id = :id");
            q.setParameter("id", categoryId);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteBook() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM Book b where b.id = :id");
            q.setParameter("id", bookId);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteBookCatalogEntity() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM BookCatalog b where b.id = :id");
            q.setParameter("id", bookCatalogId);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
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

    public int getBookCatalogId() {
        return bookCatalogId;
    }

    public void setBookCatalogId(int bookCatalogId) {
        this.bookCatalogId = bookCatalogId;
    }

    public int getBookCatalogBookId() {
        return bookCatalogBookId;
    }

    public void setBookCatalogBookId(int bookCatalogBookId) {
        this.bookCatalogBookId = bookCatalogBookId;
    }

    public boolean isBookCatalogBorrowed() {
        return bookCatalogBorrowed;
    }

    public void setBookCatalogBorrowed(boolean bookCatalogBorrowed) {
        this.bookCatalogBorrowed = bookCatalogBorrowed;
    }


    //</gettery i settery>

}
