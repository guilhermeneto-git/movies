package com.outsera.movies;

import com.outsera.movies.entity.HEADERS;
import com.outsera.movies.entity.Movie;
import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.Studio;
import com.outsera.movies.service.MovieService;
import com.outsera.movies.service.ProducerService;
import com.outsera.movies.service.StudioService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;

@Component
@Profile("!test")
public class MoviesImport implements CommandLineRunner {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private StudioService studioService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private MovieService movieService;

    @Override
    public void run(String... args) throws Exception {
        File file = resourceLoader.getResource("classpath:data/Movielist.csv").getFile();
        FileReader in = new FileReader(file);
        String[] headers = {"year", "title", "studios", "producers", "winner"};
        CSVFormat csv = CSVFormat.EXCEL.builder().setDelimiter(";").setHeader(HEADERS.class).setSkipHeaderRecord(true).get();
        Iterable<CSVRecord> records = csv.parse(in);
        for (CSVRecord record: records) {
            Movie movie = new Movie();
            movie.setYear(Integer.valueOf(record.get(HEADERS.year)));
            movie.setTitle(record.get(HEADERS.title));

            addStudios(movie, record.get(HEADERS.studios));
            addProducers(movie, record.get(HEADERS.producers));

            movie.setWinner(record.get(HEADERS.winner));

            movieService.create(movie);
        }
    }

    private void addStudios(Movie movie, String studios) {
        String [] studiosNames = studios.split("(\\band\\b|,)");
        for (String studioName: studiosNames) {
            if (!studioName.isBlank()) {
                Studio studio = new Studio(studioName.trim());
                movie.addStudio(studio);
                studio.addMovie(movie);
            }
        }
    }

    private void addProducers(Movie movie, String producers) {
        String [] producersNames = producers.split("(\\band\\b|,)");
        for (String producerName: producersNames) {
            if (!producerName.isBlank()) {
                Producer producer = new Producer(producerName.trim());
                movie.addProducer(producer);
                producer.addMovie(movie);
            }
        }
    }

}
