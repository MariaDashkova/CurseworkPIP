package com.repository;

import com.database.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;

public interface VereficationRepository extends JpaRepository<VerificationEntity, Long> {

    @Query(value = "select v from VerificationEntity v where v.idStudio = :id")
    LinkedList<VerificationEntity> selectActorsToVer(@Param("id") Integer id);


}
