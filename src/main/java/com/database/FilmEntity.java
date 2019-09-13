package com.database;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "film", schema = "public", catalog = "postgres")
public class FilmEntity {
    private Integer id;
    private String name;
    private Date createDate;
    private String body;



    private String photo;
    private Integer cashbox;
    private Collection<CastActorEntity> castActors;
    private Collection<ChatEntity> chats;
    private Collection<FilmTagEntity> filmTags;
    private Collection<GalleryFilmEntity> galleryFilms;
    private Collection<GenreFilmEntity> genreFilms;
    private Collection<ReviewEntity> reviews;
    private Collection<ScoreFilmEntity> scoreFilms;
    private Collection<ScriptEntity> scripts;
    private Collection<VotesEntity> votes;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "body", nullable = true, length = -1)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "photo", nullable = true)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Basic
    @Column(name = "cashbox", nullable = true)
    public Integer getCashbox() {
        return cashbox;
    }

    public void setCashbox(Integer cashbox) {
        this.cashbox = cashbox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmEntity that = (FilmEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (cashbox != null ? !cashbox.equals(that.cashbox) : that.cashbox != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (cashbox != null ? cashbox.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "film")
    public Collection<CastActorEntity> getCastActors() {
        return castActors;
    }

    public void setCastActors(Collection<CastActorEntity> castActorsById) {
        this.castActors = castActorsById;
    }

    @OneToMany(mappedBy = "film")
    public Collection<ChatEntity> getChats() {
        return chats;
    }

    public void setChats(Collection<ChatEntity> chatsById) {
        this.chats = chatsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<FilmTagEntity> getFilmTags() {
        return filmTags;
    }

    public void setFilmTags(Collection<FilmTagEntity> filmTagsById) {
        this.filmTags = filmTagsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<GalleryFilmEntity> getGalleryFilms() {
        return galleryFilms;
    }

    public void setGalleryFilms(Collection<GalleryFilmEntity> galleryFilmsById) {
        this.galleryFilms = galleryFilmsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<GenreFilmEntity> getGenreFilms() {
        return genreFilms;
    }

    public void setGenreFilms(Collection<GenreFilmEntity> genreFilmsById) {
        this.genreFilms = genreFilmsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<ReviewEntity> reviewsById) {
        this.reviews = reviewsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<ScoreFilmEntity> getScoreFilms() {
        return scoreFilms;
    }

    public void setScoreFilms(Collection<ScoreFilmEntity> scoreFilmsById) {
        this.scoreFilms = scoreFilmsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<ScriptEntity> getScripts() {
        return scripts;
    }

    public void setScripts(Collection<ScriptEntity> scriptsById) {
        this.scripts = scriptsById;
    }

    @OneToMany(mappedBy = "films")
    public Collection<VotesEntity> getVotes() {
        return votes;
    }

    public void setVotes(Collection<VotesEntity> votesById) {
        this.votes = votesById;
    }
}
