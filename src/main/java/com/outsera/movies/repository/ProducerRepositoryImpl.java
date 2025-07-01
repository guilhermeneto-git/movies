package com.outsera.movies.repository;

import com.outsera.movies.entity.Producer;
import com.outsera.movies.entity.dto.ProducerDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProducerRepositoryImpl implements ProducerRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Producer save(Producer producer) {
        return entityManager.merge(producer);
    }

    @Override
    public Producer findById(Long id) {
        return entityManager.find(Producer.class, id);
    }

    @Override
    public Producer findByName(String name) {
        TypedQuery<Producer> query = entityManager.createQuery("select p from Producer p " +
                "where p.name = :name", Producer.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Producer> findAllByOrderByIdAsc() {
        TypedQuery<Producer> query = entityManager.createQuery("select p.id, p.name from Producer p order by p.id", Producer.class);

        return query.getResultList();
    }

    @Override
    public Map<String, List<ProducerDTO>> findWinnerProducerWithMinAndMaxInterval() {
        Map<String, List<ProducerDTO>> map = new LinkedHashMap<>(2);
        map.put("min", findWinnerProducerWithMinInterval());
        map.put("max", findWinnerProducerWithMaxInterval());

        return map;
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Producer producer = entityManager.find(Producer.class, id);

        if (producer == null) {
            throw new EntityNotFoundException("Producer not found by id: " + id);
        }

        entityManager.remove(producer);
    }

    private List<ProducerDTO> findWinnerProducerWithMinInterval() {
        TypedQuery<ProducerDTO> query = entityManager.createQuery(
                "select p.name, min(abs(m1.year - m2.year)), m1.year, m2.year from Producer p"
                        + " join p.movies m1 on (m1.winner = 'yes')"
                        + " join p.movies m2 on (m2.id <> m1.id and m2.winner = 'yes' and m1.year <= m2.year)"
                        + " group by p.name, m1.year, m2.year"
                        + " having min(abs(m1.year - m2.year)) = ("
                            + " select min(abs(mm1.year - mm2.year)) from Producer pp"
                            + " join pp.movies mm1 on (mm1.winner = 'yes')"
                            + " join pp.movies mm2 on (mm2.id <> mm1.id and mm2.winner = 'yes' and mm1.year <= mm2.year)"
                        + " )", ProducerDTO.class);

        return query.getResultList();
    }

    private List<ProducerDTO> findWinnerProducerWithMaxInterval() {
        TypedQuery<ProducerDTO> query = entityManager.createQuery(
                " select p.name, max(abs(m1.year - m2.year)), m1.year, m2.year from Producer p"
                        + " join p.movies m1 on (m1.winner = 'yes')"
                        + " join p.movies m2 on (m2.id <> m1.id and m2.winner = 'yes' and m1.year <= m2.year)"
                        + " group by p.name, m1.year, m2.year"
                        + " having max(abs(m1.year - m2.year)) = ("
                            + " select max(abs(mm1.year - mm2.year)) from Producer pp"
                            + " join pp.movies mm1 on (mm1.winner = 'yes')"
                            + " join pp.movies mm2 on (mm2.id <> mm1.id and mm2.winner = 'yes' and mm1.year <= mm2.year)"
                        + " )",
                ProducerDTO.class
        );

        return query.getResultList();
    }
}
