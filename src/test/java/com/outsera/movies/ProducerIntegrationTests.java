package com.outsera.movies;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.Studio;
import com.outsera.movies.repository.ProducerRepository;
import com.outsera.movies.service.ProducerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class ProducerIntegrationTests {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    public void checkProducerCreated() {
        producerService.save(new Producer("Denzel Washington"));

        Producer producerFound = producerRepository.findByName("Denzel Washington");

        assertNotNull(producerFound, "Producer should be not null");
    }

    @Test
    public void checkProducerDeleted() {
        producerService.save(new Producer("Jordan Kane"));

        Producer producerFound = producerRepository.findByName("Jordan Kane");
        assertNotNull(producerFound, "Producer should be not null");

        producerService.removeById(producerFound.getId());

        Producer producerDeleted = producerRepository.findById(producerFound.getId());
        assertNull(producerDeleted, "Producer should be null");
    }

    @Test
    public void checkProducerUpdated() {
        producerService.save(new Producer("Kimi Ortega"));

        Producer producerFound = producerRepository.findByName("Kimi Ortega");
        assertNotNull(producerFound, "Producer should be not null");

        producerFound.setName("Kimi Ortega Lopes");
        producerService.save(producerFound);

        Producer producerUpdated = producerRepository.findByName("Kimi Ortega Lopes");
        assertNotNull(producerFound, "Studio should be not null");
    }

}
