package com.outsera.movies.service;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.dto.ProducerDTO;
import com.outsera.movies.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    @Override
    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    @Override
    public Producer findById(Long id) {
        return producerRepository.findById(id);
    }

    @Override
    public Producer findByName(String name) {
        return producerRepository.findByName(name);
    }

    @Override
    public List<Producer> findAllByOrderByIdAsc() {
        return producerRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Map<String, List<ProducerDTO>> findWinnerProducerWithMinAndMaxInterval() {
        return producerRepository.findWinnerProducerWithMinAndMaxInterval();
    }

    @Override
    public void removeById(Long id) {
        producerRepository.removeById(id);
    }
}
