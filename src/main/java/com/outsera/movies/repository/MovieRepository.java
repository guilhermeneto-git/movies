package com.outsera.movies.repository;

import com.outsera.movies.entity.Movie;

import java.util.List;

public interface MovieRepository {

    Movie create(Movie movie);

    Movie update(Movie movie);

    Movie findById(Long id);

    Movie findByTitle(String title);

    List<Movie> findAll();

    void removeById(Long id);

}
