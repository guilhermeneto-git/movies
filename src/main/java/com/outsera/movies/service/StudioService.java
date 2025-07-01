package com.outsera.movies.service;

import com.outsera.movies.entity.Studio;

import java.util.List;

public interface StudioService {

    Studio save(Studio studio);

    Studio findById(Long id);

    Studio findByName(String name);

    List<Studio> findAllOrderById();

    void removeById(Long id);

}
