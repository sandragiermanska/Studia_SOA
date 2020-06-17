import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "searchBean")
@ViewScoped
public class SearchBean {

    private int classSearched;
    private List<Condition> conditions = new ArrayList<>();
    private List<List<String>> properties = new ArrayList<>();
    private List<List<Condition.Operator>> operators = new ArrayList<>();


    public void addCondition() {
        conditions.add(new Condition());
        properties.add(new ArrayList<String>());
        operators.add(new ArrayList<Condition.Operator>());
    }

    public void setPropertyList(int index) {
        ArrayList<String> prop = new ArrayList<>();
        switch (conditions.get(index).getClassDB()) {
            case AUTHOR:
                prop.add("id");
                prop.add("firstName");
                prop.add("lastName");
                break;
            case READER:
                prop.add("id");
                prop.add("firstName");
                prop.add("lastName");
                break;
            case CATEGORY:
                prop.add("id");
                prop.add("name");
                break;
            case BOOK:
                prop.add("numberOf");
                prop.add("title");
                prop.add("isbn");
                break;
            case BORROW:
                prop.add("numberOf");
                prop.add("borrowDate");
                prop.add("returnDate");
            case BOOKCATALOG:
                prop.add("isBorrowed");
                break;
        }
        properties.set(index, prop);
    }

    public void setOperatorList(int index) {
        ArrayList<Condition.Operator> oper = new ArrayList<>();
        switch (conditions.get(index).getPropertyDB()) {
            case "firstName":
            case "lastName":
            case "name":
            case "title":
            case "isbn":
                oper.add(Condition.Operator.LIKE);
            case "numberOf":
                oper.add(Condition.Operator.MIN);
                oper.add(Condition.Operator.MAX);
            case "id":
            case "borrowDate":
            case "returnDate":
                oper.add(Condition.Operator.LESS);
                oper.add(Condition.Operator.GREATHER);
            case "isBorrowed":
                oper.add(Condition.Operator.EQUAL);
                break;
        }
        operators.set(index, oper);
    }


    public List<BookCatalog> getList() {
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        switch (classSearched) {
            case 1: //AUTHOR
                CriteriaQuery<Author> qA = cb.createQuery(Author.class);
                Root<Author> a = qA.from(Author.class);
                qA.select(a);
                for (Condition c : conditions) {
                    if (c.getClassDB() == Condition.ClassDB.AUTHOR) {
                        if (c.getPropertyDB().equals("id")) {
                            ParameterExpression<Integer> p = cb.parameter(Integer.class);
                            switch (c.getOperator()) {
                                case LESS:
                                    qA.where(cb.lt(a.<Number>get(c.getPropertyDB()),p));
                                case EQUAL:
                                    qA.where(cb.equal(a.<Number>get(c.getPropertyDB()),p));
                                case GREATHER:
                                    qA.where(cb.gt(a.<Number>get(c.getPropertyDB()),p));
                            }

                        } else {

                        }
                    }
                }
                TypedQuery<Author> queryA = em.createQuery(qA);
                List<Author> authors = queryA.getResultList();

            case 2: //BOOK
                CriteriaQuery<Book> queryB = cb.createQuery(Book.class);
            case 3: //READER
                CriteriaQuery<Reader> queryR = cb.createQuery(Reader.class);
            case 4: //CATEGORY
                CriteriaQuery<Category> queryC = cb.createQuery(Category.class);
        }



    }





    // GETTERY I SETTERY

    public int getClassSearched() {
        return classSearched;
    }

    public void setClassSearched(int classSearched) {
        this.classSearched = classSearched;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<List<String>> getProperties() {
        return properties;
    }

    public void setProperties(List<List<String>> properties) {
        this.properties = properties;
    }

    public List<List<Condition.Operator>> getOperators() {
        return operators;
    }

    public void setOperators(List<List<Condition.Operator>> operators) {
        this.operators = operators;
    }

}
