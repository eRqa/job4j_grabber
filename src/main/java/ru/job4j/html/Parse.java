package ru.job4j.html;

import ru.job4j.models.Post;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Parse {
    List<Post> list(String link) throws ParseException;

    Post detail(String link) throws IOException, ParseException;
}