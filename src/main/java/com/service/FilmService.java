package com.service;

import com.database.*;
import com.repository.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final ScoreAnalystRepository scoreAnalystRepository;


    @Autowired
    public FilmService(FilmRepository filmRepository,
                       PostRepository postRepository,
                       ReviewRepository reviewRepository, ScoreAnalystRepository scoreAnalystRepository) {
        this.filmRepository = filmRepository;
        this.postRepository = postRepository;
        this.reviewRepository = reviewRepository;
        this.scoreAnalystRepository = scoreAnalystRepository;

    }

    public String getInfoForUserNewsFeed(int id_user) {
        int count = 0;
        StringBuilder info = new StringBuilder();
        try {

//            LinkedList<PostEntity> postEntities = postRepository.getInfoForUserRelevantFeed(id_user);
            List<PostEntity> postEntities = postRepository.findAll();

            List<PostEntity> forFollowerPostList = new LinkedList<>();

            for (PostEntity postEntity : postEntities) {
                boolean flag = false;
                if (postEntity.getCustomers().getActors() != null) {
                    for (FollowerActorEntity followers : postEntity.getCustomers().getActors()
                            .getFollowerActors()) {
                        if (followers.getCustomers().getId() == id_user) flag = true;
                    }
                }

                if (postEntity.getCustomers().getAnalysts() != null) {
                    for (FollowerAnalystEntity followers : postEntity.getCustomers().getAnalysts()
                            .getFollowerAnalysts()) {
                        if (followers.getCustomers().getId() == id_user) flag = true;
                    }
                }

                if (postEntity.getCustomers().getStudio() != null) {
                    for (FollowerStudioEntity followers : postEntity.getCustomers().getStudio()
                            .getFollowerStudios()) {
                        if (followers.getCustomers().getId() == id_user) flag = true;
                    }
                }

                if (flag) forFollowerPostList.add(postEntity);
            }


            for (PostEntity postEntity : forFollowerPostList) {
                info.append("Author:")
                        .append(postEntity.getCustomers().getName())
                        .append("Post:")
                        .append(postEntity.getBody());
                count++;
                if (count > 50) return String.valueOf(info);
            }

            return "UserFeed";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    //TODO: Добавить к Маше 10.02.2018
    public String findTheMostInterestingAnnotation(int id_film) {
        try {
            List<ReviewEntity> reviewEntities = reviewRepository.findAllByIdFilm(id_film);
            TreeMap<Float, ReviewEntity> entityTreeMap = new TreeMap<>();

            for (ReviewEntity reviewEntity : reviewEntities) {
                float sum = 0;
                for (ScoreAnalystEntity score : reviewEntity.getAnalysts().getScoreAnalysts()) {
                    sum = sum + score.getScore();
                }
                entityTreeMap.put(sum / reviewEntity.getAnalysts().getScoreAnalysts().size(),
                        reviewEntity);
            }
            StringBuilder str = new StringBuilder("[");
            for (Float key : entityTreeMap.descendingKeySet()) {
                str.append("{\"body\":")
                        .append("\"").append(entityTreeMap.get(key).getBody()).append("\"")
                        .append(", \"score\":")
                        .append("\"").append(entityTreeMap.get(key).getScore()).append("\"")
                        //.getAnalystByIdAnalyst().getCustomersByIdAnalyst().getUserUsUsById()).append("\"")
                        .append(", \"name\":")
                        .append("\"").append(entityTreeMap.get(key).getAnalysts().getCustomersAnalyst().getName()).append("\"")
                        .append("}, ");
            }
            str.setLength(str.length() - 3);
            str.append("}]");
            return String.valueOf(str);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String getInfoForPopularFilms() {
        StringBuilder info = new StringBuilder();
        int count = 0;
        try {
            LinkedList<FilmEntity> filmEntities = filmRepository.popularFilmForFeed();
            for (FilmEntity filmEntity : filmEntities) {
                count++;
                info.append("Name: ")
                        .append(filmEntity.getName())
                        .append("  Body: ").append(filmEntity.getBody())
                        .append("  CashBox:  ").append(filmEntity.getCashbox());
                if (count > 50) return String.valueOf(info);
            }
            return "popular feed";
        } catch (NullPointerException ex) {
            return null;
        }
    }

//    public String findMainInfoForTheFilm(int film_id, int user_id) throws IOException {
//        try {
//            float sum = 0;
//            float scoreUser = 0;
//            FilmEntity filmEntity = filmRepository.findById(film_id);
//            List<ScoreFilmEntity> score = (List<ScoreFilmEntity>) filmEntity.getScoreFilmsById();
//
//            for (ScoreFilmEntity scoreFilmEntity : score) {
//                sum = sum + scoreFilmEntity.getScore();
//            }
//
//            Gson gson = new Gson();
//            FilmEntity film = new FilmEntity();
//            film.setId(filmEntity.getId());
//            film.setName(filmEntity.getName());
//            film.setCreateDate(filmEntity.getCreateDate());
//            film.setBody(filmEntity.getBody());
//            film.setCashbox(filmEntity.getCashbox());
//
//            for (int i = 0; i < filmEntity.getScoreFilmsById().size(); i++) {
//                if (((List<ScoreFilmEntity>) filmEntity.getScoreFilmsById()).get(i).getIdUserUs() == user_id)
//                    scoreUser = ((List<ScoreFilmEntity>) filmEntity.getScoreFilmsById()).get(i).getScore();
//            }
//
//            String str = gson.toJson(film);
//            String s = new sun.misc.BASE64Encoder().encode(filmEntity.getImg());
//
//            String str2 = str.substring(0, str.length() - 1).concat(",\"score\":"
//                    + sum / score.size() + ",\"userScore\":" + scoreUser + "}");
//            // ",\"photo\":\"" + filmEntity.getMeta() + s +
//
//            return str2;
//        } catch (Error ex) {
//            return null;
//        }
//    }


    public String findMainInfoForTheFilm(int film_id, int user_id) throws IOException {
        try {
            float sum = 0;
            float scoreUser = 0;
            FilmEntity filmEntity = filmRepository.findById(film_id);
            List<ScoreFilmEntity> score = (List<ScoreFilmEntity>) filmEntity.getScoreFilms();

            for (ScoreFilmEntity scoreFilmEntity : score) {
                sum = sum + scoreFilmEntity.getScore();
            }

            Gson gson = new Gson();
            FilmEntity film = new FilmEntity();
            film.setId(filmEntity.getId());
            film.setName(filmEntity.getName());
            film.setCreateDate(filmEntity.getCreateDate());
            film.setBody(filmEntity.getBody());
            film.setCashbox(filmEntity.getCashbox());

            for (int i = 0; i < filmEntity.getScoreFilms().size(); i++) {
                if (((List<ScoreFilmEntity>) filmEntity.getScoreFilms()).get(i).getIdUserUs() == user_id)
                    scoreUser = ((List<ScoreFilmEntity>) filmEntity.getScoreFilms()).get(i).getScore();
            }

            String str = gson.toJson(film);

            return str.substring(0, str.length() - 1).concat(",\"score\":"
                    + sum / score.size() + ",\"userScore\":" + scoreUser + "}");
        } catch (Error ex) {
            return null;
        }
    }

    public void saveNewFilm(String name, Date create_date, String body,
                            String photo, int cashbox, int id_studio) {
        FilmEntity film = new FilmEntity();
        film.setCashbox(cashbox);
        film.setBody(body);
        film.setName(name);
        film.setPhoto(photo);
        film.setCreateDate((java.sql.Date) create_date);
        filmRepository.save(film);
        ScriptEntity script = new ScriptEntity();
        script.setIdFilm(film.getId());
        script.setIdStudio(id_studio);
    }
}
