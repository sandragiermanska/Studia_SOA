import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bookCatalog")
public class BookCatalog {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book bookI;

    @Column(name = "isBorrowed", nullable = false)
    private boolean isBorrowed;

    @OneToMany(mappedBy = "bookCatalogEntity")
    private List<Borrow> borrows;

    public BookCatalog() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Book getBookI() {
        return bookI;
    }

    public void setBookI(Book bookI) {
        this.bookI = bookI;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
