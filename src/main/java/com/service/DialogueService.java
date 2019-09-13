package com.service;

import com.database.DialogueEntity;
import com.database.MessageEntity;
import com.repository.DialogueRepository;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class DialogueService {
    private final DialogueRepository dialogueRepository;

    private final MessageRepository messageRepository;

    @Autowired
    public DialogueService(DialogueRepository dialogueRepository, MessageRepository messageRepository) {
        this.dialogueRepository = dialogueRepository;
        this.messageRepository = messageRepository;
    }


    public String analystDialogs(int id_analyst) {
        List<DialogueEntity> dialogsForAnalyst = dialogueRepository.findByIdAnalyst(id_analyst);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (DialogueEntity dialogue : dialogsForAnalyst) {
            ArrayList<MessageEntity> messages = (ArrayList<MessageEntity>) dialogue.getMessages();
            messages.sort(Comparator.comparing(MessageEntity::getCreateDate));
            stringBuilder.append("{\"id\":")
                    .append(dialogue.getId())
                    .append("\"studio\":\"")
                    .append(dialogue.getStudios().getCustomers().getName())
                    .append("\",\"last_message\":\"")
                    .append(messages.get(0).getBody())
                    .append("\"},");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return String.valueOf(stringBuilder);
    }

    public boolean check(int id_customer, int id_dialogue){
        DialogueEntity d = dialogueRepository.findById(id_dialogue);
        if (d.getIdStudio() == id_customer || d.getIdAnalyst() == id_customer) return true;
        return false;
    }

    public String studioDialogs(int id_studio) {
        List<DialogueEntity> dialogsForAnalyst = dialogueRepository.findByIdStudio(id_studio);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (DialogueEntity dialogue : dialogsForAnalyst) {
            ArrayList<MessageEntity> messages = (ArrayList<MessageEntity>) dialogue.getMessages();
            messages.sort(Comparator.comparing(MessageEntity::getCreateDate));
            stringBuilder.append("{\"id\":")
                    .append(dialogue.getId())
                    .append("\"analyst\":\"")
                    .append(dialogue.getStudios().getCustomers().getName()) //или логин возвращать?
                    .append("\",\"last_message\":\"")
                    .append(messages.get(0).getBody()) //аккуратнее
                    .append("\"},");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return String.valueOf(stringBuilder);
    }


    public String messageFromDiaogue(int id_dialogue) {
        DialogueEntity dialogue = dialogueRepository.findById(id_dialogue);
        ArrayList<MessageEntity> messages = (ArrayList<MessageEntity>) dialogue.getMessages();
        messages.sort(Comparator.comparing(MessageEntity::getCreateDate));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (MessageEntity message : messages) {
            stringBuilder.append("{\"id\":")
                    .append(message.getId())
                    .append(", \"message\":\"")
                    .append(message.getBody())
                    .append("\", \"analyst\":\"")
                    .append(message.getAnalysts().getCustomersAnalyst().getName())
                    .append("\", \"studio\":\"")
                    .append(message.getStudios().getCustomers().getName())
                    .append("\", \"date\":\"")
                    .append(message.getCreateDate())
                    .append("\"},");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return String.valueOf(stringBuilder);
    }

    public void setMessages(int id_studio, int id_analyst, String message) {
        MessageEntity messageEntity = new MessageEntity();
        DialogueEntity dialogue = dialogueRepository.findByIdStudioAndIdAnalyst(id_studio, id_analyst);
        if (dialogue != null) messageEntity.setIdDialogue(dialogue.getId());
        else {
            dialogue = new DialogueEntity();
            dialogue.setId(messageEntity.getIdDialogue());
            dialogue.setIdAnalyst(id_analyst);
            dialogue.setIdStudio(id_studio);
            dialogueRepository.save(dialogue);
        }
        messageEntity.setIdAnalyst(id_analyst);
        messageEntity.setIdStudio(id_studio);
        messageEntity.setBody(message);

        messageRepository.save(messageEntity);
    }

}