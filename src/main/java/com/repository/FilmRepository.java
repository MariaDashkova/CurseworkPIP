package com.repository;

import com.database.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    FilmEntity findById(int id);

    @Query(value = "SELECT f from film f" +
            " join film_tag ft on f.id = ft.id_film join" +
            " tag t on ft.id_tag = t.id order by t.count desc", nativeQuery = true)
    LinkedList<FilmEntity> popularFilmForFeed();

}