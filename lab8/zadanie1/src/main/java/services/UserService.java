package services;

import models.Movie;
import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAll() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    public User getById(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    @Transactional
    public User create(User user) {
        User newUser = new User();
        if (user.getFirstName() != null) {
            newUser.setFirstName(user.getFirstName());
        }
        newUser.setAge(user.getAge());
        if (user.getAvatar() != null) {
            newUser.setAvatar(user.getAvatar());
        }
        if (user.getMovies() != null) {
            newUser.setMovies(user.getMovies());
        }
        entityManager.persist(newUser);
        return newUser;
    }

    @Transactional
    public User addMovie(int idInt, int movie) {
        User u = entityManager.find(User.class, idInt);
        Movie m = entityManager.getReference(Movie.class, movie);
        User u2 = entityManager.merge(u);
        u2.getMovies().add(m);
//        u2.setFirstName((new Date()).toString());
        return u;
    }

    @Transactional
    public User deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            User u = typedQuery.getSingleResult();
            entityManager.remove(u);
            return u;
        } else {
            return null;
        }
    }

    @Transactional
    public User deleteMovie(int idInt, int movie) {
        User u = entityManager.find(User.class, idInt);
        Movie m = entityManager.getReference(Movie.class, movie);
        User u2 = entityManager.merge(u);
        u2.getMovies().remove(m);
        return u;
    }

    @Transactional
    public User update(User user) {
        User u = entityManager.find(User.class, user.getId());
        entityManager.merge(u);
        if (user.getFirstName() != null) {
            u.setFirstName(user.getFirstName());
        }
        if (user.getAge() != 0) {
            u.setAge(user.getAge());
        }
        if (user.getAvatar() != null) {
            u.setAvatar(user.getAvatar());
        }
        return u;
    }


}
