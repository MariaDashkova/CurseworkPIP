package com.repository;

import com.database.GalleryFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryFilmRepository extends JpaRepository<GalleryFilmEntity, Long> {

        List<GalleryFilmEntity> findAllByIdFilm(int id_film);

}
