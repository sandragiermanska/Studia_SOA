package bean;

import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class AppBean  {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

    private final static AppBean instance = new AppBean();

    public static AppBean getInstance() {
        return instance;
    }

    private AppBean() {

    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
}
