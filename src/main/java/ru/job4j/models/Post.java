package ru.job4j.models;

import java.util.Date;
import java.util.Objects;

public class Post {

    private String id;
    private String name;
    private String text;
    private String author;
    private Date created;

    public Post(String id, String name, String text, String author, Date created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.author = author;
        this.created = created;
    }

    public Post() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreated() {
        return created;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(text, post.text) &&
                Objects.equals(author, post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, author);
    }
}
