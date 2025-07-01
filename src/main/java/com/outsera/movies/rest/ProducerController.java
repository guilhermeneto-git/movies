package com.outsera.movies.rest;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.dto.ProducerDTO;
import com.outsera.movies.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping
    public Producer create(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @PutMapping
    public Producer update(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @GetMapping("/{producerId}")
    public Producer findById(@PathVariable("producerId") Long producerId) {
        return producerService.findById(producerId);
    }

    @GetMapping
    public List<Producer> findAllOrderedById() {
        return producerService.findAllByOrderByIdAsc();
    }

    @GetMapping("/winners-with-min-and-max-interval")
    public Map<String, List<ProducerDTO>> findWinnerProducerWithMinAndMaxInterval() {
        return producerService.findWinnerProducerWithMinAndMaxInterval();
    }

    @DeleteMapping("/{producerId}")
    public void removeById(@PathVariable("producerId") Long producerId) {
        producerService.removeById(producerId);
    }

}
