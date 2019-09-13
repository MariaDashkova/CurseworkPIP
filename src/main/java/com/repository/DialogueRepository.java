package com.repository;

import com.database.DialogueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public interface DialogueRepository extends JpaRepository<DialogueEntity, Long> {


    @Query(value = "select d from  DialogueEntity d where d.id = :id")
    DialogueEntity findById(@Param("id") int id);

    @Query(value = "select d.id from DialogueEntity d where d.idStudio = :id")
    ArrayList<Integer> getStudioDialogue(@Param("id") int id);

    @Query(value = "select d.id from DialogueEntity d where d.idAnalyst = :id")
    ArrayList<Integer> getAnalystDialogue(@Param("id") int id);

    @Query(value = "select d.id from DialogueEntity d where d.idAnalyst = :idA and d.idStudio = :idS")
    DialogueEntity getDialogue(@Param("idA") int idA, @Param("idS") int idS);

    List<DialogueEntity> findByIdAnalyst(int id_analyst);

    List<DialogueEntity> findByIdStudio(int id_studio);

    DialogueEntity findByIdStudioAndIdAnalyst(int id_studio, int id_analyst);

}
