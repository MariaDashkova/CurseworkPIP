package com.service;

import com.database.ComplaintEntity;
import com.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;

    @Autowired
    public ComplaintService(ComplaintRepository repository) {
        this.repository = repository;
    }

    public String returnAnswers() {
        try {
            StringBuilder allAnswers = new StringBuilder();
            List<ComplaintEntity> complaintEntities = repository.findComplaintEntitiesById(1);
            for (ComplaintEntity complaintEntity : complaintEntities) {
                allAnswers.append(complaintEntity.getAnswer());
                allAnswers.append("\n");
//                18:30
            }
            return allAnswers.toString();
        } catch (NullPointerException ex) {
            return null;
        }
    }


    public String returnAllComplaintForDevelopers() {
        String allComplains = "";
        try {
            List<ComplaintEntity> complaintEntities = repository.findComplaintEntitiesByIdNotNull();
            for (ComplaintEntity complaintEntity : complaintEntities) {
                allComplains = allComplains + " Пользователь: ";
//            allComplains = allComplains + complaintEntity.getIdCustomers();
                allComplains = allComplains + " Сообщение: ";
                allComplains = allComplains + complaintEntity.getBody();
                allComplains = allComplains + ("\n");
            }
        } catch (NullPointerException ex) {
            return null;
        }

        return allComplains;
    }

    public void saveNewComplaint(String body, int idCustomer) {
        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setBody(body);
        complaintEntity.setIdCustomers(idCustomer);
        repository.save(complaintEntity);

    }

    //TODO answer
    public void answer(int id, String body){
//        ComplaintEntity complaint = repository.findById(id);
//        complaint.setAnswer(body);
        repository.updateComplaint(body, id);
    }

    //TODO : удалние жалобы, если она рассмотрена
    public void deleteComplaint(int id) {
        ComplaintEntity complaintEntity = repository.findById(id);
        if (complaintEntity.getChecker()) repository.delete(complaintEntity);
    }

    //TOdo: вывести тольок актуальные жалобы сделала флаг
    public LinkedList<ComplaintEntity> currentComplaint() {
        //вот здесь выборка непроверенных
        LinkedList<ComplaintEntity> list = repository.dateSelect();
        return list;
    }
}
