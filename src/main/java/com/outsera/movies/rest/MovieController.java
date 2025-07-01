package com.outsera.movies.rest;

import com.outsera.movies.entity.Movie;
import com.outsera.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return movieService.create(movie);
    }

    @PutMapping
    public Movie update(@RequestBody Movie movie) {
        return movieService.update(movie);
    }

    @GetMapping("/{movieId}")
    public Movie findById(@PathVariable("movieId") Long movieId) {
        return movieService.findById(movieId);
    }

    @GetMapping("/title/{title}")
    public Movie findByTitle(@PathVariable("title") String title) {
        return movieService.findByTitle(title);
    }

    @DeleteMapping("/{movieId}")
    public void removeById(@PathVariable("movieId") Long movieId) {
        movieService.removeById(movieId);
    }

}
