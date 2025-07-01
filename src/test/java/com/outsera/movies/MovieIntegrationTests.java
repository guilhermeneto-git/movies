package com.outsera.movies;

import com.outsera.movies.entity.Movie;
import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.Studio;
import com.outsera.movies.entity.dto.ProducerDTO;
import com.outsera.movies.repository.MovieRepository;
import com.outsera.movies.repository.ProducerRepository;
import com.outsera.movies.repository.StudioRepository;
import com.outsera.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = MovieIntegrationTests.class)
@ActiveProfiles("test")
@TestConfiguration
public class MovieIntegrationTests {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    @Transactional
    public void checkMovieCreated() {
        Movie movie = new Movie();
        movie.setYear(2025);
        movie.setTitle("Formula 1 Driver");
        movie.addStudio(new Studio("Europe Movies"));
        movie.addProducer(new Producer("Ron Denis"));
        movie.addProducer(new Producer("Allan Prost"));
        movie.setWinner("");

        movieService.create(movie);

        Movie movieFound = movieRepository.findByTitle("Formula 1 Driver");

        assertNotNull(movieFound, "Movie should be not null");
        assertEquals(2025, movieFound.getYear(), "Movie year should be '2025'");
        assertEquals("Formula 1 Driver", movieFound.getTitle(), "Movie title should be 'Formula 1 Driver'");
        assertEquals("", movieFound.getWinner(), "Movie winner should be empty.");

        assertNotNull(movieFound.getStudios(), "Studios should be not null");
        assertNotNull(movieFound.getProducers(), "Producers should be not null");

        assertEquals(1, movieFound.getStudios().size(), "Studios size should be 1");
        assertEquals(2, movieFound.getProducers().size(), "Producers size should be 2");

        assertEquals("Europe Movies", movieFound.getStudios().iterator().next().getName(), "Studio name should be 'Europe Movies'");

        assertArrayEquals(new String[]{"Allan Prost", "Ron Denis"}, movieFound.getProducers().stream().map(Producer::getName).sorted().toArray(), "Producers names should be 'Ron Denins' and 'Allan Prost'");
    }

    @Test
    public void checkMovieDeleted() {
        Movie movie = new Movie();
        movie.setYear(2025);
        movie.setTitle("Formula 1 Driver");
        movie.addStudio(new Studio("Europe Movies"));
        movie.addProducer(new Producer("Allan Prost"));
        movie.setWinner("");

        movieService.create(movie);

        Movie movieFound = movieRepository.findByTitle("Formula 1 Driver");
        assertNotNull(movieFound, "Movie should be not null");

        movieService.removeById(movieFound.getId());

        Movie movieDeleted = movieRepository.findById(movieFound.getId());
        assertNull(movieDeleted, "Movie should be null");

        Studio studioFound = studioRepository.findByName("Europe Movies");
        assertNotNull(studioFound, "Studio should be not null");

        Producer producerFound = producerRepository.findByName("Allan Prost");
        assertNotNull(producerFound, "Producer should be not null");
    }

    @Test
    public void checkMovieUpdated() {
        Movie movie = new Movie();
        movie.setYear(2023);
        movie.setTitle("Formula Indy Driver");
        movie.addStudio(new Studio("American Movies"));
        movie.addProducer(new Producer("Scott Dickson"));
        movie.setWinner("");

        movieService.create(movie);

        Movie movieFound = movieRepository.findByTitle("Formula Indy Driver");
        assertNotNull(movieFound, "Movie should be not null");
        assertEquals(2023, movieFound.getYear(), "Year should be 2023");
        assertEquals("", movieFound.getWinner(), "Winner should be blank");

        movieFound.setYear(2024);
        movieFound.setWinner("yes");

        movieRepository.update(movieFound);

        Movie movieUpdated = movieRepository.findByTitle("Formula Indy Driver");
        assertNotNull(movieUpdated, "Movie updated should be not null");
        assertEquals(2024, movieUpdated.getYear(), "Year should be 2024");
        assertEquals("yes", movieUpdated.getWinner(), "Winner shoulf be 'yes'");
    }

    @Test
    public void checkWinnerProducerWithMinAndMaxInterval() {
        Studio studio1 = new Studio("Public Movies");

        Producer producer1 = new Producer("Armand Cruise");
        Producer producer2 = new Producer("Norton Land");
        Producer producer3 = new Producer("Cobalt Wiki");
        Producer producer4 = new Producer("Philipe Jordan II");

        Movie movie1 = new Movie();
        movie1.setYear(1999);
        movie1.setTitle("Football for gamers");
        movie1.setWinner("yes");
        movie1.addStudio(studio1);
        movie1.addProducer(producer1);
        movie1.addProducer(producer2);

        Movie movie2 = new Movie();
        movie2.setYear(2005);
        movie2.setTitle("The best friend of bride");
        movie2.setWinner("");
        movie2.addStudio(studio1);
        movie2.addProducer(producer2);
        movie2.addProducer(producer3);

        Movie movie3 = new Movie();
        movie3.setYear(2007);
        movie3.setTitle("Two is better than one");
        movie3.setWinner("yes");
        movie3.addStudio(studio1);
        movie3.addProducer(producer3);
        movie3.addProducer(producer4);

        Movie movie4 = new Movie();
        movie4.setYear(2008);
        movie4.setTitle("The darkest night");
        movie4.setWinner("yes");
        movie4.addStudio(studio1);
        movie4.addProducer(producer1);
        movie4.addProducer(producer3);

        Movie movie5 = new Movie();
        movie5.setYear(2010);
        movie5.setTitle("You can do it");
        movie5.setWinner("");
        movie5.addStudio(studio1);
        movie5.addProducer(producer1);
        movie5.addProducer(producer2);
        movie5.addProducer(producer3);

        Movie movie6 = new Movie();
        movie6.setYear(2016);
        movie6.setTitle("The cars");
        movie6.setWinner("yes");
        movie6.addStudio(studio1);
        movie6.addProducer(producer4);

        movieService.create(movie1);
        movieService.create(movie2);
        movieService.create(movie3);
        movieService.create(movie4);
        movieService.create(movie5);
        movieService.create(movie6);

        Map<String, List<ProducerDTO>> producers = producerRepository.findWinnerProducerWithMinAndMaxInterval();

        assertNotNull(producers, "Producers should be not null");
        assertEquals(2, producers.size(), "Producers should be 2");

        assertArrayEquals(new String[]{"max","min"}, producers.keySet().stream().sorted().toArray());

        List<ProducerDTO> producersMin = producers.get("min");
        List<ProducerDTO> producersMax = producers.get("max");

        assertEquals(1, producersMin.size(), "Producers with minimum interval should be 1");
        assertEquals(2, producersMax.size(), "Producers with maximum interval should be 2");

        // min - producer 3 - Cobalt Wiki - previous year (2007) - following year (2008) - interval (1)
        ProducerDTO producerDTOMin = producersMin.getFirst();
        assertNotNull(producerDTOMin, "Producer with minimum interval should be not null");
        assertEquals("Cobalt Wiki", producerDTOMin.getProducer());
        assertEquals(2007, producerDTOMin.getPreviousWin(), "Previous year should be 2007");
        assertEquals(2008, producerDTOMin.getFollowingWin(), "Following year should be 2008");
        assertEquals(1, producerDTOMin.getInterval(), "Interval should be 1");

        // max - producer 1 - Armand Cruise - previous year (1999) - following year (2008) - interval (9)
        ProducerDTO producerDTOMax1 = producersMax.getFirst();
        assertNotNull(producerDTOMax1, "Producer with maximum interval should be not null");
        assertEquals("Armand Cruise", producerDTOMax1.getProducer());
        assertEquals(1999, producerDTOMax1.getPreviousWin(), "Previous year should be 1999");
        assertEquals(2008, producerDTOMax1.getFollowingWin(), "Following year should be 2008");
        assertEquals(9, producerDTOMax1.getInterval(), "Interval should be 9");

        ProducerDTO producerDTOMax2 = producersMax.getLast();
        assertNotNull(producerDTOMin, "Producer with maximum interval should be not null");
        assertEquals("Philipe Jordan II", producerDTOMax2.getProducer());
        assertEquals(2007, producerDTOMax2.getPreviousWin(), "Previous year should be 2007");
        assertEquals(2016, producerDTOMax2.getFollowingWin(), "Following year should be 2016");
        assertEquals(9, producerDTOMax2.getInterval(), "Interval should be 9");
    }

}
