package com.outsera.movies;

import com.outsera.movies.entity.Studio;
import com.outsera.movies.repository.StudioRepository;
import com.outsera.movies.service.StudioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class StudioIntegrationTests {

    @Autowired
    private StudioService studioService;

    @Autowired
    private StudioRepository studioRepository;

    @Test
    public void checkStudioCreated() {
        studioService.save(new Studio("Golden Movies"));

        Studio studioFound = studioRepository.findByName("Golden Movies");

        assertNotNull(studioFound, "Studio should be not null");
    }

    @Test
    public void checkStudioDeleted() {
        studioService.save(new Studio("Silver Movies"));

        Studio studioFound = studioRepository.findByName("Silver Movies");
        assertNotNull(studioFound, "Studio should be not null");

        studioService.removeById(studioFound.getId());

        Studio studioDelete = studioRepository.findByName("Silver Movies");
        assertNull(studioDelete, "Studio should be null");
    }

    @Test
    public void checkStudioUpdated() {
        studioService.save(new Studio("Golden Movies Age"));

        Studio studioFound = studioRepository.findByName("Golden Movies Age");
        assertNotNull(studioFound, "Studio should be not null");

        studioFound.setName("Silver Movies Age");
        studioService.save(studioFound);

        Studio studioUpdated = studioRepository.findByName("Silver Movies Age");
        assertNotNull(studioFound, "Studio should be not null");
    }

}
