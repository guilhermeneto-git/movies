package com.outsera.movies.repository;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.dto.ProducerDTO;

import java.util.List;
import java.util.Map;

public interface ProducerRepository {

    Producer save(Producer producer);

    Producer findById(Long id);

    Producer findByName(String name);

    List<Producer> findAllByOrderByIdAsc();

    Map<String, List<ProducerDTO>> findWinnerProducerWithMinAndMaxInterval();

    void removeById(Long id);

}
