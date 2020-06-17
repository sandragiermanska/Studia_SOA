import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "borrowBean")
@SessionScoped
public class BorrowViewBean {

    private int borrowBookId;
    private int borrowReaderId;
    private boolean borrowAccepted = true;

    public List<BookCatalog> getBookList() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        try {
            Query q = em.createQuery("FROM BookCatalog", BookCatalog.class);
            List<BookCatalog> books = q.getResultList();
            return books;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordu: " + e);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void search() {

    }

    public void borrowBook() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (borrowReaderId != 0 && borrowBookId != 0) {

                Query q = em.createNativeQuery("UPDATE Book SET isBorrowed = true " +
                        "where id = :id and isBorrowed = false");
                q.setParameter("id", borrowBookId);
                em.joinTransaction();
                int res = q.executeUpdate();
                if (res == 0) {
                    borrowAccepted = false;
                } else {
                    borrowAccepted = true;
                }
                Borrow b = new Borrow();
                b.setBookCatalogEntity(em.getReference(BookCatalog.class, borrowBookId));
                b.setBorrowDate(new Date());
                b.setReader(em.getReference(Reader.class, borrowReaderId));
                em.persist(b);
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            System.err.println("Blad przy modyfikacji rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    public void returnBook() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-books");
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (borrowReaderId != 0 && borrowBookId != 0) {

                Query q = em.createNativeQuery("UPDATE Book SET isBorrowed = false " +
                        "where id = :id and isBorrowed = true");
                q.setParameter("id", borrowBookId);
                em.joinTransaction();
                int res = q.executeUpdate();
                if (res == 0) {
                    borrowAccepted = false;
                } else {
                    Date d = new Date();
                    q = em.createNativeQuery("UPDATE Borrow SET returnDate = :d " +
                            "where bookId = :bid and readerId = :rid and returnDate = null");
                    q.setParameter("d", d);
                    q.setParameter("bid", borrowBookId);
                    q.setParameter("rid", borrowReaderId);
                    em.joinTransaction();
                }
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            System.err.println("Blad przy modyfikacji rekordu: " + e);
            System.err.println(e.getMessage());
        }
    }

    // GETTERY I SETTERY


    public int getBorrowBookId() {
        return borrowBookId;
    }

    public void setBorrowBookId(int borrowBookId) {
        this.borrowBookId = borrowBookId;
    }

    public boolean isBorrowAccepted() {
        return borrowAccepted;
    }

    public void setBorrowAccepted(boolean borrowAccepted) {
        this.borrowAccepted = borrowAccepted;
    }

    public int getBorrowReaderId() {
        return borrowReaderId;
    }

    public void setBorrowReaderId(int borrowReaderId) {
        this.borrowReaderId = borrowReaderId;
    }
}
