package db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "borrow_entity")
public class Borrow {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "readerId")
    private User user;

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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
