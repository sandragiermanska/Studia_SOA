package services;

import models.Movie;

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
public class MovieService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Movie> getAll() {
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m", Movie.class);
        return query.getResultList();
    }

    public Movie getById(int id) {
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m where m.id = :id", Movie.class);
        query.setParameter("id", id);
        List<Movie> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }


    public List<Movie> getByTitle(String title) {
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m where m.title = :title", Movie.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Transactional
    public Movie create(Movie movie) {
        Movie newMovie = new Movie();
        if (movie.getTitle() != null) {
            newMovie.setTitle(movie.getTitle());
        }
        if (movie.getUri() != null) {
            newMovie.setUri(movie.getUri());
        }
        if (movie.getUsers() != null) {
            newMovie.setUsers(movie.getUsers());
        }
        entityManager.persist(newMovie);
        return newMovie;
    }

    public Movie delete(Movie movie) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), movie.getId()));
        TypedQuery<Movie> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Movie m = typedQuery.getSingleResult();
            entityManager.getTransaction().begin();
            entityManager.remove(m);
            entityManager.getTransaction().commit();
            return m;
        } else {
            return null;
        }
    }

    @Transactional
    public Movie update(Movie movie) {
        Movie m = entityManager.find(Movie.class, movie.getId());
        entityManager.merge(m);
        if (movie.getTitle() != null) {
            m.setTitle(movie.getTitle());
        }
        if (movie.getUri() != null) {
            m.setUri(movie.getUri());
        }
        return m;
    }



    @Transactional
    public Movie deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Movie> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Movie m = typedQuery.getSingleResult();
            entityManager.remove(m);
            return m;
        } else {
            return null;
        }
    }


}
