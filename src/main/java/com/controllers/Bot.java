package com.controllers;

import botService.CustomerService;
import botService.FollowerService;
import botService.PostService;
import botBase.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    CustomersEntity telegaCustomer = null;

    CustomerService customerService = new CustomerService();
    FollowerService followerService = new FollowerService();
    PostService postService = new PostService();

    String login = null;
    Boolean flag = false;
    String msg2;




    //для према сообщений пользователя через лонгпул
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        if (msg != null && msg.hasText()) {
            switch (msg.getText()) {
                case "/start":
                    sendMsg(msg, "Welcome to MoviesMTBot! Check your keyboard and continue");
                    break;
                case "/help":
                    StringBuilder builder = new StringBuilder();
                    builder.append("Команды: ");
                    builder.append("\n");
                    builder.append("/login - залогиниться ");
                    builder.append("\n");
                    builder.append("/subscriptions - посмотреть новые посты подписок ");
                    builder.append("\n");
                    sendAnswer(msg, builder.toString());
                    break;
                case "/login":
                    if (this.telegaCustomer != null) sendAnswer(msg, "Вы уже вошли под своим именем ");
                    else sendAnswer(msg, "Чтобы продолжить дальше введите логин");
                    msg2 = msg.getText();
                    break;
                case "/subscriptions":
                    //метод который формирует подписки и количесвто постов
                    if(this.telegaCustomer != null) sendAnswer(msg, allSub(this.telegaCustomer.getId()));
                    else {sendMsg(msg, "Вы не прошли регистрацию");}
                    break;

                default:
                    if (msg2.equals("/login") && !flag) {
                        login = msg.getText();
                        sendMsg(msg, "Введите пароль");
                        msg2="Введите пароль";
                        flag = true;
                        break;
                    }
                    if (login != null &&  flag) {
                        try {
                            this.telegaCustomer = findUser(login);

                            if (msg.getText().equals(this.telegaCustomer.getPassword())) {

                                sendMsg(msg, "Добро пожаловать " + findUser(login).getName() + "\n" +
                                        "/subscriptions - если хотите узнать о постах на сегодня ");
                                login = null;
                                flag = false;

                            } else {
                                sendMsg(msg, "некорректные данные");
                                flag = true;
                            }

                        } catch (NullPointerException e) {
                            sendAnswer(msg, "некорректные данные");
                        }
                    }else {
                        sendMsg(msg, "некорректные данные1");
                    }
                    break;


            }

        }
    }

    public String getBotUsername() {
        return "MoviesMTBot";
    }

    public String getBotToken() {
        return "716567697:AAGuM9M1EkI3ft5I_2rJowBv0w56sluRAcE";
    }

    //отправка ответа на просьбу
    public synchronized void sendAnswer(Message message, String reply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(reply);

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMsg(Message message, String reply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(reply);

        try {
            if (message.getText().equals("/start")) setButton(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //клавиатура висит постоянно
    public void setButton(SendMessage msg) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        //связывем сообщение с клавиатурой
        msg.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true); //кому выводится клавиатура всем)
        replyKeyboardMarkup.setResizeKeyboard(true); //автоматический подгон клавиатуры по размеру
        replyKeyboardMarkup.setOneTimeKeyboard(false); //клавиатура не будет скрываться после нажатия

        //сама клавиатура
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstRaw = new KeyboardRow();
        firstRaw.add(new KeyboardButton("/help"));

        keyboardRowList.add(firstRaw);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }


    public CustomersEntity findUser(String s) {

        CustomersEntity customer = customerService.findCustomer(s);
        return customer;
    }

    public String allSub(int id) {
        StringBuilder builder = new StringBuilder();
        List<FollowerActorEntity> actors = followerService.findActors(this.telegaCustomer.getId());
        List<FollowerAnalystEntity> analysts = followerService.findAnalysts(this.telegaCustomer.getId());
        List<FollowerStudioEntity> studios = followerService.findStudios(this.telegaCustomer.getId());

        if (actors == null && analysts == null && studios == null) return "Вы не подписаны на кого-либо";

        builder.append("на сеогодня: ");
        builder.append("\n");

        for (FollowerActorEntity a : actors) {
            List<PostEntity> p = postService.findPostsBySub(a.getIdActor());
            if (p.size() != 0) {
                builder.append(customerService.checkCustomer(a.getIdActor()).getName());
                builder.append(" - ");
                builder.append(p.size());
                builder.append("\n");
            }
            p.clear();
        }

        for (FollowerAnalystEntity a : analysts) {
            List<PostEntity> p = postService.findPostsBySub(a.getIdAnalyst());
            if (p.size() != 0) {
                builder.append(customerService.checkCustomer(a.getIdAnalyst()).getName());
                builder.append(" - ");
                builder.append(p.size());
                builder.append("\n");
            }
            p.clear();
        }

        for (FollowerStudioEntity a : studios) {
            List<PostEntity> p = postService.findPostsBySub(a.getIdStudio());
            if (p.size() != 0) {
                builder.append(customerService.checkCustomer(a.getIdStudio()).getName());
                builder.append(" - ");
                builder.append(p.size());
                builder.append("\n");
            }
            p.clear();
        }


        return builder.toString();
    }
}
