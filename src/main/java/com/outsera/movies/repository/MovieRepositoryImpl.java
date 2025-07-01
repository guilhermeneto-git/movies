package com.outsera.movies.repository;

import com.outsera.movies.entity.Movie;
import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.Studio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MovieRepositoryImpl implements MovieRepository{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Override
    @Transactional
    public Movie create(Movie movie) {
        adjustStudios(movie);
        adjustProducers(movie);

        return entityManager.merge(movie);
    }

    @Override
    @Transactional
    public Movie update(Movie movie) {
        Movie movieFound = entityManager.find(Movie.class, movie.getId());

        if (movieFound != null) {
            movieFound.setYear(movie.getYear());
            movieFound.setTitle(movie.getTitle());
            movieFound.setWinner(movie.getWinner());
            movie = movieFound;
        }

        adjustStudios(movie);
        adjustProducers(movie);

        return entityManager.merge(movie);
    }

    @Override
    public Movie findById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    private void adjustStudios(Movie movie) {
        Set<Studio> studios = movie.getStudios() != null ?movie.getStudios() :new HashSet<>();
        Set<Studio> studiosList = new HashSet<>(studios.size());

        for (Studio studio: studios) {
            Studio studioPersisted = studioRepository.findByName(studio.getName());
            studiosList.add(studioPersisted != null? studioPersisted :studio);
        }
        movie.setStudios(studiosList);
    }

    private void adjustProducers(Movie movie) {
        Set<Producer> producers = movie.getProducers() != null ?movie.getProducers() :new HashSet<>();
        Set<Producer> producersList = new HashSet<>(producers.size());

        for (Producer producer: producers) {
            Producer producerPersisted = producerRepository.findByName(producer.getName());
            producersList.add(producerPersisted != null? producerPersisted :producer);
        }
        movie.setProducers(producersList);
    }

    @Override
    public Movie findByTitle(String title) {
        TypedQuery<Movie> query = entityManager.createQuery(
                "select m from Movie m"
                        + " left join fetch m.studios"
                        + " left join fetch m.producers"
                        + " where m.title = :title",
                Movie.class);
        query.setParameter("title", title);

        return query.getSingleResult();
    }

    @Override
    public List<Movie> findAll() {
        TypedQuery<Movie> query = entityManager.createQuery(
                "select m from Movie m"
                + " left join fetch m.studios"
                + " left join fetch m.producers",
                Movie.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Movie movie = entityManager.find(Movie.class, id);

        if (movie == null) {
            throw new EntityNotFoundException("Movie not found by id: " + id);
        }

        entityManager.remove(movie);
    }

}
