package com.repository;

import com.database.PostTagEntity;
import com.database.PostTagEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostTagRepository extends JpaRepository<PostTagEntity, Long> {
    @Modifying
    @Query(value = "insert into post_tag (id_post, id_tag) VALUES (:insertLink,:id)", nativeQuery = true)
    @Transactional
    void insert(@Param("insertLink") int insertLink, @Param("id") int id);

    PostTagEntity findByIdTag(long id_tag);
}
