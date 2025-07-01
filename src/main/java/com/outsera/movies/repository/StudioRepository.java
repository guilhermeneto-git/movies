package com.outsera.movies.repository;

import com.outsera.movies.entity.Studio;

import java.util.List;

public interface StudioRepository {

    Studio save(Studio studio);

    Studio findById(Long id);

    Studio findByName(String name);

    List<Studio> findAllByOrderByIdAsc();

    void removeById(Long id);

}
