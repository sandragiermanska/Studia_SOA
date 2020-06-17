import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name = "table")
@SessionScoped
public class TableBean {

    private List<Book> bookList;

    private boolean originalCurrency = true;
    private boolean showSummary = false;
    private int numberOfCheckedBooks = 0;
    private int sum = 0;

    private Integer[] categoriesSorting = {-1, -1, -1, -1, -1, -1};
    private boolean[] categoriesSortingIsAscending = {false, false, false, false, false, false};
    int maxValueBySorting = 0;
    private boolean changeSorting = false;

    private boolean showTitle = true;
    private boolean showAuthor = true;
    private boolean showType = true;
    private boolean showPrice = true;
    private boolean showCurrency = true;
    private boolean showPages = true;

    public TableBean() {
        bookList = new ArrayList<>();
        Book book = new Book("Morderstwo w Orient Expresie", "Christie A.",
                "kryminal", 20, "PLN", 250);
        bookList.add(book);
        book = new Book("Krzyzacy", "Sienkiewicz H.",
                "historyczna", 30, "PLN", 600);
        bookList.add(book);
        book = new Book("Sherlock Holmes", "Doyle A. C.",
                "kryminal", 10, "USD", 300);
        bookList.add(book);
        book = new Book("Romeo i Julia", "Szekspir W.",
                "dramat", 8, "EURO", 100);
        bookList.add(book);
        book = new Book("10 malych murzynkow", "Christie A.",
                "kryminal", 9, "USD", 200);
        bookList.add(book);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public String sortByTitle() {
        changeSorting = true;
        if (categoriesSorting[0] == maxValueBySorting) {
            categoriesSortingIsAscending[0] = !categoriesSortingIsAscending[0];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[0] = maxValueBySorting;
            categoriesSortingIsAscending[0] = true;
        }
        return null;
    }

    public String sortByAuthor() {
        changeSorting = true;
        if (categoriesSorting[1] == maxValueBySorting) {
            categoriesSortingIsAscending[1] = !categoriesSortingIsAscending[1];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[1] = maxValueBySorting;
            categoriesSortingIsAscending[1] = true;
        }
        return null;
    }

    public String sortByType() {
        changeSorting = true;
        if (categoriesSorting[2] == maxValueBySorting) {
            categoriesSortingIsAscending[2] = !categoriesSortingIsAscending[2];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[2] = maxValueBySorting;
            categoriesSortingIsAscending[2] = true;
        }
        return null;
    }

    public String sortByPrice() {
        changeSorting = true;
        if (categoriesSorting[3] == maxValueBySorting) {
            categoriesSortingIsAscending[3] = !categoriesSortingIsAscending[3];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[3] = maxValueBySorting;
            categoriesSortingIsAscending[3] = true;
        }
        return null;
    }

    public String sortByCurrency() {
        changeSorting = true;
        if (categoriesSorting[4] == maxValueBySorting) {
            categoriesSortingIsAscending[4] = !categoriesSortingIsAscending[4];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[4] = maxValueBySorting;
            categoriesSortingIsAscending[4] = true;
        }
        return null;
    }

    public String sortByPages() {
        changeSorting = true;
        if (categoriesSorting[5] == maxValueBySorting) {
            categoriesSortingIsAscending[5] = !categoriesSortingIsAscending[5];
        } else {
            maxValueBySorting = maxValueBySorting + 1;
            categoriesSorting[5] = maxValueBySorting;
            categoriesSortingIsAscending[5] = true;
        }
        return null;
    }

    public void sort() {
        for (int i = maxValueBySorting; i > 0; i--) {
            int index = Arrays.asList(categoriesSorting).indexOf(i);
            if (index != -1) {
                switch (index) {
                    case 0:
                        if (categoriesSortingIsAscending[0]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getTitle().compareTo(o2.getTitle());
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getTitle().compareTo(o1.getTitle());
                                }
                            });
                        }
                        break;
                    case 1:
                        if (categoriesSortingIsAscending[1]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getAuthor().compareTo(o2.getAuthor());
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getAuthor().compareTo(o1.getAuthor());
                                }
                            });
                        }
                    case 2:
                        if (categoriesSortingIsAscending[2]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getType().compareTo(o2.getType());
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getType().compareTo(o1.getType());
                                }
                            });
                        }
                        break;
                    case 3:
                        if (categoriesSortingIsAscending[3]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getPrice() - o2.getPrice();
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getPrice() - o1.getPrice();
                                }
                            });
                        }
                        break;
                    case 4:
                        if (categoriesSortingIsAscending[4]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getCurrency().compareTo(o2.getCurrency());
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getCurrency().compareTo(o1.getCurrency());
                                }
                            });
                        }
                        break;
                    case 5:
                        if (categoriesSortingIsAscending[5]) {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o1.getNumberOfPages() - o2.getNumberOfPages();
                                }
                            });
                        } else {
                            bookList.sort(new Comparator<Book>() {
                                @Override
                                public int compare(Book o1, Book o2) {
                                    return o2.getNumberOfPages() - o1.getNumberOfPages();
                                }
                            });
                        }
                        break;
                }
                categoriesSorting[index] = -1;
                categoriesSortingIsAscending[index] = false;
            }
        }
        maxValueBySorting = 0;
        changeSorting = false;
    }

    //<gettery i settery>


    public boolean[] getCategoriesSortingIsAscending() {
        return categoriesSortingIsAscending;
    }

    public void setCategoriesSortingIsAscending(boolean[] categoriesSortingIsAscending) {
        this.categoriesSortingIsAscending = categoriesSortingIsAscending;
    }

    public Integer[] getCategoriesSorting() {
        return categoriesSorting;
    }

    public void setCategoriesSorting(Integer[] categoriesSorting) {
        this.categoriesSorting = categoriesSorting;
    }

    public boolean isChangeSorting() {
        return changeSorting;
    }

    public void setChangeSorting(boolean changeSorting) {
        this.changeSorting = changeSorting;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowAuthor() {
        return showAuthor;
    }

    public void setShowAuthor(boolean showAuthor) {
        this.showAuthor = showAuthor;
    }

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public boolean isShowPrice() {
        return showPrice;
    }

    public void setShowPrice(boolean showPrice) {
        this.showPrice = showPrice;
    }

    public boolean isShowCurrency() {
        return showCurrency;
    }

    public void setShowCurrency(boolean showCurrency) {
        this.showCurrency = showCurrency;
    }

    public boolean isShowPages() {
        return showPages;
    }

    public void setShowPages(boolean showPages) {
        this.showPages = showPages;
    }

    public boolean isShowSummary() {
        return showSummary;
    }

    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
    }

    public int getNumberOfCheckedBooks() {
        return numberOfCheckedBooks;
    }

    public void setNumberOfCheckedBooks(int numberOfCheckedBooks) {
        this.numberOfCheckedBooks = numberOfCheckedBooks;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    //</gettery i settery>

    public void changeShowTitle() {
        this.showTitle = !this.showTitle;
    }

    public void changeShowAuthor() {
        this.showAuthor = !this.showAuthor;
    }

    public void changeShowType() {
        this.showType = !this.showType;
    }

    public void changeShowPrice() {
        this.showPrice = !this.showPrice;
    }

    public void changeShowCurrency() {
        this.showCurrency = !this.showCurrency;
    }

    public void changeShowPages() {
        this.showPages = !this.showPages;
    }

    public void changeCurrency() {
        if (originalCurrency) {
            for (Book b : bookList) {
                b.changeToPLN();
            }
            originalCurrency = false;
        } else {
            for (Book b : bookList) {
                b.changeToOriginalCurrency();
            }
            originalCurrency = true;
        }
    }

    public void summary() {
        summaryCancel();
        this.showSummary = true;
        for (Book b : this.bookList) {
            if (b.isChecked()) {
                this.numberOfCheckedBooks = this.numberOfCheckedBooks + 1;
                this.sum = this.sum + b.getPLNPrice();
            }
        }
    }

    public void summaryCancel() {
        this.showSummary = false;
        this.numberOfCheckedBooks = 0;
        this.sum = 0;
    }
}
