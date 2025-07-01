package com.outsera.movies.repository;

import com.outsera.movies.entity.Studio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudioRepositoryImpl implements StudioRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Studio save(Studio studio) {
        return entityManager.merge(studio);
    }

    @Override
    public Studio findById(Long id) {
        return entityManager.find(Studio.class, id);
    }

    @Override
    public Studio findByName(String name) {
        TypedQuery<Studio> query = entityManager.createQuery("select s from Studio s " +
                "where s.name = :name", Studio.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Studio> findAllByOrderByIdAsc() {
        TypedQuery<Studio> query = entityManager.createQuery("select s.id, s.name from Studio s order by s.id", Studio.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Studio studio = entityManager.find(Studio.class, id);

        if (studio == null) {
            throw new EntityNotFoundException("Studio not found by id: " + id);
        }

        entityManager.remove(studio);
    }
}
