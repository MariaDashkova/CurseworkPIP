package com.service;

import com.database.VotesUserEntity;
import com.repository.VotesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotesUserService {

    private final VotesUserRepository votesUserRepository;

    @Autowired
    public VotesUserService(VotesUserRepository votesUserRepository) {
        this.votesUserRepository = votesUserRepository;
    }

    public String addNewVote(int id_user, int id_vote) {
        try {
            VotesUserEntity votesUserEntity = new VotesUserEntity();
            votesUserEntity.setIdUser(id_user);
            votesUserEntity.setIdVotes(id_vote);
            votesUserRepository.save(votesUserEntity);
            return "Save person after voting";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public boolean checkUserVote(int id_user, int id_vote) {
        VotesUserEntity votesUserEntity = votesUserRepository.findByIdUserAndAndIdVotes(id_user, id_vote);
        return votesUserEntity != null;
    }

}
