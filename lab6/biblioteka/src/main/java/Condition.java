public class Condition {
    private ClassDB classDB;
    private String propertyDB;
    private Operator operator;
    private String value;

    public enum Operator {
        LESS, GREATHER, EQUAL, LIKE, MIN, MAX
    }

    public enum ClassDB {
        READER, AUTHOR, CATEGORY, BOOK, BORROW, BOOKCATALOG
    }

    public ClassDB getClassDB() {
        return classDB;
    }

    public void setClassDB(ClassDB classDB) {
        this.classDB = classDB;
    }

    public String getPropertyDB() {
        return propertyDB;
    }

    public void setPropertyDB(String propertyDB) {
        this.propertyDB = propertyDB;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
