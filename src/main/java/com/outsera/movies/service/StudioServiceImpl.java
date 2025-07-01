package com.outsera.movies.service;

import com.outsera.movies.entity.Studio;
import com.outsera.movies.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioRepository studioRepository;

    public Studio save(Studio studio) {
        return studioRepository.save(studio);
    }

    @Override
    public Studio findById(Long id) {
        return studioRepository.findById(id);
    }

    @Override
    public Studio findByName(String name) {
        return studioRepository.findByName(name);
    }

    @Override
    public List<Studio> findAllOrderById() {
        return studioRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void removeById(Long id) {
        studioRepository.removeById(id);
    }

}
