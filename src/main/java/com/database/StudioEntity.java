package com.database;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "studio", schema = "public", catalog = "postgres")
public class StudioEntity {
    private int idStudio;
    private String aboutMe;
    private Long purse;
    private Collection<DialogueEntity> dialogues;
    private Collection<FollowerStudioEntity> followerStudios;
    private Collection<GalleryStudioEntity> galleryStudios;
    private Collection<MessageEntity> messages;
    private Collection<ScoreStudioEntity> scoreStudios;
    private Collection<ScriptEntity> scripts;
    private CustomersEntity customers;
    private Collection<TransactionEntity> transactions;
    private Collection<VerificationEntity> verifications;

    @Id
    @Column(name = "id_studio", nullable = false)
    public int getIdStudio() {
        return idStudio;
    }

    public void setIdStudio(int idStudio) {
        this.idStudio = idStudio;
    }

    @Basic
    @Column(name = "about_me", nullable = true, length = -1)
    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Basic
    @Column(name = "purse", nullable = true)
    public Long getPurse() {
        return purse;
    }

    public void setPurse(Long purse) {
        this.purse = purse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudioEntity that = (StudioEntity) o;

        if (idStudio != that.idStudio) return false;
        if (aboutMe != null ? !aboutMe.equals(that.aboutMe) : that.aboutMe != null) return false;
        if (purse != null ? !purse.equals(that.purse) : that.purse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStudio;
        result = 31 * result + (aboutMe != null ? aboutMe.hashCode() : 0);
        result = 31 * result + (purse != null ? purse.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<DialogueEntity> getDialogues() {
        return dialogues;
    }

    public void setDialogues(Collection<DialogueEntity> dialoguesByIdStudio) {
        this.dialogues = dialoguesByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<FollowerStudioEntity> getFollowerStudios() {
        return followerStudios;
    }

    public void setFollowerStudios(Collection<FollowerStudioEntity> followerStudiosByIdStudio) {
        this.followerStudios = followerStudiosByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<GalleryStudioEntity> getGalleryStudios() {
        return galleryStudios;
    }

    public void setGalleryStudios(Collection<GalleryStudioEntity> galleryStudiosByIdStudio) {
        this.galleryStudios = galleryStudiosByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(Collection<MessageEntity> messagesByIdStudio) {
        this.messages = messagesByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<ScoreStudioEntity> getScoreStudios() {
        return scoreStudios;
    }

    public void setScoreStudios(Collection<ScoreStudioEntity> scoreStudiosByIdStudio) {
        this.scoreStudios = scoreStudiosByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<ScriptEntity> getScripts() {
        return scripts;
    }

    public void setScripts(Collection<ScriptEntity> scriptsByIdStudio) {
        this.scripts = scriptsByIdStudio;
    }

    @OneToOne
    @JoinColumn(name = "id_studio", referencedColumnName = "id", nullable = false)
    public CustomersEntity getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersEntity customersByIdStudio) {
        this.customers = customersByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<TransactionEntity> transactionsByIdStudio) {
        this.transactions = transactionsByIdStudio;
    }

    @OneToMany(mappedBy = "studios")
    public Collection<VerificationEntity> getVerifications() {
        return verifications;
    }

    public void setVerifications(Collection<VerificationEntity> verificationsByIdStudio) {
        this.verifications = verificationsByIdStudio;
    }
}
