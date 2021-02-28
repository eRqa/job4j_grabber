package ru.job4j.models;

import java.util.Date;

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
}
