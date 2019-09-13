package com.repository;

import com.database.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;

public interface CustomersRepository extends JpaRepository<CustomersEntity, Long> {

    @Query(value = "select r.id from CustomersEntity r")
    HashSet<Integer> allId();



    CustomersEntity findCustomersEntityById(int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.profileImageUrl = :imgUrl where u.id = :id")
    void updateUserSetImgForId(@Param("imgUrl") String email, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.emailHash = :emailHash where u.id = :id")
    void updateUserSetEmailForId(@Param("emailHash") String email, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.password = :password where u.id = :id")
    void updateUserSetPasswordForId(@Param("password") String password, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.levelAccess = :level_access where u.id = :id")
    void updateUserSetLevelAccessForId(@Param("level_access") int level_access, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.mobNumber = :mobNumber where u.id = :id")
    void updateUserSetMobailNumberForId(@Param("mobNumber") String mobNumber, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.name = :name where u.id = :id")
    void updateUserSetNameForId(@Param("name") String name, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("update CustomersEntity u set u.img = :img where u.id = :id")
    void updateUserSetIMGForId(@Param("img") byte[] img, @Param("id") int id);

    @Query(value = "select a from CustomersEntity a where a.id = :id")
    CustomersEntity findById(@Param("id") Integer id); // return email be get in Entity

    @Query(value = "select a from CustomersEntity a where a.login= :log and a.password = :pas")
    CustomersEntity findLogPas(@Param("log") String log, @Param("pas") String pas); // return email be get in Entity

    @Query(value = "select a from CustomersEntity a where a.login= :log")
    CustomersEntity findByLog(@Param("log") String log); // return email be get in Entity

//    @Modifying
//    @org.springframework.transaction.annotation.Transactional
//    @Query(value = "update CustomersEntity u set u.email = :email where u.id = :id")
//    void updateUserSetEmailForId(@Param("email") String email, @Param("id") Integer id);

    CustomersEntity findById(int id_user);

    @Query(value = "select r from CustomersEntity r where r.login = :log")
    CustomersEntity findByLogin(@Param("log") String log);

    @Query(value = "select r from CustomersEntity r where r.password = :log")
    CustomersEntity findByPassword(@Param("log") String log);

}
