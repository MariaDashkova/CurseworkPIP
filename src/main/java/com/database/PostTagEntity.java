package com.database;

import javax.persistence.*;

@Entity
@Table(name = "post_tag", schema = "public", catalog = "postgres")
@IdClass(PostTagEntityPK.class)
public class PostTagEntity {
    private int idPost;
    private int idTag;
    private PostEntity posts;
    private TagEntity tags;

    @Basic
    @Id
    @Column(name = "id_post", nullable = false, insertable = false, updatable = false)
    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    @Basic
    @Id
    @Column(name = "id_tag", nullable = false, insertable = false, updatable = false)
    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostTagEntity that = (PostTagEntity) o;

        if (idPost != that.idPost) return false;
        if (idTag != that.idTag) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPost;
        result = 31 * result + idTag;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_post", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PostEntity getPosts() {
        return posts;
    }

    public void setPosts(PostEntity postByIdPost) {
        this.posts = postByIdPost;
    }

    @ManyToOne
    @JoinColumn(name = "id_tag", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public TagEntity getTags() {
        return tags;
    }

    public void setTags(TagEntity tagByIdTag) {
        this.tags = tagByIdTag;
    }
}
