package com.service;

import com.database.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;
    private final ScoreFilmRepository scoreFilmRepository;
    private final ScoreActorRepository scoreActorRepository;
    private final ScoreAnalystRepository scoreAnalystRepository;
    private final ScoreStudioRepository scoreStudioRepository;
    private final VereficationRepository vereficationRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository,
                            ScoreFilmRepository scoreFilmRepository,
                            ScoreActorRepository scoreActorRepository,
                            ScoreAnalystRepository scoreAnalystRepository,
                            ScoreStudioRepository scoreStudioRepository,
                            VereficationRepository vereficationRepository) {
        this.customersRepository = customersRepository;
        this.scoreFilmRepository = scoreFilmRepository;
        this.scoreActorRepository = scoreActorRepository;
        this.scoreAnalystRepository = scoreAnalystRepository;
        this.scoreStudioRepository = scoreStudioRepository;
        this.vereficationRepository = vereficationRepository;
    }

    public CustomersEntity returnUser(String log, String pass) {
        return customersRepository.findLogPas(log, pass);
    }

    public void updateLevelAccess(int id_customer, int id_studio) {
            customersRepository.findCustomersEntityById(id_customer).setLevelAccess(1);
            customersRepository.updateUserSetLevelAccessForId(1, id_customer);
            VerificationEntity verificationEntity = new VerificationEntity();
            verificationEntity.setIdCustomers(id_customer);
            verificationEntity.setIdStudio(id_studio);
            vereficationRepository.save(verificationEntity);
    }

    public void updateEmail(int id, String email) {
        customersRepository.updateUserSetEmailForId(email, id);
    }


    public void updatePassword(int id_customer, String password) {
            customersRepository.updateUserSetPasswordForId(getMd5(password), id_customer);

    }

    public void updateMobailNumber(int id, String mobile) {

        customersRepository.updateUserSetMobailNumberForId(mobile, id);
    }

    public void updateName(String name, int id) {
        customersRepository.updateUserSetNameForId(name, id);
    }


    public String returnEmail(int id_user) {
        //do not understand how to put here id
        try {
            return customersRepository.findById(id_user).getEmailHash();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String returnHashPas(int id_user) {
        try {
            return customersRepository.findById(id_user).getPassword();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void scoreForFilm(int idCust, int idFilm, float score) {
        ScoreFilmEntity sf = new ScoreFilmEntity();
        sf.setIdFilm(idFilm);
        sf.setIdUserUs(idCust);
        sf.setScore(score);
        scoreFilmRepository.save(sf);
    }

    public void scoreForAnalyst(int idCust, int idAnalyst, float score) {
        ScoreAnalystEntity sa = new ScoreAnalystEntity();
        sa.setIdAnalyst(idAnalyst);
        sa.setIdUserUs(idCust);
        sa.setScore(score);
        scoreAnalystRepository.save(sa);
    }

    public void scoreForActor(int idCust, int idActor, float score) {
        ScoreActorEntity sa = new ScoreActorEntity();
        sa.setIdActor(idActor);
        sa.setIdUserUs(idCust);
        sa.setScore(score);
        scoreActorRepository.save(sa);
    }

    public void scoreForStudio(int idCust, int idStudio, float score) {
        ScoreStudioEntity ss = new ScoreStudioEntity();
        ss.setIdStudio(idStudio);
        ss.setIdUserUs(idCust);
        ss.setScore(score);
        scoreStudioRepository.save(ss);
    }

    public void insertNewCustomer(String name, String login, String
            pIurl, String email, String pass, String mobile, Boolean rep,
                                  Date date, int level) {

        CustomersEntity newCust = new CustomersEntity();
        newCust.setName(name);
        newCust.setLogin(login);
        newCust.setProfileImageUrl(pIurl);
        newCust.setEmailHash(email);
        newCust.setPassword(getMd5(pass));
        newCust.setMobNumber(mobile);
        newCust.setReputation(rep);
        newCust.setLastAccess(date);
        newCust.setLevelAccess(level);
        customersRepository.save(newCust);

    }

    public boolean auth(String log, String pass) {
        return customersRepository.findLogPas(log, getMd5(pass)) != null;
    }

    public void loadImg(int id, String img) {
        customersRepository.updateUserSetImgForId(img, id);
    }


    public CustomersEntity findByLog(String log) {
        return customersRepository.findByLogin(log);
    }

    public CustomersEntity findById(int id) {
        return customersRepository.findById(id);
    }

    public CustomersEntity findByPas(String pas) {
        return customersRepository.findByPassword(pas);
    }


    public void registrationWithSocialNetwork(String first_name,
                                              String last_name,
                                              String href,
                                              String mid) {
        CustomersEntity customer = new CustomersEntity();
        try {
            customer.setName(first_name + " " + last_name);
            customer.setEmailHash(href);
            customer.setLogin(mid);

            customer.setLevelAccess(0);
            java.util.Date date = new java.util.Date();
            customer.setLastAccess(new Date(date.getTime()));
            customer.setPassword(getMd5(mid));
            customer.setReputation(true);
            if (customersRepository.findByLog(mid) == null) customersRepository.save(customer);
            else System.out.println("отловлен повторяющийся акк");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Todo add
    public ArrayList<Integer> subscribeID(int id) {
        ArrayList<Integer> ids = new ArrayList<>();
        Collection<FollowerActorEntity> followersActor = customersRepository.findById(id).getFollowersActor();
        Collection<FollowerAnalystEntity> followersAnalyst = customersRepository.findById(id).getFollowersAnalyst();
        Collection<FollowerStudioEntity> followersStudio = customersRepository.findById(id).getFollowersStudio();

        for (FollowerActorEntity actor : followersActor
        ) {
            ids.add(actor.getIdActor());
        }

        for (FollowerAnalystEntity a : followersAnalyst
        ) {
            ids.add(a.getIdAnalyst());
        }

        for (FollowerStudioEntity s : followersStudio
        ) {
            ids.add(s.getIdStudio());
        }
        return ids;
    }

    public String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
