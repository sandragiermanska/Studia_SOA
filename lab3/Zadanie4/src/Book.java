public class Book implements Cloneable {

    private String title;
    private String author;
    private String type;
    private int price;
    private String currency;
    private int originalPrice;
    private String originalCurrency;
    private int numberOfPages;
    private boolean checked;


    public Book(String title, String author, String type, int price, String currency, int numberOfPages) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.price = price;
        this.currency = currency;
        this.originalPrice = price;
        this.originalCurrency = currency;
        this.numberOfPages = numberOfPages;
        this.checked = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPLNPrice() {
        if (originalCurrency.equals("PLN")) {
            return originalPrice;
        }
        switch (this.originalCurrency) {
            case "USD":
            case "EURO":
                return this.originalPrice * 4;
        }
        return price;
    }

    public void changeToPLN() {
        this.price = getPLNPrice();
        this.currency = "PLN";
    }

    public void changeToOriginalCurrency() {
        this.currency = this.originalCurrency;
        this.price = this.originalPrice;
    }

}
