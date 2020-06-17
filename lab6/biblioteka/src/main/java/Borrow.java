import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookCatalog bookCatalogEntity;

    @ManyToOne
    @JoinColumn(name = "readerId")
    private Reader reader;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowDate")
    private Date borrowDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "returnDate")
    private Date returnDate;

    public Borrow() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookCatalog getBookCatalogEntity() {
        return bookCatalogEntity;
    }

    public void setBookCatalogEntity(BookCatalog bookCatalogEntity) {
        this.bookCatalogEntity = bookCatalogEntity;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
