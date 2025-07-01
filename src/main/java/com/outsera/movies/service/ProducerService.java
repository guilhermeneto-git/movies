package com.outsera.movies.service;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.dto.ProducerDTO;

import java.util.List;
import java.util.Map;

public interface ProducerService {

    Producer save(Producer producer);

    Producer findById(Long id);

    Producer findByName(String name);

    List<Producer> findAllByOrderByIdAsc();

    Map<String, List<ProducerDTO>> findWinnerProducerWithMinAndMaxInterval();

    void removeById(Long id);

}
