import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book implements Cloneable {

    private int id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String ISBNnumber;
    private int year;
    private int price;

    public Book() {
        super();
    }

    public Book(String title, String authorFirstName, String authorLastName, String ISBNnumber, int year, int price) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.ISBNnumber = ISBNnumber;
        this.year = year;
        this.price = price;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "firstname", nullable = true)
    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    @Column(name = "lastname", nullable = false)
    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    @Column(name = "price", nullable = true)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column(name = "ISBN", nullable = true)
    public String getISBNnumber() {
        return ISBNnumber;
    }

    public void setISBNnumber(String ISBNnumber) {
        this.ISBNnumber = ISBNnumber;
    }

    public int getYear() {
        return year;
    }

    @Column(name = "year", nullable = true)
    public void setYear(int year) {
        this.year = year;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
