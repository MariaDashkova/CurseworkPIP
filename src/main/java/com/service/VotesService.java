package com.service;

import com.database.VotesEntity;
import com.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VotesService {

    private final VotesRepository votesRepository;

    @Autowired
    public VotesService(VotesRepository votesRepository) {
        this.votesRepository = votesRepository;
    }

    public String addNewVote(int id_film, String name) {
        try {
            VotesEntity votesEntity = new VotesEntity();
            votesEntity.setIdFilm(id_film);
            votesEntity.setCountNegative(0);
            votesEntity.setCountPositive(0);
            votesEntity.setName(name);
            votesEntity.setFlagAccess(true);
            votesRepository.save(votesEntity);
            return "Create new Vote";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public String findAllAccessVote() {
        try {
            StringBuilder votes = new StringBuilder();
            List<VotesEntity> entities = votesRepository.findAllByFlagAccess(true);
            for (VotesEntity entity : entities) {
                votes.append("name: ").append(entity.getName());
            }
            return String.valueOf(votes);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private void positive(VotesEntity votesEntity) {
        try {
            setFlag(true);
            votesRepository.updatePositive(votesEntity.getCountPositive() + 1, votesEntity.getId());
        } catch (NullPointerException ex) {
            setFlag(false);
        }
    }

    private void negative(VotesEntity votesEntity) {
        try {
            setFlag(true);
            votesRepository.updateNegative(votesEntity.getCountNegative() + 1, votesEntity.getId());
        } catch (NullPointerException ex) {
            setFlag(false);
        }
    }

    public String recalculation(int id_vote, boolean choice) {
        try {
            VotesEntity votesEntity = votesRepository.findById(id_vote);
            if (choice) positive(votesEntity);
            else negative(votesEntity);
            return "пересчитано";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String findAllVotesForFilm(int id_film) {
        try {
            List<VotesEntity> entities = votesRepository.findAllByIdFilm(id_film);
            StringBuilder answer = new StringBuilder();
            for (VotesEntity entity : entities) {
                answer.append("Name: ").append(entity.getName())
                        .append(",Negative: ").append(entity.getCountNegative())
                        .append(",Positive: ").append(entity.getCountPositive())
                        .append(",IMG: ").append(Arrays.toString(entity.getVotesImageUrl()));
            }
            return String.valueOf(answer);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String findInfoForVote(int id_vote) {
        try {
            VotesEntity votesEntity = votesRepository.findById(id_vote);
            return "  Name:  " + votesEntity.getName() + "  Pos: "
                    + votesEntity.getCountPositive() + "  Neg:  " + votesEntity.getCountNegative();
        } catch (NullPointerException ex) {
            return null;
        }
    }

}
