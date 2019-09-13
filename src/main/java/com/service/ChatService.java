package com.service;

import com.database.ChatEntity;
import com.database.ChatUserEntity;
import com.repository.ChatRepository;
import com.repository.ChatUserRepository;
import com.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;
    private final FilmRepository filmRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, ChatUserRepository chatUserRepository, FilmRepository filmRepository) {
        this.chatRepository = chatRepository;
        this.chatUserRepository = chatUserRepository;
        this.filmRepository = filmRepository;
    }

    public String returnSetMes() {
        try {
            Date date = new Date();
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setCreateDate((java.sql.Date) date);
            chatEntity.setMsg("cool");
            chatEntity.setFilmId(1);
            chatEntity.setUserUsId(1);
            chatRepository.save(chatEntity);
            return "ok";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String returnSetMes(int film_id, int user_id, String message) {
        try {
            ChatEntity chatEntity = new ChatEntity();
            java.util.Date date = new java.util.Date();
            chatEntity.setCreateDate(new java.sql.Date(date.getTime()));
            chatEntity.setMsg(message);
            chatEntity.setFilmId(film_id);
            chatEntity.setUserUsId(user_id);
            chatRepository.save(chatEntity);
            return "ok";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String returnMessageContent(int film_id) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            StringBuilder answer = new StringBuilder();
            answer.append("[");

            List<ChatEntity> chat = chatRepository.findByFilmId(film_id);
            for (ChatEntity message : chat) {
                answer.append("{\"message\":\"")
                        .append(message.getMsg())
                        .append("\",\"date\":\"")
                        .append(dateFormat.format(message.getCreateDate()))
                        .append("\",\"user\":\"")
                        .append(message.getCustomers().getName())
                        .append("\"},");
            }
            answer.setLength(answer.length() - 1);
            answer.append("]");
            return String.valueOf(answer);
        } catch (NullPointerException ex) {
            return null;
        }
    }



     public boolean checkUserInChat() {
        try {
            return chatRepository.findAllByUserUsId(1).size() > 0;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public String addNewChat() {
        try {
            ChatUserEntity chatUserEntity = new ChatUserEntity();
            chatUserEntity.setIdChat(1);
            chatUserEntity.setIdUs(1);
            chatUserRepository.save(chatUserEntity);
            return "User added chat";
        } catch (NullPointerException ex) {
            return "Error, user not added";
        }
    }

    public String findAllChatsForUser() {
        try {
            int id_user = 1;
            List<ChatEntity> chatEntities = chatRepository.findAllByUserUsId(id_user);
            StringBuilder chatNames = new StringBuilder();
            for (ChatEntity chatEntity : chatEntities) {
                chatNames.append("  Chat Name:  ")
                        .append(chatEntity.getFilm().getName());
            }
            return String.valueOf(chatNames);
        } catch (NullPointerException ex) {
            return "Ошибка";
        }
    }

    public String findChatForFilm(int film_id) {
        try {
            List<ChatEntity> chatEntities = chatRepository.findAll();
            ChatEntity chatInfo;
            for (int i = 0; i < chatEntities.size(); i++) {
                if (chatEntities.get(i).getFilmId() == film_id) {
                    chatInfo = chatEntities.get(i);
                }
            }
            return "Find chat Entity for film";
        } catch (NullPointerException ex) {
            return null;
        }
    }

}
