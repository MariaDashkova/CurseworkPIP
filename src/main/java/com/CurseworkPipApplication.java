package com;

import com.controllers.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class CurseworkPipApplication {

    public static void main(String[] args) {
         SpringApplication.run(CurseworkPipApplication.class, args);

//        ApiContextInitializer.init();
//        TelegramBotsApi tApi = new TelegramBotsApi();
//        //ргеистрация бота
//        try {
//
//            tApi.registerBot(new Bot());
//        } catch (TelegramApiRequestException g) {
//            g.printStackTrace();
//        }

//        ApiContextInitializer.init();
//        ConfigurableApplicationContext ctx = SpringApplication.run(CurseworkPipApplication.class, args);
//        TelegramBotsApi tApi = new TelegramBotsApi();
//        try {
//            tApi.registerBot(ctx.getBean(Bot.class));
//        } catch (TelegramApiRequestException e) {
////            logger.error("Error: " + Arrays.toString(e.getStackTrace()));
//            e.printStackTrace();
//        }
    }
}
