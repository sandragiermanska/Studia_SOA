import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ManagedBean(name = "table")
@SessionScoped
public class TableBean {

    private int id;
    private String title;
    private String firstName;
    private String lastName;
    private String isbn;
    private int year;
    private int price;


    public List<Book> getBookList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
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

    @Transactional
    public void delete() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("DELETE FROM Book b where b.id = :id");
            q.setParameter("id", id);

            em.joinTransaction();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void update() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            if (!title.isEmpty()) {
                Query q = em.createNativeQuery("UPDATE Book SET title = :title " +
                        "where id = :id");
                q.setParameter("title", title);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            if (!firstName.isEmpty()) {
                Query q = em.createNativeQuery("UPDATE Book SET firstname = :firstName " +
                        "where id = :id");
                q.setParameter("firstName", firstName);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            if (!lastName.isEmpty()) {
                Query q = em.createNativeQuery("UPDATE Book SET lastname = :lastName " +
                        "where id = :id");
                q.setParameter("lastName", lastName);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            if (year != 0) {
                Query q = em.createNativeQuery("UPDATE Book SET year = :year " +
                        "where id = :id");
                q.setParameter("year", year);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            if (price != 0) {
                Query q = em.createNativeQuery("UPDATE Book SET price = :price " +
                        "where id = :id");
                q.setParameter("price", price);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            if (!isbn.isEmpty()) {
                Query q = em.createNativeQuery("UPDATE Book SET isbn = :isbn " +
                        "where id = :id");
                q.setParameter("isbn", isbn);
                q.setParameter("id", id);
                em.joinTransaction();
                q.executeUpdate();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy modyfikacji rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void add() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
        EntityManager em = factory.createEntityManager();

        try {
            Book b = new Book(title, firstName, lastName, isbn, year, price);

            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();

            System.out.println("Zapisano w bazie: " + b);
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    //<gettery i settery>


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    //</gettery i settery>

}
