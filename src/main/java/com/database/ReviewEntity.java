package com.database;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "review", schema = "public", catalog = "postgres")
public class ReviewEntity {
    private Integer id;
    private Integer idAnalyst;
    private Integer idFilm;
    private String body;
    private Integer score;

    @Basic
    @Column(name = "score", nullable = true, insertable = false, updatable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    private Collection<RevTagEntity> revTags;
    private AnalystEntity analysts;
    private FilmEntity films;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_analyst", nullable = true, insertable = false, updatable = false)
    public Integer getIdAnalyst() {
        return idAnalyst;
    }

    public void setIdAnalyst(Integer idAnalyst) {
        this.idAnalyst = idAnalyst;
    }

    @Basic
    @Column(name = "id_film", nullable = true, insertable = false, updatable = false)
    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    @Basic
    @Column(name = "body", nullable = true, length = -1)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewEntity that = (ReviewEntity) o;

        if (id != that.id) return false;
        if (idAnalyst != null ? !idAnalyst.equals(that.idAnalyst) : that.idAnalyst != null) return false;
        if (idFilm != null ? !idFilm.equals(that.idFilm) : that.idFilm != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idAnalyst != null ? idAnalyst.hashCode() : 0);
        result = 31 * result + (idFilm != null ? idFilm.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reviews")
    public Collection<RevTagEntity> getRevTags() {
        return revTags;
    }

    public void setRevTags(Collection<RevTagEntity> revTagsById) {
        this.revTags = revTagsById;
    }

    @ManyToOne
    @JoinColumn(name = "id_analyst", referencedColumnName = "id_analyst")
    public AnalystEntity getAnalysts() {
        return analysts;
    }

    public void setAnalysts(AnalystEntity analystByIdAnalyst) {
        this.analysts = analystByIdAnalyst;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id")
    public FilmEntity getFilms() {
        return films;
    }

    public void setFilms(FilmEntity filmByIdFilm) {
        this.films = filmByIdFilm;
    }
}
