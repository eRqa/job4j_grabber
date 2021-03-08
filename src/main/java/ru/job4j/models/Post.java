package ru.job4j.models;

import java.util.Date;
import java.util.Objects;

public class Post {

    private String id;
    private String link;
    private String name;
    private String text;
    private Date created;

    public Post(String id, String link, String name, String text, Date created) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.text = text;
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

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", created=" + created +
                '}';
    }
}
