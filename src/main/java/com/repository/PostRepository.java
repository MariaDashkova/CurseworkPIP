package com.repository;

import com.database.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    @Query(value = "select p from PostEntity p where p.userUsOwnerId = :id")
    List<PostEntity> findAllUsers(@Param("id") int id);

    @Query(value = "select p from PostEntity p where p.userUsOwnerId = :id")
    Collection<PostEntity> findByIdUsers(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("update PostEntity u set u.createDate = :date where u.id = :id")
    void updatePostDate(@Param("date") Date date, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update PostEntity u set u.body = :body where u.id = :id")
    void updatePostBody(@Param("body")String body, @Param("id") int id);


//    @Query(value = "SELECT p from customers follower " +
//
//            "join follower_analyst fa on follower.id = fa.id_customers " +
//            "join follower_studio fs on follower.id = fs.id_customers " +
//            "join follower_actor fac on follower.id = fac.id_customers " +
//
//            "join author_tag author_t on author_t.id_customers = fac.id_actor " +
//            "or author_t.id_customers = fs.id_studio or author_t.id_customers = fa.id_analyst " +
//
//            "join tag t on t.id = author_t.id_tag " +
//            "join post_tag pt on pt.id_tag = t.id " +
//            "join post p on p.id = pt.id_post " +
////            "left join author_tag author_t2 on author_t2.id_customers = fs.id_studio " +
////            "left join author_tag author_t3 on author_t3.id_customers = fa.id_analyst" +
//
//            " where follower.id = :id_follower " +
//            "order by t.count asc")
//    LinkedList<PostEntity> getInfoForUserRelevantFeed(@Param("id_follower") Integer id_us);

    List<PostEntity> findAll();

}
