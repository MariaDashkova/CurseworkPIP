package com.service;

import com.database.ScoreActorEntity;
import com.database.ScoreAnalystEntity;
import com.database.ScoreFilmEntity;
import com.database.ScoreStudioEntity;
import com.repository.ScoreActorRepository;
import com.repository.ScoreAnalystRepository;
import com.repository.ScoreFilmRepository;
import com.repository.ScoreStudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreActorRepository scoreActorRepository;
    private final ScoreAnalystRepository scoreAnalystRepository;
    private final ScoreStudioRepository scoreStudioRepository;
    private final ScoreFilmRepository scoreFilmRepository;

    @Autowired
    public ScoreService(ScoreFilmRepository scoreFilmRepository, ScoreStudioRepository scoreStudioRepository,
                        ScoreAnalystRepository scoreAnalystRepository, ScoreActorRepository scoreActorRepository) {
        this.scoreActorRepository = scoreActorRepository;
        this.scoreAnalystRepository = scoreAnalystRepository;
        this.scoreFilmRepository = scoreFilmRepository;
        this.scoreStudioRepository = scoreStudioRepository;
    }

    public String findAllScoreForAnalyst(int id_analyst) {
        float sum = 0;
        try {
            List<ScoreAnalystEntity> scoreAnalystEntities = scoreAnalystRepository.findAllByIdAnalyst(id_analyst);
            int count = scoreAnalystEntities.size();
            for (ScoreAnalystEntity scoreAnalystEntity : scoreAnalystEntities) {
                sum = sum + scoreAnalystEntity.getScore();
            }
            return "SumScoreAnalyst: " + sum + "   CountOfScores:  " + count;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String findAllScoreForActor(int id_actor) {
        float sum = 0;
        try {
            List<ScoreActorEntity> scoreActorEntities = scoreActorRepository.findAllByIdActor(id_actor);
            int count = scoreActorEntities.size();
            for (ScoreActorEntity scoreActorEntity : scoreActorEntities) {
                sum = sum + scoreActorEntity.getScore();
            }
            return "SumScoreActor: " + sum + "   CountOfScores:  " + count;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String findAllScoreForStudio(int id_studio) {
        float sum = 0;
        try {
            List<ScoreStudioEntity> scoreStudioEntities = scoreStudioRepository.findAllByIdStudio(id_studio);
            int count = scoreStudioEntities.size();
            for (ScoreStudioEntity scoreStudioEntity : scoreStudioEntities) {
                sum = sum + scoreStudioEntity.getScore();
            }
            return "SumScoreStudio: " + sum + "   CountOfScores:  " + count;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String findAllScoreForFilm(int id_film) {
        float sum = 0;
        try {
            List<ScoreFilmEntity> scoreFilmEntities = scoreFilmRepository.findAllByIdFilm(id_film);
            int count = scoreFilmEntities.size();
            for (ScoreFilmEntity scoreFilmEntity : scoreFilmEntities) {
                sum = sum + scoreFilmEntity.getScore();
            }
            return "SumScoreFilm: " + sum + "   CountOfScores:  " + count;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void insertNewScoreForAnalyst(int id_analyst, int id_user, int score) {
        ScoreAnalystEntity scoreAnalystEntity = new ScoreAnalystEntity();
        scoreAnalystEntity.setIdAnalyst(id_analyst);
        scoreAnalystEntity.setScore(score);
        scoreAnalystEntity.setIdUserUs(id_user);
        scoreAnalystRepository.save(scoreAnalystEntity);
    }

    public void insertNewScoreForStudio(int id_studio, int id_user, int score) {
        ScoreStudioEntity scoreAnalystEntity = new ScoreStudioEntity();
        scoreAnalystEntity.setIdStudio(id_studio);
        scoreAnalystEntity.setScore(score);
        scoreAnalystEntity.setIdUserUs(id_user);
        scoreStudioRepository.save(scoreAnalystEntity);

    }

    public void insertNewScoreForActor(int id_actor, int id_user, int score) {
        ScoreActorEntity scoreAnalystEntity = new ScoreActorEntity();
        scoreAnalystEntity.setIdActor(id_actor);
        scoreAnalystEntity.setScore(score);
        scoreAnalystEntity.setIdUserUs(id_user);
        scoreActorRepository.save(scoreAnalystEntity);
    }

    public void insertNewScoreForFilm(int id_film, int id_user, int score) {
        ScoreFilmEntity scoreAnalystEntity = new ScoreFilmEntity();
        scoreAnalystEntity.setIdFilm(id_film);
        scoreAnalystEntity.setScore(score);
        scoreAnalystEntity.setIdUserUs(id_user);
        scoreFilmRepository.save(scoreAnalystEntity);
    }

}
