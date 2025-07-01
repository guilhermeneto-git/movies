package com.outsera.movies.rest;

import com.outsera.movies.entity.Studio;
import com.outsera.movies.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @GetMapping
    public List<Studio> findAllOrderById() {
        return studioService.findAllOrderById();
    }

    @PostMapping
    public Studio create(@RequestBody Studio studio) {
        return studioService.save(studio);
    }

    @PutMapping
    public Studio update(@RequestBody Studio studio) { return studioService.save(studio); }

    @GetMapping("/{studioId}")
    public Studio findById(@PathVariable("studioId") Long id) {
        return studioService.findById(id);
    }

    @DeleteMapping("/{studioId}")
    public void remove(@PathVariable("studioId") Long id) {
        studioService.removeById(id);
    }

}
